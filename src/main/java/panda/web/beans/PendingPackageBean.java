package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.view.PendingPackageViewModel;
import panda.service.PackageService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class PendingPackageBean {
	
	private List<PendingPackageViewModel> packages;
	private String packageId;
	
	private final PackageService packageService;
	private final ModelMapper modelMapper;
	
	@Inject
	public PendingPackageBean(PackageService packageService, ModelMapper modelMapper) {
		this.packageService = packageService;
		this.modelMapper = modelMapper;
		this.initPackages();
	}
	
	private void initPackages() {
		List<PackageServiceModel> packageServiceModels = packageService.getAllPackagesByStatus(Status.PENDING);
		packages = packageServiceModels.stream()
				.map(packageServiceModel -> {
					String recipientName = packageServiceModel.getRecipient().getUsername();
					PendingPackageViewModel pendingPackageViewModel = this.modelMapper.map(packageServiceModel, PendingPackageViewModel.class);
					pendingPackageViewModel.setRecipient(recipientName);
					return pendingPackageViewModel;
				})
				.collect(Collectors.toList());
		
	}
	
	public List<PendingPackageViewModel> getPackages() {
		return packages;
	}
	
	public void setPackages(List<PendingPackageViewModel> packages) {
		this.packages = packages;
	}
	
	public String getPackageId() {
		return packageId;
	}
	
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	public void shipPackage(String packageId) throws IOException {
		if (!this.packageService.shipPackage(packageId)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/view/admin/pending.xhtml");
		}
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/view/admin/shipped.xhtml");
	}
	
}
