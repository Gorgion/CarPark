/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.rest.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

/**
 * Auth remote rest client.
 *
 * @author Tomas Svoboda
 */
@Component
public class RestAuthFilter implements Filter
{
    @Autowired
    @Qualifier("authMgr")
    private AuthenticationManager authenticationManager;
    
    private void tryAuthenticateRestClient()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_BUILT_IN_ADMIN"));
        
        User account = new User("rest", "rest", authorities);
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(account, "rest", authorities);
        authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {            
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        if(!SecurityContextHolder.getContext().getAuthentication().isAuthenticated())
        {
            tryAuthenticateRestClient();
        }
    
        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {
    }
    
}
