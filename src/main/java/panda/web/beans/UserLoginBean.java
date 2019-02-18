package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.models.binding.UserLoginBindingModel;
import panda.domain.models.service.UserServiceModel;
import panda.service.UserService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Named
public class UserLoginBean {
	
	private UserLoginBindingModel userLoginBindingModel;
	
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Inject
	public UserLoginBean(UserService userService, ModelMapper modelMapper) {
		this.userService = userService;
		this.modelMapper = modelMapper;
		this.userLoginBindingModel = new UserLoginBindingModel();
	}
	
	public UserLoginBindingModel getUserLoginBindingModel() {
		return userLoginBindingModel;
	}
	
	public void setUserLoginBindingModel(UserLoginBindingModel userLoginBindingModel) {
		this.userLoginBindingModel = userLoginBindingModel;
	}
	
	
	public void loginUser() throws IOException {
		UserServiceModel userServiceModel = this.modelMapper.map(userLoginBindingModel, UserServiceModel.class);
		
		Optional<UserServiceModel> loginUserServiceModel = this.userService.loginUser(userServiceModel);
		
		if (loginUserServiceModel.isEmpty()) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/view/guestuser/login.xhtml");
			return;
		}
		
		HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		httpSession.setAttribute("username", loginUserServiceModel.get().getUsername());
		httpSession.setAttribute("role", loginUserServiceModel.get().getRole());
		
		FacesContext.getCurrentInstance().getExternalContext().redirect("/view/loginuser/home.xhtml");
	}
	

}
