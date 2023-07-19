package com.fourbench.gatewayservice.configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition
public class ApiDocumentConfig {
    @Bean
    public OpenAPI openAPI() {
        Contact contact = new Contact();
        contact.setEmail("mluste94@gmail.com");
        contact.setName("Manish Luste");
        contact.setUrl("https://sites.google.com/view/techies-report");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Gateway API")
                .contact(contact)
                .version("1.0")
                .description("Gateway API v1.0 Documentation.")
                .termsOfService("") // define terms and service url link
                .license(mitLicense);

        return new OpenAPI()
                .info(info);
    }
}
