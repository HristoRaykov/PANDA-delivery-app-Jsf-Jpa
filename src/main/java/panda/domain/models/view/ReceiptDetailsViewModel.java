package panda.domain.models.view;

import java.math.BigDecimal;

public class ReceiptDetailsViewModel {
	
	private String id;
	private String issuedOn;
	private String deliveryAddress;
	private Double packageWeight;
	private String packageDescription;
	private BigDecimal fee;
	private String recipientName;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getIssuedOn() {
		return issuedOn;
	}
	
	public void setIssuedOn(String issuedOn) {
		this.issuedOn = issuedOn;
	}
	
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public Double getPackageWeight() {
		return packageWeight;
	}
	
	public void setPackageWeight(Double packageWeight) {
		this.packageWeight = packageWeight;
	}
	
	public String getPackageDescription() {
		return packageDescription;
	}
	
	public void setPackageDescription(String packageDescription) {
		this.packageDescription = packageDescription;
	}
	
	public BigDecimal getFee() {
		return fee;
	}
	
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	public String getRecipientName() {
		return recipientName;
	}
	
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
}
