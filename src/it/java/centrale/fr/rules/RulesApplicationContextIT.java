package centrale.fr.rules;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RulesApplicationContextIT {

	@TempDir
	public File temporaryFolder;

	@Test
	public void shouldContextLoads() {
	}

	@Test
	public void shouldRunApplication() {

		// GIVEN
		String inputFilePath = "src/test/resources/ads.json";

		String outputFilePath = new File(temporaryFolder, "out.json").getAbsolutePath();

		// WHEN
		String params = String.format("inputFilePath=%s " + "outputFilePath=%s ", inputFilePath, outputFilePath);

		try {
			RulesApplication.main(params.split(" "));
		} catch (Exception e) {
			fail();
		}

	}

}
