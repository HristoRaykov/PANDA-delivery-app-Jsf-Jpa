package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.view.DeliveredPackageViewModel;
import panda.domain.models.view.ShippedPackageViewModel;
import panda.service.PackageService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class DeliveredPackageBean {
	
	private List<DeliveredPackageViewModel> deliveredPackages;
	private String packageId;
	
	private final PackageService packageService;
	private final ModelMapper modelMapper;
	
	@Inject
	public DeliveredPackageBean(PackageService packageService, ModelMapper modelMapper) {
		this.packageService = packageService;
		this.modelMapper = modelMapper;
		this.initReceivedPackages();
	}
	
	private void initReceivedPackages() {
		List<PackageServiceModel> delivered = packageService.getAllPackagesByStatus(Status.DELIVERED);
		List<PackageServiceModel> acquired = packageService.getAllPackagesByStatus(Status.ACQUIREED);
		List<PackageServiceModel> packageServiceModels = new ArrayList<>(delivered);
		packageServiceModels.addAll(acquired);
		deliveredPackages = packageServiceModels.stream()
				.map(packageServiceModel -> {
					String recipientName = packageServiceModel.getRecipient().getUsername();
					DeliveredPackageViewModel deliveredPackageViewModel = this.modelMapper.map(packageServiceModel, DeliveredPackageViewModel.class);
					deliveredPackageViewModel.setRecipient(recipientName);
					
					return deliveredPackageViewModel;
				})
				.collect(Collectors.toList());
	}
	
	public List<DeliveredPackageViewModel> getDeliveredPackages() {
		return deliveredPackages;
	}
	
	public void setDeliveredPackages(List<DeliveredPackageViewModel> deliveredPackages) {
		this.deliveredPackages = deliveredPackages;
	}
	
	public String getPackageId() {
		return packageId;
	}
	
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	
}
