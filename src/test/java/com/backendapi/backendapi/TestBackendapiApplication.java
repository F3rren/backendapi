package com.backendapi.backendapi;

import org.springframework.boot.SpringApplication;

public class TestBackendapiApplication {

	public static void main(String[] args) {
		SpringApplication.from(BackendapiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
