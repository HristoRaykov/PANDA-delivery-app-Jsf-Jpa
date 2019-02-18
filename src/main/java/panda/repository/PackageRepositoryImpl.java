package panda.repository;

import panda.domain.entities.Package;
import panda.domain.entities.Status;
import panda.domain.entities.User;
import panda.domain.models.service.PackageServiceModel;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PackageRepositoryImpl implements PackageRepository {

    private final EntityManager entityManager;

    @Inject
    public PackageRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Package save(Package entity) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(entity);
        this.entityManager.getTransaction().commit();

        return entity;
    }

    @Override
    public List<Package> findAll() {
        this.entityManager.getTransaction().begin();
        List<Package> packages = this.entityManager
                .createQuery("SELECT p FROM Package p ", Package.class)
                .getResultList();
        this.entityManager.getTransaction().commit();

        return packages;
    }

    @Override
    public Package findById(String id) {
        this.entityManager.getTransaction().begin();
	    Package aPackage = null;
	    try {
		    aPackage = this.entityManager
		            .createQuery("SELECT p FROM Package p WHERE p.id = :id", Package.class)
		            .setParameter("id", id)
		            .getSingleResult();
	    } catch (Exception e) {
		    e.printStackTrace();
	    }finally {
		    this.entityManager.getTransaction().commit();
	    }
	    
        return aPackage;
    }

    @Override
    public Long size() {
        this.entityManager.getTransaction().begin();
        Long size = this.entityManager
                .createQuery("SELECT count(p) FROM Package p ", Long.class)
                .getSingleResult();
        this.entityManager.getTransaction().commit();

        return size;
    }

    @Override
    public List<Package> findAllPackagesByStatus(Status status) {
        this.entityManager.getTransaction().begin();
	    List<Package> packages = null;
	    try {
		    packages = this.entityManager
		            .createQuery("SELECT p FROM Package p WHERE p.status = :status", Package.class)
		            .setParameter("status", status)
		            .getResultList();
	    } catch (Exception e) {
		    return new ArrayList<>();
	    } finally {
		    this.entityManager.getTransaction().commit();
	    }

        return packages;
    }

    @Override
    public Optional<Package> savePackage(Package aPackage) {
        this.entityManager.getTransaction().begin();
	
	    try {
		   this.entityManager.persist(aPackage);
	    } catch (Exception e) {
		   return Optional.empty();
	    }finally {
		    this.entityManager.getTransaction().commit();
	    }
	    
        return Optional.of(aPackage);
    }
	
	@Override
	public Optional<Package> updatePackage(Package aPackage) {
		this.entityManager.getTransaction().begin();
		
		try {
			aPackage = this.entityManager.merge(aPackage);
		} catch (Exception e) {
			return Optional.empty();
		}finally {
			this.entityManager.getTransaction().commit();
		}
		
		return Optional.of(aPackage);
	}
	
	@Override
	public List<Package> findAllPackagesByUserAndStatus(String username,Status status) {
		this.entityManager.getTransaction().begin();
		List<Package> packages = null;
		try {
			packages = this.entityManager
					.createQuery("SELECT p FROM Package p WHERE p.status=:status and p.recipient.username = :username", Package.class)
					.setParameter("username", username)
					.setParameter("status", status)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.entityManager.getTransaction().commit();
		}
		
		return packages;
	}
}
