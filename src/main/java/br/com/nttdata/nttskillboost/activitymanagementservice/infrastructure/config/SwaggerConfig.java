package br.com.nttdata.nttskillboost.activitymanagementservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Service API")
                        .version("v1")
                        .description("API de gerenciamento de funcionários para NTTSkillBoost"));
    }
}
