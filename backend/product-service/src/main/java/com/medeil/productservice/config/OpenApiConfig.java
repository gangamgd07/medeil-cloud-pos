package com.medeil.productservice.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	
	OpenAPI ProductAPI() {
		return new OpenAPI().info(new Info()
									  .title("Medeil Product Service API")
									  .version("1.0")
									  .description("Product Service APIs"));
	}

}
