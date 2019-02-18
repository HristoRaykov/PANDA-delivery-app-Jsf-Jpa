package panda.service;

import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;

import java.util.List;

public interface PackageService {
	
	boolean createPackage(PackageServiceModel packageServiceModel);
	
	List<PackageServiceModel> getAllPackagesByStatus(Status status);
	
	boolean shipPackage(String packageId);
	
	boolean deliverPackage(String packageId);
	
	PackageServiceModel findPackageById(String packageId);
	
	void acquirePackage(String packageId);
	
	List<PackageServiceModel> getAllPackagesByUserAndStatus(String username, Status pending);
}
