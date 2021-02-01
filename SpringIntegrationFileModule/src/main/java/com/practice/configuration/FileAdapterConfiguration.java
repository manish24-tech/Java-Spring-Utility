package com.practice.configuration;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;

import com.practice.processor.FileProcessor;
import com.practice.processor.LastModifiedFileFilter;

/**
 * Configuration to poll and watch directories activity
 * Register necessary beans in spring IOC container automatically 
 * @author Manish Luste
 */
@SpringBootConfiguration
public class FileAdapterConfiguration {
	
	/** Watch directory - to watch add/update/delete file activities */
	//private static final String DIRECTORY = "D:\\resourcepkg";

	private static final String DIRECTORY = Paths.get("..", "SpringIntegrationFileModule", "src", "main", "resources", "files").toString();
	
	/** Defines methods for sending messages. */
	@Bean
	public MessageChannel fileInputChannel() {
		return new DirectChannel();
	}
	
	/** A payload transformer that copies a File's contents to a String. */
	@Bean
	public FileToStringTransformer fileToStringTransformer() {
		return new FileToStringTransformer();
	}

	/**Custom Bean that registerd in IOC containerit's purpose to print message of file with name and content  */
	@Bean
	public FileProcessor fileProcessor() {
		return new FileProcessor();
	}
	
	/**
	 * The main Integration DSL abstraction. 
	 * The StandardIntegrationFlow implementation (produced by IntegrationFlowBuilder)represents a container for the integration components, which will be registered in the application context. 
	 */
	@Bean
	public IntegrationFlow processFileFlow() {
		return IntegrationFlows
				.from("fileInputChannel")
				.transform(fileToStringTransformer())
				.handle("fileProcessor", "process").get();
	}
	
	/** Channel that return values from the annotated method may be of any type.*/
	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public MessageSource<File> fileReadingMessageSource() {
		
		// Take file and add filter to be process 
		CompositeFileListFilter<File> filters =new CompositeFileListFilter<>();
		filters.addFilter(new SimplePatternFileListFilter("*.txt"));
		filters.addFilter(new LastModifiedFileFilter());

		// Read file from watch directory and process with filter 
		FileReadingMessageSource source = new FileReadingMessageSource();
		source.setAutoCreateDirectory(true);
		source.setDirectory(new File(DIRECTORY));
		source.setFilter(filters);

		return source;
	}
}
