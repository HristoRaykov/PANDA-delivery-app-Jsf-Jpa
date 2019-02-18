package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.models.service.ReceiptServiceModel;
import panda.domain.models.view.ReceiptDetailsViewModel;
import panda.service.ReceiptService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;

@Named
public class ReceiptDetailsBean {
	
	private ReceiptDetailsViewModel receiptDetailsViewModel;
	
	private final ReceiptService receiptService;
	private final ModelMapper modelMapper;
	
	@Inject
	public ReceiptDetailsBean(ReceiptService receiptService, ModelMapper modelMapper) {
		this.receiptService = receiptService;
		this.modelMapper = modelMapper;
		this.initReceipt();
	}
	
	private void initReceipt() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String receiptId = request.getParameter("id");
		ReceiptServiceModel receiptServiceModel = this.receiptService.findReceiptById(receiptId);
		
		receiptDetailsViewModel = this.modelMapper.map(receiptServiceModel,ReceiptDetailsViewModel.class);
		receiptDetailsViewModel.setDeliveryAddress(receiptServiceModel.getaPackage().getShippingAddress());
		receiptDetailsViewModel.setRecipientName(receiptServiceModel.getRecipient().getUsername());
		receiptDetailsViewModel.setIssuedOn(receiptServiceModel.getIssuedOn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}
	
	public ReceiptDetailsViewModel getReceiptDetailsViewModel() {
		return receiptDetailsViewModel;
	}
	
	public void setReceiptDetailsViewModel(ReceiptDetailsViewModel receiptDetailsViewModel) {
		this.receiptDetailsViewModel = receiptDetailsViewModel;
	}
	
	
}
