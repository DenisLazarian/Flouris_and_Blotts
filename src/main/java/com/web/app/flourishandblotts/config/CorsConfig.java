package com.web.app.flourishandblotts.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
//    public static void main(String[] args) {
//        SpringApplication.run(CorsConfig.class, args);
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/user/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders(
                                "Authorization",
                                "Content-Type",
                                "Access-Control-Allow-Origin"
                        )
                        .maxAge(3600);

                registry.addMapping("/file/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("Authorization", "Content-Type")
                        .maxAge(3600);
                registry.addMapping("/book/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                        .allowedHeaders("Authorization", "Content-Type")
                        .maxAge(3600);

                registry.addMapping("/login")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
//                        .exposedHeaders("*")
//                        .allowedHeaders("Authorization", "Content-Type")
//                        .allowedHeaders("Access-Control-Allow-Origin", "*");
//                        .maxAge(3600);
            }
        };
    }
}
