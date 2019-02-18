package panda.repository;

import panda.domain.entities.Receipt;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class ReceiptRepositoryImpl implements ReceiptRepository {
	
	private final EntityManager entityManager;
	
	@Inject
	public ReceiptRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	@Override
	public Receipt save(Receipt entity) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(entity);
		this.entityManager.getTransaction().commit();
		
		return entity;
	}
	
	@Override
	public List<Receipt> findAll() {
		this.entityManager.getTransaction().begin();
		List<Receipt> receipts = this.entityManager
				.createQuery("SELECT r FROM Receipt r ", Receipt.class)
				.getResultList();
		this.entityManager.getTransaction().commit();
		
		return receipts;
	}
	
	@Override
	public Receipt findById(String id) {
		return this.entityManager.find(Receipt.class,id);
	}
	
	@Override
	public Long size() {
		return (long) this.findAll().size();
	}
	
	@Override
	public List<Receipt> findAllByUsername(String username) {
		this.entityManager.getTransaction().begin();
		List<Receipt> receipts = this.entityManager
				.createQuery("SELECT r FROM Receipt r where r.recipient.username=:username", Receipt.class)
				.setParameter("username",username)
				.getResultList();
		this.entityManager.getTransaction().commit();
		
		return receipts;
		
	}
}
