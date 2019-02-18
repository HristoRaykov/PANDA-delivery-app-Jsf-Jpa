package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.view.ShippedPackageViewModel;
import panda.service.PackageService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class ShippedPackageBean {
	
	private List<ShippedPackageViewModel> shippedPackages;
	private String packageId;
	
	private final PackageService packageService;
	private final ModelMapper modelMapper;
	
	@Inject
	public ShippedPackageBean(PackageService packageService, ModelMapper modelMapper) {
		this.packageService = packageService;
		this.modelMapper = modelMapper;
		this.initShippedPackages();
	}
	
	public List<ShippedPackageViewModel> getShippedPackages() {
		return shippedPackages;
	}
	
	public void setShippedPackages(List<ShippedPackageViewModel> shippedPackages) {
		this.shippedPackages = shippedPackages;
	}
	
	public String getPackageId() {
		return packageId;
	}
	
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	private void initShippedPackages() {
		List<PackageServiceModel> packageServiceModels = packageService.getAllPackagesByStatus(Status.SHIPPED);
		shippedPackages = packageServiceModels.stream()
				.map(packageServiceModel -> {
					String recipientName = packageServiceModel.getRecipient().getUsername();
					LocalDateTime estimatedDeliveryTime = packageServiceModel.getEstimatedDeliveryTime();
					
					ShippedPackageViewModel shippedPackageViewModel = this.modelMapper.map(packageServiceModel, ShippedPackageViewModel.class);
					shippedPackageViewModel.setRecipient(recipientName);
					shippedPackageViewModel.setEstimateDeliveryDate(estimatedDeliveryTime.format(
							DateTimeFormatter.ofPattern("dd/MM/yyyy")
					));
					
					return shippedPackageViewModel;
				})
				.collect(Collectors.toList());
	}
	
	
	public void deliverPackage(String packageId) throws IOException {
		if (!this.packageService.deliverPackage(packageId)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/view/admin/shipped.xhtml");
		}
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/view/admin/delivered.xhtml");
	}
}
