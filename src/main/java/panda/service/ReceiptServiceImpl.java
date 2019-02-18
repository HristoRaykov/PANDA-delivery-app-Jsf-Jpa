package panda.service;

import org.modelmapper.ModelMapper;
import panda.domain.entities.Package;
import panda.domain.entities.Receipt;
import panda.domain.models.service.PackageServiceModel;
import panda.domain.models.service.ReceiptServiceModel;
import panda.domain.models.service.UserServiceModel;
import panda.repository.ReceiptRepository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ReceiptServiceImpl implements ReceiptService {
	
	private final ReceiptRepository receiptRepository;
	private final ModelMapper modelMapper;
	
	@Inject
	public ReceiptServiceImpl(ReceiptRepository receiptRepository, ModelMapper modelMapper) {
		this.receiptRepository = receiptRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public void createReceipt(Package aPackage) {
		Receipt receipt = new Receipt();
		receipt.setFee(new BigDecimal(aPackage.getWeight()*2.67));
		receipt.setIssuedOn(LocalDateTime.now());
		receipt.setRecipient(aPackage.getRecipient());
		receipt.setaPackage(aPackage);
		
		this.receiptRepository.save(receipt);
	}
	
	@Override
	public List<ReceiptServiceModel> findAllReceiptsByUsername(String username) {
		return Arrays.asList(this.modelMapper.map(this.receiptRepository.findAllByUsername(username),ReceiptServiceModel[].class));
	}
	
	@Override
	public ReceiptServiceModel findReceiptById(String receiptId){
		Receipt receipt = this.receiptRepository.findById(receiptId);
		ReceiptServiceModel receiptServiceModel = this.modelMapper.map(receipt,ReceiptServiceModel.class);
		receiptServiceModel.setaPackage(this.modelMapper.map(receipt.getaPackage(), PackageServiceModel.class));
		receiptServiceModel.setRecipient(this.modelMapper.map(receipt.getRecipient(), UserServiceModel.class));
		
		return receiptServiceModel;
	}
}
