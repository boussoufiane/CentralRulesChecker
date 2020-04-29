package centrale.fr.rules;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.junit.rules.TemporaryFolder;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import centrale.fr.rules.config.ApplicationConfig;
import centrale.fr.rules.config.BatchConfig;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { BatchConfig.class, RulesApplication.class, RulesProperties.class })
@SpringBatchTest
@EnableAutoConfiguration
@PropertySource(value = { "application.properties" })
public class RulesApplicationBatchIT {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@TempDir
	public File temporaryFolder;

	@AfterEach
	public void cleanUp() {
		jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	public void shouldExecuteJobWithSuccess() throws Exception {
		// given
		JobParametersBuilder paramsBuilder = new JobParametersBuilder();
		paramsBuilder.addString("inputFilePath", "src/test/resources/ads.json");
		File output = new File(temporaryFolder, "out.json");
		paramsBuilder.addString("outputFilePath", output.getAbsolutePath());

		JobParameters defaultJobParameter = paramsBuilder.toJobParameters();

		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameter);

		// then

		JobInstance actualJobInstance = jobExecution.getJobInstance();
		ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

		FileSystemResource expectedResult = new FileSystemResource(output.getAbsolutePath());
		FileSystemResource actualResult = new FileSystemResource("src/test/resources/expectedOutput.json");

		assertThat(actualJobInstance.getJobName()).isEqualTo("rulesJob");
		assertThat(actualJobExitStatus.getExitCode()).isEqualTo("COMPLETED");
		AssertFile.assertFileEquals(expectedResult, actualResult);

	}

}
