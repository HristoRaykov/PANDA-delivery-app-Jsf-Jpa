package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.service.PackageService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class AcquirePackageBean {
	
	private final PackageService packageService;
	private final ModelMapper modelMapper;
	
	@Inject
	public AcquirePackageBean(PackageService packageService, ModelMapper modelMapper) {
		this.packageService = packageService;
		this.modelMapper = modelMapper;
	}
	
	public void acquirePackage(String packageId) throws IOException {
		this.packageService.acquirePackage(packageId);
		FacesContext.getCurrentInstance().getExternalContext().redirect("/view/loginuser/receipts.xhtml");
	}
	
	
}
