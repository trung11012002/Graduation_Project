package com.cinema.notification.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    //    @Bean
    //    CorsWebFilter corsWebFilter(){
    //        CorsConfiguration corsConfiguration = new CorsConfiguration();
    //        corsConfiguration.setAllowedOrigins(List.of("*"));
    //        corsConfiguration.setAllowedHeaders(List.of("*"));
    //        corsConfiguration.setAllowedMethods(List.of("*"));
    //
    //        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    //        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    //
    //        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    //    }
}
