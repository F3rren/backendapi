package com.backendapi.backendapi;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backendapi.model.entity.RoleEntity;
import com.backendapi.model.entity.UserEntity;
import com.backendapi.service.UserServiceImpl;

@SpringBootApplication
public class BackendapiApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(BackendapiApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService)
	{
		return args-> {
			roleService.save(new RoleEntity(null, "ROLE_USER"));
			roleService.save(new RoleEntity(null, "ROLE_ADMIN"));

			userService.save(new UserEntity(null, "rossi", "password", new ArrayList<>()));
			userService.save(new UserEntity(null, "bianchi", "password", new ArrayList<>()));

			userService.addRoleToUser("rossi", "ROLE_USER");
			userService.addRoleToUser("rossi", "ROLE_ADMIN");
			userService.addRoleToUser("bianchi", "ROLE_USER");

		};



	}

}
