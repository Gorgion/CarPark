/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.config;

import cz.muni.fi.pa165.carpark.web.rest.filter.RestAuthFilter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 *
 * @author Tomas Svoboda
 */
@Order(1)
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer
{
    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext)
    {
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");        
    }
    
    @Override
    protected void afterSpringSecurityFilterChain(ServletContext servletContext)
    {
        FilterRegistration.Dynamic RestAuthFilter = servletContext.addFilter("restAuthFilter", RestAuthFilter.class);
        RestAuthFilter.addMappingForUrlPatterns(null, true, "/rest/*");
    }
}
