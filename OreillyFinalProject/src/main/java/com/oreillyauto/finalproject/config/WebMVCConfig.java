package com.oreillyauto.finalproject.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebMVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());

        super.configureMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter props = new MappingJackson2HttpMessageConverter();

        /*
         * Registering a hibernate module to the JsonUtil 
         * Object Mapper makes the Object Mapper Hibernate
         * Aware
         */
        /*        JsonUtil.registerModule(new Hibernate5Module());
        
        //copy of JsonUtil's objectMapper instance
        props.setObjectMapper(JsonUtil.copyOfObjectMapper());*/
        return props;
    }

    @Bean
    public FormattingConversionServiceFactoryBean conversionService() {
        return new FormattingConversionServiceFactoryBean();
    }

    @Bean
    public DomainClassConverter<?> domainClassConverter() {
        return new DomainClassConverter<FormattingConversionService>(conversionService().getObject());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31555926);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/DoNothing").setViewName("donothing");
        registry.addViewController("/ping").setViewName("donothing");
        registry.addViewController("/DoNothing.do").setViewName("donothing");
        registry.addViewController("/403").setViewName("403");
    }

    @Bean
    public MappedInterceptor mappedInterceptor() {
        String[] mapInclude = { "/**" };
        String[] mapExclude = { "/resources/**" };
        WebContentInterceptor interceptor = new WebContentInterceptor();
        interceptor.setCacheSeconds(0);
        return new MappedInterceptor(mapInclude, mapExclude, interceptor);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
    }
    
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer props = new TilesConfigurer();
        props.setDefinitions("/WEB-INF/views.xml", "/WEB-INF/views/page-views.xml", "/WEB-INF/layouts/layout-views.xml", 
                             "/WEB-INF/views/**/views.xml");
        return props;
    }

    @Bean
    public TilesViewResolver tilesViewResolver() {
        TilesViewResolver props = new TilesViewResolver();
        props.setViewClass(TilesView.class);
        return props;
    }
}
