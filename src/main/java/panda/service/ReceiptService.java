package panda.service;

import panda.domain.entities.Package;
import panda.domain.models.service.ReceiptServiceModel;

import java.util.List;

public interface ReceiptService {
	
	
	void createReceipt(Package aPackage);
	
	List<ReceiptServiceModel> findAllReceiptsByUsername(String username);
	
	ReceiptServiceModel findReceiptById(String receiptId);
}
