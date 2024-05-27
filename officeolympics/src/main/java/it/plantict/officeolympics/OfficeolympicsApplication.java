package it.plantict.officeolympics;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Arrays;

@SpringBootApplication()
public class OfficeolympicsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfficeolympicsApplication.class, args);
	}

	@Bean
	public OpenAPI beOpenAPI() {
		return new OpenAPI()
				.components(new Components()
						.addSecuritySchemes("bearer-key",
								new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
										.in(SecurityScheme.In.HEADER).name("Authorization")))

				.info(new Info()
						.title("Office Olympics Resource Services")
						.version("1.0.0")
						.description("Office Olympics Resource Service")
						.termsOfService("https://www.plantict.it/")
						.license(new License().name("(C) Copyright - Plant ICT S.r.l.").url("https://www.plantict.it/"))
				)

				.addSecurityItem(
						new SecurityRequirement().addList("bearer-key", Arrays.asList("read", "write")));

	}
}
