package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.models.binding.PackageCreateBindingModel;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.service.UserServiceModel;
import panda.service.PackageService;
import panda.service.UserService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class PackageCreateBean {
	
	private PackageCreateBindingModel packageCreateBindingModel;
	private List<String> usernames;
	
	private final PackageService packageService;
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Inject
	public PackageCreateBean(PackageService packageService, UserService userService, ModelMapper modelMapper) {
		this.packageService = packageService;
		this.userService = userService;
		this.modelMapper = modelMapper;
		this.packageCreateBindingModel = new PackageCreateBindingModel();
		this.initUsernames();
	}
	
	private void initUsernames() {
		this.usernames = this.userService.getAllUsers()
				.stream()
				.map(UserServiceModel::getUsername)
				.collect(Collectors.toList());
	}
	
	public PackageCreateBindingModel getPackageCreateBindingModel() {
		return packageCreateBindingModel;
	}
	
	public void setPackageCreateBindingModel(PackageCreateBindingModel packageCreateBindingModel) {
		this.packageCreateBindingModel = packageCreateBindingModel;
	}
	
	public List<String> getUsernames() {
		return usernames;
	}
	
	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}
	
	public void createPackage() throws IOException {
		PackageServiceModel packageServiceModel = this.modelMapper.map(this.packageCreateBindingModel,PackageServiceModel.class);
		String recipientName = packageCreateBindingModel.getRecipient();
		packageServiceModel.getRecipient().setUsername(recipientName);
		if (!this.packageService.createPackage(packageServiceModel)){
			FacesContext.getCurrentInstance().getExternalContext().redirect("/view/admin/create-package.xhtml");
		}
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/view/admin/pending.xhtml");
	}
}
