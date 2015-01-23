///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package cz.muni.fi.pa165.carpark.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Represents security configuration.
 * 
 * @author Tomas Svoboda
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http.authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/assets/**").permitAll()
            .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID") 
            .and()
                .csrf().disable();
    }        
    
    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    @Bean(name="myAuthManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception 
    {
        return super.authenticationManagerBean();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder()).and()
                .inMemoryAuthentication().withUser("admin").password("admin").roles("BUILT_IN_ADMIN").and()
                .withUser("rest").password("rest").roles("BUILT_IN_ADMIN");
   }
}
