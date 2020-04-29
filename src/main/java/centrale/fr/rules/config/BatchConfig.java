package centrale.fr.rules.config;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

import centrale.fr.rules.model.Advertisement;
import centrale.fr.rules.model.ValidationResult;
import centrale.fr.rules.service.RulesValidator;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	@StepScope
	public JsonItemReader<Advertisement> jsonItemReader(
			@Value("#{jobParameters['inputFilePath']}") String inputFilePath) {
		ObjectMapper objectMapper = new ObjectMapper();
		// configure the objectMapper as required
		JacksonJsonObjectReader<Advertisement> jsonObjectReader = new JacksonJsonObjectReader<>(Advertisement.class);
		jsonObjectReader.setMapper(objectMapper);

		return new JsonItemReaderBuilder<Advertisement>().jsonObjectReader(jsonObjectReader)
				//.resource(new ClassPathResource("ads.json"))
				.resource(new FileSystemResource(inputFilePath))
				.name("advertissementJsonItemReader").build();
	}

	@Bean
	@StepScope
	public AdvertissementProcessor processor(RulesValidator rulesValidator) {
		return new AdvertissementProcessor(rulesValidator);
	}


	@Bean
	@StepScope
	public JsonFileItemWriter<ValidationResult> jsonItemWriter(
			@Value("#{jobParameters['outputFilePath']}") String outputFilePath) {

		return new JsonFileItemWriterBuilder<ValidationResult>()
				.name("jsonItemWriter")
				.jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
				.resource(new FileSystemResource(outputFilePath))
				.build();

	}

	@Bean
	public Step step(JsonItemReader<Advertisement> jsonItemReader, AdvertissementProcessor processor , ItemWriter<ValidationResult> jsonItemWriter) {
		return stepBuilderFactory.get("rulesStep").<Advertisement, ValidationResult>chunk(1).reader(jsonItemReader)
				.processor(processor).writer(jsonItemWriter).build();
	}

	@Bean
	public Job job(JsonItemReader<Advertisement> jsonItemReader, AdvertissementProcessor processor  , ItemWriter<ValidationResult> jsonItemWriter)
			throws Exception {
		return jobBuilderFactory.get("rulesJob").start(step(jsonItemReader, processor , jsonItemWriter)).build();
	}

}
