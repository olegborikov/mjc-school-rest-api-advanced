package com.epam.esm.configuration;

import com.epam.esm.converter.StringToOrderTypeConverter;
import com.epam.esm.converter.StringToSortTypeConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class {@code WebConfiguration} contains spring configuration for web subproject.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see WebMvcConfigurer
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * Add {@link Converter} and {@link Formatter} in addition to the ones
     * registered by default.
     *
     * @param registry the formatter registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToSortTypeConverter());
        registry.addConverter(new StringToOrderTypeConverter());
    }
}
