package com.epam.esm.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Class {@code ApplicationInitializer} is deployment descriptor. It refers
 * to a configuration file for an artifact that is deployed to Tomcat.
 *
 * @author Oleg Borikov
 * @version 1.0
 * @see WebApplicationInitializer
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    /**
     * Configure the given {@link ServletContext} with any servlets, filters, listeners
     * context-params and attributes necessary for initializing this web application.
     * See examples {@linkplain WebApplicationInitializer above}.
     *
     * @param servletContext the {@code ServletContext} to initialize
     * @throws ServletException if any call against the given {@code ServletContext}
     *                          throws a {@code ServletException}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(WebConfiguration.class);
        applicationContext.setServletContext(servletContext);
        servletContext.addListener(new ContextLoaderListener(applicationContext));
        servletContext.setInitParameter("spring.profiles.active", "prod");
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet",
                new DispatcherServlet(applicationContext));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }
}
