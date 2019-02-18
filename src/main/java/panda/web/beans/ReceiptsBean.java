package panda.web.beans;

import org.modelmapper.ModelMapper;
import panda.domain.models.service.ReceiptServiceModel;
import panda.domain.models.view.ReceiptsListViewModel;
import panda.service.ReceiptService;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class ReceiptsBean {
	
	private List<ReceiptsListViewModel> receipts;
	
	private final ReceiptService receiptService;
	private final ModelMapper modelMapper;
	
	@Inject
	public ReceiptsBean(ReceiptService receiptService, ModelMapper modelMapper) {
		this.receiptService = receiptService;
		this.modelMapper = modelMapper;
		this.initReceipts();
	}

	
	private void initReceipts() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		String username = (String) session.getAttribute("username");
		List<ReceiptServiceModel> receiptServiceModels = this.receiptService.findAllReceiptsByUsername(username);
		
		receipts = receiptServiceModels.stream()
				.map(receiptServiceModel -> {
					String recipientName = receiptServiceModel.getRecipient().getUsername();
					LocalDateTime issuedOn = receiptServiceModel.getIssuedOn();
					ReceiptsListViewModel receiptsListViewmodel = this.modelMapper.map(receiptServiceModel, ReceiptsListViewModel.class);
					receiptsListViewmodel.setRecipient(recipientName);
					receiptsListViewmodel.setIssuedOn(issuedOn.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					return receiptsListViewmodel;
				})
				.collect(Collectors.toList());
		
		System.out.println();
	}
	
	public List<ReceiptsListViewModel> getReceipts() {
		return receipts;
	}
	
	public void setReceipts(List<ReceiptsListViewModel> receipts) {
		this.receipts = receipts;
	}
}
