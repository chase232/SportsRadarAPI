package com.oreillyauto.finalproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@ComponentScan("com.oreillyauto")
public class WebApplicationConfig {

    /*
     * Configuration Property Files
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        final PropertySourcesPlaceholderConfigurer props = new PropertySourcesPlaceholderConfigurer();
        props.setLocation(new ClassPathResource("project.properties"));
        return props;
    }

    /*
     * Points to resource bundle for messages
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        final ResourceBundleMessageSource props = new ResourceBundleMessageSource();
        props.setBasename("locale/messages");
        return props;
    }

    /*
     * JSR-303 Hibernate Validator
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        final LocalValidatorFactoryBean props = new LocalValidatorFactoryBean();
        props.setValidationMessageSource(messageSource());
        return props;
    }
}
