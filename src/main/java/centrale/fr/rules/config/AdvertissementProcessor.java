package centrale.fr.rules.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import centrale.fr.rules.model.Advertisement;
import centrale.fr.rules.model.ValidationResult;
import centrale.fr.rules.service.RulesValidator;

public class AdvertissementProcessor implements ItemProcessor<Advertisement, ValidationResult>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdvertissementProcessor.class);
	
	
	private RulesValidator rulesValidator;
	
	public AdvertissementProcessor(RulesValidator rulesValidator) {
		super();
		this.rulesValidator = rulesValidator;
	}


	@Override
	public ValidationResult process(Advertisement ad) throws Exception {		
		  ValidationResult validationResult = rulesValidator.validate(ad);
		  LOGGER.info(validationResult.toString());
		  return validationResult;
	}
	

}
