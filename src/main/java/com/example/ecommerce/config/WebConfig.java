package com.example.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Când utilizatorul accesează runda principală (ex: librariabelciug.railway.app/),
        // Spring Boot va încărca direct fișierul products.html
        registry.addViewController("/").setViewName("forward:/products.html");
    }
}