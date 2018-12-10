package com.oreillyauto.finalproject.config;

import java.util.EnumSet;
import java.util.ResourceBundle;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

public class WebConfig implements WebApplicationInitializer {

    public static final String PROJECT_NAME;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("app");
        PROJECT_NAME = bundle.getString("project.name");
    }
    
    @Override
    public void onStartup(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        //Application Name
        rootContext.setDisplayName(PROJECT_NAME);

        /*Add the appName to the MDC to configure LogBack (import org.sl4j.MDC)
         * Uncomment out line below to log to file(see README.txt) 
         *
         * MDC.put("appName", PROJECT_NAME)
         */

        //Configuration Classes
        rootContext.register(WebApplicationConfig.class);

        //Springs ContextLoadListener
        container.addListener(new ContextLoaderListener(rootContext));
        
        //Reads request input using UTF-8 encoding
        FilterRegistration characterEncodingFilter = container.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        //Spring Security Filter Chain: Necessary for Springs PreAuthenticated Security
        FilterRegistration springSecurityFilterChain = container.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
        springSecurityFilterChain.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        //tuckey url rewrite filter
        FilterRegistration urlRewriteFilter = container.addFilter("urlRewriteFilter", UrlRewriteFilter.class);
        urlRewriteFilter.setInitParameter("logLevel", "WARN");
        urlRewriteFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        //Dispatch Servlet
        ServletRegistration dispatcherServlet = container.addServlet("dispatcherServlet", DispatcherServlet.class);
        dispatcherServlet.setInitParameter("contextConfigLocation", "");
        dispatcherServlet.addMapping("/");

    }
}
