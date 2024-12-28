package com.foreverchild.paystack_integration.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String[] allowedOrigins;

    public WebConfig() {
        Dotenv dotenv = Dotenv.load();
        this.allowedOrigins = dotenv.get("ALLOWED_ORIGINS", "http://localhost:9091").split(",");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedOrigins("http://localhost:3000","http://localhost:8080", "https://payment-integration-1-m6j4.onrender.com")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}