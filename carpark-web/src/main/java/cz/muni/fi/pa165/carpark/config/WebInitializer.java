/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.config;

import cz.muni.fi.pa165.carpark.web.rest.filter.SimpleCORSFilter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Tomas Svoboda
 */
@Order(2)
public class WebInitializer implements WebApplicationInitializer
{

    @Override
    public void onStartup(ServletContext container) throws ServletException
    {

        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(CoreConfig.class);
        ctx.register(WebApplicationConfig.class);

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(ctx));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        FilterRegistration.Dynamic encoding = container.addFilter("encoding", CharacterEncodingFilter.class);
        encoding.setInitParameter("encoding", "utf-8");
        encoding.addMappingForUrlPatterns(null, false, "/*");

        container.addListener(new ContextLoaderListener(ctx));
        
        FilterRegistration.Dynamic CORFilter = container.addFilter("simpleCorFilter", SimpleCORSFilter.class);
        CORFilter.addMappingForUrlPatterns(null, true, "/*");
    }
}
