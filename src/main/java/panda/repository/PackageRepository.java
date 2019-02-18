package panda.repository;

import panda.domain.entities.Package;
import panda.domain.entities.Status;
import panda.domain.models.service.PackageServiceModel;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends GenericRepository<Package, String> {

    List<Package> findAllPackagesByStatus(Status status);
	
	Optional<Package> savePackage(Package aPackage);
	
	
	Optional<Package> updatePackage(Package aPackage);
	
	List<Package> findAllPackagesByUserAndStatus(String username,Status status);
}
