package panda.web.beans;


import org.modelmapper.ModelMapper;
import panda.domain.models.binding.UserRegisterBindingModel;
import panda.domain.models.service.UserServiceModel;
import panda.service.UserService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class UserRegisterBean {
	
	private UserRegisterBindingModel userRegisterBindingModel;
	
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Inject
	public UserRegisterBean(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
		this.userRegisterBindingModel = new UserRegisterBindingModel();
	}
	
	public UserRegisterBindingModel getUserRegisterBindingModel() {
		return userRegisterBindingModel;
	}
	
	public void setUserRegisterBindingModel(UserRegisterBindingModel userRegisterBindingModel) {
		this.userRegisterBindingModel = userRegisterBindingModel;
	}
	
	public void registerUser() throws IOException {
		UserServiceModel userServiceModel = this.modelMapper.map(this.userRegisterBindingModel,UserServiceModel.class);
		
		if (!this.userRegisterBindingModel.getPassword().equals(this.userRegisterBindingModel.getConfirmPassword())){
			FacesContext.getCurrentInstance().getExternalContext().redirect("/view/guestuser/register.xhtml");
			return;
		}
		
		this.userService.register(userServiceModel);
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/view/guestuser/login.xhtml");
		
	}
}
