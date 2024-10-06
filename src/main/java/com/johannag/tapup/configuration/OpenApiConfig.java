package com.johannag.tapup.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${info.app.version}")
    private String infoAppVersion;

    @Value("${info.app.name}")
    private String infoAppName;

    /**
     * Configure the open api bean with application properties
     *
     * @return the configured open api config
     */

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(infoAppName)
                        .version(infoAppVersion)
                        .description("API for managing horse betting"));
    }
}
