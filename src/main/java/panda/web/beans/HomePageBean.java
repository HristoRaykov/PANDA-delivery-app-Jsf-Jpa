package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Status;
import panda.domain.models.view.PackageHomePageView;
import panda.service.PackageService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Named
public class HomePageBean {
	
	private List<PackageHomePageView> pendingPackages;
	private List<PackageHomePageView> shippedPackages;
	private List<PackageHomePageView> deliveredPackages;
	
	private final PackageService packageService;
	private final ModelMapper modelMapper;
	
	@Inject
	public HomePageBean(PackageService packageService, ModelMapper modelMapper) {
		this.packageService = packageService;
		this.modelMapper = modelMapper;
		this.initPackageLists();
	}
	
	private void initPackageLists() {
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String username = (String) httpSession.getAttribute("username");
		pendingPackages = Arrays.asList(this.modelMapper
				.map(this.packageService.getAllPackagesByUserAndStatus(username, Status.PENDING),PackageHomePageView[].class));
		shippedPackages = Arrays.asList(this.modelMapper
				.map(this.packageService.getAllPackagesByUserAndStatus(username, Status.SHIPPED),PackageHomePageView[].class));
		deliveredPackages = Arrays.asList(this.modelMapper
				.map(this.packageService.getAllPackagesByUserAndStatus(username, Status.DELIVERED),PackageHomePageView[].class));
	}
	
	public List<PackageHomePageView> getPendingPackages() {
		return pendingPackages;
	}
	
	public void setPendingPackages(List<PackageHomePageView> pendingPackages) {
		this.pendingPackages = pendingPackages;
	}
	
	public List<PackageHomePageView> getShippedPackages() {
		return shippedPackages;
	}
	
	public void setShippedPackages(List<PackageHomePageView> shippedPackages) {
		this.shippedPackages = shippedPackages;
	}
	
	public List<PackageHomePageView> getDeliveredPackages() {
		return deliveredPackages;
	}
	
	public void setDeliveredPackages(List<PackageHomePageView> deliveredPackages) {
		this.deliveredPackages = deliveredPackages;
	}
}
