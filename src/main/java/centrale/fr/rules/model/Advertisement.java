package centrale.fr.rules.model;

import java.util.Date;
import java.util.List;

public class Advertisement {
	

	
	private int price ; 
	
	private String reference ;
	
	private Vehicle vehicle ; 
	
	private Contacts contacts;
	
	private Date creationDate ; 
	
	private List<String> publicationOptions ;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<String> getPublicationOptions() {
		return publicationOptions;
	}

	public void setPublicationOptions(List<String> publicationOptions) {
		this.publicationOptions = publicationOptions;
	}
	
	
	
	
	
	

}
