package centrale.fr.rules.service;

import centrale.fr.rules.model.Advertisement;
import centrale.fr.rules.model.ValidationResult;

public interface RulesValidator {

	ValidationResult validate(Advertisement ad);

}
