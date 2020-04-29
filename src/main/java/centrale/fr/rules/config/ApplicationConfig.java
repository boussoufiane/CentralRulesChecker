package centrale.fr.rules.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import centrale.fr.rules.service.BlacklistServiceImpl;
import centrale.fr.rules.service.QuotationServiceImpl;
import centrale.fr.rules.service.QuotationService;
import centrale.fr.rules.service.RulesValidator;
import centrale.fr.rules.service.RulesValidatorImpl;
import centrale.fr.rules.RulesProperties;
import centrale.fr.rules.service.BlacklistService;

@Configuration
public class ApplicationConfig {
	
	@Bean
	public RulesValidator RulesValidator( RulesProperties rulesProperties ) {
		return new RulesValidatorImpl(quotationService(), blacklistService(), rulesProperties) ;
	}
	
	@Bean
	public BlacklistService blacklistService() {
		return new BlacklistServiceImpl();
	}
	
	@Bean
	public QuotationService quotationService() {
		return new QuotationServiceImpl();
	}

}
