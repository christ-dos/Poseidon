package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that start the application
 *
 * @author Christine Duarte
 */
@SpringBootApplication
public class Application {
	/**
	 * Method main that initiate the application
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
