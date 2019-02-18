package panda.service;

import panda.domain.models.service.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {
	
	boolean register(UserServiceModel userServiceModel);
	
	
	Optional<UserServiceModel> loginUser(UserServiceModel userServiceModel);
	
	List<UserServiceModel> getAllUsers();
}
