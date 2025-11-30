package com.phindulo_dev.student_gradle_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class StudentGradleTrackerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(StudentGradleTrackerApplication.class)
				.properties("server.port=8081")  // Set port here
				.run(args);	}

}
