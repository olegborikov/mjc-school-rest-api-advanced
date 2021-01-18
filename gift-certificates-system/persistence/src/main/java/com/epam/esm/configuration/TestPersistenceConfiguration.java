package com.epam.esm.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Class {@code TestPersistenceConfiguration} contains spring configuration for persistence subproject tests.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@Configuration
@Profile("dev")
@ComponentScan("com.epam.esm")
public class TestPersistenceConfiguration {

    @Value("UTF-8")
    private String encoding;
    @Value("classpath:script/gift_certificate_create.sql")
    private String giftCertificateCreate;
    @Value("classpath:script/gift_certificate_fill_up.sql")
    private String giftCertificateFillUp;
    @Value("classpath:script/gift_certificate_has_tag_create.sql")
    private String giftCertificateHasTagCreate;
    @Value("classpath:script/gift_certificate_has_tag_fill_up.sql")
    private String giftCertificateHasTagFillUp;
    @Value("classpath:script/tag_create.sql")
    private String tagCreate;
    @Value("classpath:script/tag_fill_up.sql")
    private String tagFillUp;

    /**
     * Create bean {@link DataSource} which will be used as data source.
     *
     * @return the hikari data source
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding(encoding)
                .addScript(giftCertificateCreate)
                .addScript(giftCertificateFillUp)
                .addScript(giftCertificateHasTagCreate)
                .addScript(giftCertificateHasTagFillUp)
                .addScript(tagCreate)
                .addScript(tagFillUp)
                .build();
    }

    /**
     * Create bean {@link JdbcTemplate} which will be used for queries to database.
     *
     * @param dataSource the data source
     * @return the jdbc template
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
