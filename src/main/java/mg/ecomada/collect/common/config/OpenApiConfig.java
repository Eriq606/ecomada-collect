package mg.ecomada.collect.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoMada Collect API")
                        .version("1.0.0")
                        .description("API REST de gestion intelligente des déchets et de valorisation circulaire à Madagascar.")
                        .contact(new Contact()
                                .name("Support EcoMada")
                                .email("support@ecomada.mg")));
    }
}
