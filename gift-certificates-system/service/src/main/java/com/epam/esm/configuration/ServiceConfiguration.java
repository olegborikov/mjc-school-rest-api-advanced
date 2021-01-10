package com.epam.esm.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Class {@code ServiceConfiguration} contains spring configuration for service subproject.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Configuration
@ComponentScan("com.epam.esm")
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

    /**
     * Create bean {@link TransactionManager} which will be used to manage transactions.
     *
     * @param dataSource the data source
     * @return the transaction manager
     */
    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
