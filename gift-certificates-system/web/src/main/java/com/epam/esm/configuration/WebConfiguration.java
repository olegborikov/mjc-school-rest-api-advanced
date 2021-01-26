package com.epam.esm.configuration;

import com.epam.esm.converter.StringToOrderTypeConverter;
import com.epam.esm.converter.StringToSortTypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class {@code WebConfiguration} contains spring configuration for web subproject.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see WebMvcConfigurer
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {

    @Value("UTF-8")
    private String encoding;
    @Value("classpath:i18n/exception_message")
    private String fileName;

    /**
     * Add {@link Converter} and {@link Formatter} in addition to the ones registered by default.
     *
     * @param registry the formatter registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortTypeConverter());
        registry.addConverter(new StringToOrderTypeConverter());
    }

    /**
     * Create bean {@link MessageSource} which will be used to get info from properties files.
     *
     * @return the message source
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(fileName);
        messageSource.setDefaultEncoding(encoding);
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        return localValidatorFactoryBean;
    }
}
