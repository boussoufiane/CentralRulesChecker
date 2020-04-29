package centrale.fr.rules.service;



import centrale.fr.rules.RulesProperties;
import centrale.fr.rules.enums.RulesNames;
import centrale.fr.rules.model.Advertisement;
import centrale.fr.rules.model.ValidationResult;
import centrale.fr.rules.model.Vehicle;


public class RulesValidatorImpl implements RulesValidator {

	private QuotationService quotationService;
	

	private BlacklistService blacklistService;
	
	private RulesProperties rulesProperties ;

	
	public RulesValidatorImpl(QuotationService quotationService, BlacklistService blacklistService,
			RulesProperties rulesProperties) {
		super();
		this.quotationService = quotationService;
		this.blacklistService = blacklistService;
		this.rulesProperties = rulesProperties;
	}

	@Override
	public ValidationResult validate(Advertisement ad) {
		ValidationResult validationResult = new ValidationResult() ; 

		validationResult.setReference(ad.getReference());
		
		if(!validateFirstName(ad.getContacts().getFirstName())) {
			validationResult.getRules().add(RulesNames.RULE_FIRSTNAME_LENGHT.getValue());
		}
		
		if(!validateLastName(ad.getContacts().getLastName())) {
			validationResult.getRules().add(RulesNames.RULE_LASTNAME_LENGHT.getValue());
		}
		
		if(!validateMailAlphaRate(ad.getContacts().getEmail())) {
			validationResult.getRules().add(RulesNames.RULE_MAIL_ALPHA_RATE.getValue());
		}
		
		if(!validateMailNumberRate(ad.getContacts().getEmail())) {
			validationResult.getRules().add(RulesNames.RULE_MAIL_NUMBER_RATE.getValue());
		}
		
		if(!validatePriceQuotation(ad.getVehicle() , ad.getPrice())) {
			validationResult.getRules().add(RulesNames.RULE_PRICE_QUOTATION_RATE.getValue());
		}
		
		if(blacklistService.isRegisterNumberBlacklisted(ad.getVehicle().getRegisterNumber())) {
			validationResult.getRules().add(RulesNames.RULE_REGISTER_NUMBER_BLACKLIST.getValue());
		}
				
		if(validationResult.getRules().size() > 0) {
			validationResult.setScam(true);
		}
		return validationResult ; 
	}
	
	private boolean validateLastName(String lastName) {
		return lastName.length() > rulesProperties.getLastNameLenght() ;
	}

	private boolean validateFirstName(String firstName) {
		return firstName.length() > rulesProperties.getFirstNameLenght() ;
	}
	
	private boolean validateMailAlphaRate(String mail) {
	
		String split_first = mail.split("[@._]")[0];
		double rate = (double) countAlphaNumeric(split_first) /(double)  split_first.length() * 100;
		if(rate <= rulesProperties.getMailAlphaRate()) {
			return false ; 
		}
		return true ;
	}
	
	
	private boolean validateMailNumberRate(String mail) {
		
		String split_first = mail.split("[@._]")[0];
		double rate = (double) countDigit(split_first) /(double)  split_first.length() * 100;
		if(rate >= rulesProperties.getMailNumberRate()) {
			return false ; 
		}
		return true ;
	}

	private int countAlphaNumeric(String input) {
		int count = 0 ; 
		for (int i = 0; i < input.length(); i++) {
	         if (Character.isLetterOrDigit((input.charAt(i))))
	            count++;
	      }
		return count;
	}
	
	private int countDigit(String input) {
		int count = 0 ; 
		for (int i = 0; i < input.length(); i++) {
	         if (Character.isDigit((input.charAt(i))))
	            count++;
	      }
		return count;
	}

	private boolean validatePriceQuotation(Vehicle vehicle , int price) {
		double quotation = quotationService.getQuotation(vehicle);
		double minPrice = quotation * (1 - rulesProperties.getQuotationRatePercentage() / 100);
		double maxPrice = quotation * (1 + rulesProperties.getQuotationRatePercentage() / 100);
		if(!(price <= maxPrice && price >= minPrice)) {
			return false ; 
		}
		return true ; 
	}

}
