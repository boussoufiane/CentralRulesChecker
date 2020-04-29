package centrale.fr.rules.enums;

public enum RulesNames {
	
	RULE_FIRSTNAME_LENGHT("rule::firstname::length"),
	RULE_LASTNAME_LENGHT("rule::lastname::length"),
	RULE_MAIL_ALPHA_RATE("rule:mail:alpha_rate"),
	RULE_MAIL_NUMBER_RATE("rule:mail:number_rate"),
	RULE_PRICE_QUOTATION_RATE("rule::price::quotation_rate"),
	RULE_REGISTER_NUMBER_BLACKLIST("rule::registernumber::blacklist")
	;
	
	private String value ;

	private RulesNames(String name) {
		this.value = name;
	}

	public String getValue() {
		return value;
	}
	
	
	
	
	
	

}
