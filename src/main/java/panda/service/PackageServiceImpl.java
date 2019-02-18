package panda.service;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Package;
import panda.domain.entities.Status;
import panda.domain.entities.User;
import panda.domain.models.service.PackageServiceModel;
import panda.repository.PackageRepository;
import panda.repository.UserRepository;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PackageServiceImpl implements PackageService {
	
	private final ReceiptService receiptService;
	private final PackageRepository packageRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	
	
	@Inject
	public PackageServiceImpl(PackageRepository packageRepository, UserRepository userRepository, ModelMapper modelMapper, ReceiptService receiptService) {
		this.packageRepository = packageRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.receiptService = receiptService;
		
	}
	
	
	@Override
	public boolean createPackage(PackageServiceModel packageServiceModel) {
		Package aPackage = this.modelMapper.map(packageServiceModel, Package.class);
		Optional<User> user = this.userRepository.findByUsername(packageServiceModel.getRecipient().getUsername());
		
		if (user.isEmpty()) {
			return false;
		}
		aPackage.setRecipient(user.get());
		aPackage.setStatus(Status.PENDING);
		aPackage.setEstimatedDeliveryTime(null);
		
		Optional<Package> savedPackage = this.packageRepository.savePackage(aPackage);
		
		return savedPackage.isPresent();
	}
	
	@Override
	public List<PackageServiceModel> getAllPackagesByStatus(Status status) {
		return Arrays.asList(this.modelMapper
				.map(this.packageRepository.findAllPackagesByStatus(status),PackageServiceModel[].class));
	}
	
	@Override
	public boolean shipPackage(String packageId) {
		Package aPackage = this.packageRepository.findById(packageId);
		
		this.updatePackageStatus(aPackage);
		LocalDateTime estimatedDeliverDate = this.calculateEstimateDeliveryDate();
		aPackage.setEstimatedDeliveryTime(estimatedDeliverDate);
		
		Optional<Package> updatedPackage = this.packageRepository.updatePackage(aPackage);
		
		return updatedPackage.isPresent();
	}
	
	@Override
	public boolean deliverPackage(String packageId) {
		Package aPackage = this.packageRepository.findById(packageId);
		
		this.updatePackageStatus(aPackage);
		
		Optional<Package> updatedPackage = this.packageRepository.updatePackage(aPackage);
		
		return updatedPackage.isPresent();
	}
	
	@Override
	public PackageServiceModel findPackageById(String packageId) {
		Package aPackage = this.packageRepository.findById(packageId);
		return (this.modelMapper.map(aPackage,PackageServiceModel.class));
	}
	
	@Override
	public void acquirePackage(String packageId) {
		Package aPackage = this.packageRepository.findById(packageId);
		aPackage.setStatus(Status.ACQUIREED);
		this.packageRepository.updatePackage(aPackage);
		this.receiptService.createReceipt(aPackage);
	}
	
	@Override
	public List<PackageServiceModel> getAllPackagesByUserAndStatus(String username, Status status) {
		return Arrays.asList(this.modelMapper
				.map(this.packageRepository.findAllPackagesByUserAndStatus(username,status),PackageServiceModel[].class));
	}
	
	private LocalDateTime calculateEstimateDeliveryDate() {
		long random = System.currentTimeMillis()%21 + 20;
		return LocalDateTime.now().plusDays(random);
	}
	
	private void updatePackageStatus(Package aPackage) {
		Status currentStatus = aPackage.getStatus();
		Status nextStatus = Status.values()[(currentStatus.ordinal()+1)];
		aPackage.setStatus(nextStatus);
	}
}
