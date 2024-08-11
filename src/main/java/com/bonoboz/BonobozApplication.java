package com.bonoboz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bonoboz.model.User;
import com.bonoboz.service.UserService;

@SpringBootApplication
public class BonobozApplication {


	public static void main(String[] args) {
		SpringApplication.run(BonobozApplication.class, args);
		System.out.println("started___");
	}


}
