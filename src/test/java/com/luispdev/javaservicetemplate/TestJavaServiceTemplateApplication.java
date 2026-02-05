package com.luispdev.javaservicetemplate;

import org.springframework.boot.SpringApplication;

public class TestJavaServiceTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.from(JavaServiceTemplateApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
