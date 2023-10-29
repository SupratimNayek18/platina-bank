package com.platinabank.loans;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Loans Microservice REST API Documentation",
				description = "Loans Microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Supratim Nayek",
						email = "supratimnayek@gmail.com"
				),
				license = @License(
						name = "Apache 2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "External Documentation"
		)
)
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
