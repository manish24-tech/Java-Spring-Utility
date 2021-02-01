package com.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring integration module is a part of spring boot messaging module to keep watch activities from particular directory
 * Configuration - register messaging module configuration in IOC container
 * Processor - process watch activities of file: add,update,copy,move,delete
 * Purpose - Replacement of Java7 File watcher
 * @author Manish.Luste
 *
 */
@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}
