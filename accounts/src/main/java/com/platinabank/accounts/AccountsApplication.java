package com.platinabank.accounts;

import com.platinabank.accounts.dto.ConfigProperties;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts Microservice REST API Documentation",
                description = "Platina Bank Accounts Microservice REST API Documentation",
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
@EnableConfigurationProperties(value = {ConfigProperties.class})
@EnableFeignClients
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
