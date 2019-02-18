package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.view.PackageDetailsViewModel;
import panda.service.PackageService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Named
public class PackageDetailsBean {
	
	private PackageDetailsViewModel packageDetailsViewModel;
	
	private final PackageService packageService;
	private final ModelMapper modelMapper;
	
	@Inject
	public PackageDetailsBean(PackageService packageService, ModelMapper modelMapper) {
		this.packageService = packageService;
		this.modelMapper = modelMapper;
		this.initPackageDetailsViewModel();
	}
	
	private void initPackageDetailsViewModel() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String packageId = req.getParameter("id");
		PackageServiceModel packageServiceModel = this.packageService.findPackageById(packageId);
		Optional<LocalDateTime> estimatedDeliveryTime = Optional.ofNullable(packageServiceModel.getEstimatedDeliveryTime());
		
		packageDetailsViewModel = this.modelMapper.map(packageServiceModel,PackageDetailsViewModel.class);
		packageDetailsViewModel.setRecipient(packageServiceModel.getRecipient().getUsername());
		if ( packageServiceModel.getStatus().equals(Status.ACQUIREED) || packageServiceModel.getStatus().equals(Status.DELIVERED) ) {
			packageDetailsViewModel.setEstimatedDeliveryTime("Delivered");
		}else if (estimatedDeliveryTime.isPresent()) {
			packageDetailsViewModel.setEstimatedDeliveryTime(estimatedDeliveryTime.get().format(
					DateTimeFormatter.ofPattern("dd/MM/yyyy")
			));
		}else {
			packageDetailsViewModel.setEstimatedDeliveryTime("N/A");
		}
		System.out.println();
	}
	
	public PackageDetailsViewModel getPackageDetailsViewModel() {
		return packageDetailsViewModel;
	}
	
	public void setPackageDetailsViewModel(PackageDetailsViewModel packageDetailsViewModel) {
		this.packageDetailsViewModel = packageDetailsViewModel;
	}
	
	
	
}
