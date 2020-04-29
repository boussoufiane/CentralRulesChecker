package centrale.fr.rules.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import centrale.fr.rules.model.Vehicle;


public class QuotationServiceImpl implements QuotationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QuotationServiceImpl.class);


	@Override
	public double getQuotation(Vehicle vehicle) {	
		LOGGER.info("Calling Quotation service  ...");

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			LOGGER.error("Error when calling quotation service" , e);
		}
		return 35000;
	}

}
