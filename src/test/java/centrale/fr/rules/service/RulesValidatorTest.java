package centrale.fr.rules.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import centrale.fr.rules.RulesProperties;
import centrale.fr.rules.enums.RulesNames;
import centrale.fr.rules.model.Advertisement;
import centrale.fr.rules.model.Contacts;
import centrale.fr.rules.model.ValidationResult;
import centrale.fr.rules.model.Vehicle;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class RulesValidatorTest {
	
	@InjectMocks
	private RulesValidatorImpl beanUnderTest; 
	
	@Mock
	private QuotationService quotationServiceMock;
	
	@Mock
	private BlacklistService blacklistServiceMock ;
	
	@Mock
	private RulesProperties rulesPropertiesMock;
	
	@BeforeEach
	public void setUp() {
		Mockito.when(rulesPropertiesMock.getFirstNameLenght()).thenReturn(2);
		Mockito.when(rulesPropertiesMock.getLastNameLenght()).thenReturn(2);
		Mockito.when(rulesPropertiesMock.getMailAlphaRate()).thenReturn(70d);
		Mockito.when(rulesPropertiesMock.getMailNumberRate()).thenReturn(30d);
		Mockito.when(rulesPropertiesMock.getQuotationRatePercentage()).thenReturn(20d);
		
		Mockito.when(quotationServiceMock.getQuotation(Mockito.any(Vehicle.class))).thenReturn(35000d);
		
		Mockito.when(blacklistServiceMock.isRegisterNumberBlacklisted(Mockito.anyString())).thenAnswer(new Answer<Boolean>() {
		    @Override
		    public Boolean answer(InvocationOnMock invocation) throws Throwable {
		    	String input = (String) invocation.getArguments()[0];
		    	if(input.equals("A000")) {
		    		return true ;
		    	}
		        return false;
		    }
		});
		

		

	}
	
	@Test
	public void shouldValidateRulesWithSuccess() {
		
		//GIVEN
		Advertisement ad = buildValidAdvertissment();
		
		//WHEN
		ValidationResult validationResult = beanUnderTest.validate(ad);
		
		//THEN
		assertFalse(validationResult.isScam());
		Assertions.assertThat(validationResult.getRules()).isEmpty();
		Assertions.assertThat(validationResult.getReference()).isEqualTo(ad.getReference());

			
	}
	
	@Test
	public void shouldNotValidateWhenFirstNameRuleKO() {
		
		//GIVEN
		Advertisement ad = buildValidAdvertissment();
		ad.getContacts().setFirstName("a");
		
		//WHEN
		ValidationResult validationResult = beanUnderTest.validate(ad);
		
		//THEN
		assertTrue(validationResult.isScam());
		Assertions.assertThat(validationResult.getRules()).containsOnly(RulesNames.RULE_FIRSTNAME_LENGHT.getValue());
		
	}
	
	@Test
	public void shouldNotValidateWhenLastNameRuleKO() {
		
		//GIVEN
		Advertisement ad = buildValidAdvertissment();
		ad.getContacts().setLastName("ab");
		
		//WHEN
		ValidationResult validationResult = beanUnderTest.validate(ad);
		
		//THEN
		assertTrue(validationResult.isScam());
		Assertions.assertThat(validationResult.getRules()).containsOnly(RulesNames.RULE_LASTNAME_LENGHT.getValue());
		
	}
	
	@Test
	public void shouldNotValidateWhenMailAlphaRateRuleKO() {
		
		//GIVEN
		Advertisement ad = buildValidAdvertissment();
		ad.getContacts().setEmail("a1ert#####@toto.com");
		
		//WHEN
		ValidationResult validationResult = beanUnderTest.validate(ad);
		
		//THEN
		assertTrue(validationResult.isScam());
		Assertions.assertThat(validationResult.getRules()).containsOnly(RulesNames.RULE_MAIL_ALPHA_RATE.getValue());
		
	}
	
	@Test
	public void shouldNotValidateWhenMailNumberRateRuleKO() {
		
		//GIVEN
		Advertisement ad = buildValidAdvertissment();
		ad.getContacts().setEmail("1234tyuiop@toto.com");
		
		//WHEN
		ValidationResult validationResult = beanUnderTest.validate(ad);
		
		//THEN
		assertTrue(validationResult.isScam());
		Assertions.assertThat(validationResult.getRules()).containsOnly(RulesNames.RULE_MAIL_NUMBER_RATE.getValue());
		
	}
	
	@Test
	public void shouldNotValidateWhenPriceQuotationRateRuleKO() {
		
		//GIVEN
		Advertisement ad = buildValidAdvertissment();
		ad.setPrice(100);
		
		//WHEN
		ValidationResult validationResult = beanUnderTest.validate(ad);
		
		//THEN
		assertTrue(validationResult.isScam());
		Assertions.assertThat(validationResult.getRules()).containsOnly(RulesNames.RULE_PRICE_QUOTATION_RATE.getValue());
		
	}
	
	@Test
	public void shouldNotValidateWhenRegisterNumberBlackListRuleKO() {
		
		//GIVEN
		Advertisement ad = buildValidAdvertissment();
		ad.getVehicle().setRegisterNumber("A000");
		
		//WHEN
		ValidationResult validationResult = beanUnderTest.validate(ad);
		
		//THEN
		assertTrue(validationResult.isScam());
		Assertions.assertThat(validationResult.getRules()).containsOnly(RulesNames.RULE_REGISTER_NUMBER_BLACKLIST.getValue());
		
	}

	private Advertisement buildValidAdvertissment() {
		Advertisement ad = new Advertisement();
		ad.setPrice(35000);
		ad.setReference("A125");
		
		Contacts contacts = new Contacts();
		contacts.setFirstName("Yassine");
		contacts.setLastName("Yassine");
		contacts.setEmail("yassineBoussou124@gmail.com");
		ad.setContacts(contacts );
		
		Vehicle vehicle = new Vehicle();
		vehicle.setRegisterNumber("A235TGH");
		ad.setVehicle(vehicle);
		return ad;
	}

}
