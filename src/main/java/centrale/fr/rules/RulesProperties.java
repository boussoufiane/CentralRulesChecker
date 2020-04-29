package centrale.fr.rules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RulesProperties {
	
	@Value("${rule.price.quotation_rate}")
	private double quotationRatePercentage ;
	
	@Value("${rule.firstname.length}")
	private int firstNameLenght ;
	
	@Value("${rule.lastName.length}")
	private int lastNameLenght ;
	
	@Value("${rule.mail.alpha_rate}")
	private double mailAlphaRate ;
	
	@Value("${rule.mail.number_rate}")
	private double mailNumberRate  ;

	public double getQuotationRatePercentage() {
		return quotationRatePercentage;
	}

	

	public int getFirstNameLenght() {
		return firstNameLenght;
	}

	

	public int getLastNameLenght() {
		return lastNameLenght;
	}

	

	public double getMailAlphaRate() {
		return mailAlphaRate;
	}

	

	public double getMailNumberRate() {
		return mailNumberRate;
	}

	
	
	

}
