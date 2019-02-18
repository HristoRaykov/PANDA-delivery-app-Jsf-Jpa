package panda.repository;

import panda.domain.entities.Receipt;

import java.util.List;

public interface ReceiptRepository extends GenericRepository<Receipt,String> {
	
	
	List<Receipt> findAllByUsername(String username);
}
