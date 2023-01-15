package com.gordwilling.futurestay.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Configuration
public class ConfigJson {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        var javaTimeModule = new JavaTimeModule()
                .addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE)
                .addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);

        return new Jackson2ObjectMapperBuilder()
                .modulesToInstall(javaTimeModule)
                .serializationInclusion(Include.NON_NULL)
                .autoDetectGettersSetters(true)
                .failOnUnknownProperties(true)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToEnable(
                        DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                        SerializationFeature.INDENT_OUTPUT
                );
    }
}
