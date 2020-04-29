package centrale.fr.rules.model;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
	
	private String reference ;
	
	private boolean scam ;
	
	private List<String> rules = new ArrayList<>();
	
	
	

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public boolean isScam() {
		return scam;
	}

	public void setScam(boolean scam) {
		this.scam = scam;
	}

	public List<String> getRules() {
		return rules;
	}

	public void setRules(List<String> rules) {
		this.rules = rules;
	} 
	
	public void addRule(String rule) {
		this.rules.add(rule);
	}

	@Override
	public String toString() {
		return "ValidationResult [reference=" + reference + ", scam=" + scam + ", rules=" + rules + "]";
	}
	
	
	
	

}
