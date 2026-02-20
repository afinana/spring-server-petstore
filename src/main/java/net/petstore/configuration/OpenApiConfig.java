package net.petstore.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI petstoreOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Swagger Pet store")
                        .description("This is a sample server Pet store server.")
                        .version("1.0.6")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .contact(new Contact()
                                .email("apiteam@swagger.io")));
    }
}
