package panda.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import panda.domain.entities.User;
import panda.domain.models.service.UserServiceModel;
import panda.repository.UserRepository;
import panda.util.ValidatorUtil;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final ValidatorUtil validatorUtil;
	
	@Inject
	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.validatorUtil = validatorUtil;
	}
	
	
	@Override
	public boolean register(UserServiceModel userServiceModel) {
		User user = this.modelMapper.map(userServiceModel, User.class);
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		if (this.userRepository.size() == 0) {
			user.setRole("admin");
		} else {
			user.setRole("user");
		}
		user = this.userRepository.save(user);
		return user != null;
	}
	
	@Override
	public Optional<UserServiceModel> loginUser(UserServiceModel userServiceModel) {
		Optional<User> user = this.userRepository.findByUsername(userServiceModel.getUsername());
		
		if (user.isEmpty()) {
			return Optional.empty();
		}
		
		if (!user.get().getPassword().equals(DigestUtils.sha256Hex(userServiceModel.getPassword()))){
			return Optional.empty();
		}
		
		return Optional.of(this.modelMapper.map(user.get(),UserServiceModel.class));
	}
	
	@Override
	public List<UserServiceModel> getAllUsers(){
		return Arrays.asList(this.modelMapper.map(this.userRepository.findAll(),UserServiceModel[].class));
	}
}
