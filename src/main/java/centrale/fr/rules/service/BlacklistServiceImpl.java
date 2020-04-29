package centrale.fr.rules.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlacklistServiceImpl implements BlacklistService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BlacklistServiceImpl.class);

	
	@Override
	public boolean isRegisterNumberBlacklisted(String registerNumber) {
		LOGGER.info("Calling BlackList service checker ...");
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			LOGGER.error("Error when calling blackList service" , e);
		}
		return "AA123AA".equals(registerNumber) ; 	
	}

}
