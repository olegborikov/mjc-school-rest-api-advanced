package com.epam.esm.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Class {@code ServiceConfiguration} contains spring configuration for service subproject.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EnableTransactionManagement
public class ServiceConfiguration {

    /**
     * Create bean {@link ModelMapper} which will be used to parse entity to dto and opposite.
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return mapper;
    }
}
