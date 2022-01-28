package com.readingisgood.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
