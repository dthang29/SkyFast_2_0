package com.example.skyfast_2_0.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Chuyển đổi String -> LocalDate
        Converter<String, LocalDate> toLocalDate =
                context -> (context.getSource() == null || context.getSource().isEmpty()) ? null :
                        LocalDate.parse(context.getSource(), DateTimeFormatter.ISO_DATE);

        // Chuyển đổi LocalDate -> String
        Converter<LocalDate, String> fromLocalDate =
                context -> context.getSource() == null ? null : context.getSource().toString();

        modelMapper.addConverter(toLocalDate);
        modelMapper.addConverter(fromLocalDate);

        return modelMapper;
    }
}
