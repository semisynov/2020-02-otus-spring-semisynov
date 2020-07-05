package ru.semisynov.otus.spring.homework11.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationTestConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
