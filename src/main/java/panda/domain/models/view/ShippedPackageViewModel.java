package panda.domain.models.view;

public class ShippedPackageViewModel {
	
	private String id;
	private String description;
	private Double weight;
	private String estimateDeliveryDate;
	private String recipient;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getWeight() {
		return weight;
	}
	
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	
	public String getEstimateDeliveryDate() {
		return estimateDeliveryDate;
	}
	
	public void setEstimateDeliveryDate(String estimateDeliveryDate) {
		this.estimateDeliveryDate = estimateDeliveryDate;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
}
