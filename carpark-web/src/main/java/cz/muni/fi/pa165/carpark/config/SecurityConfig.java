///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package cz.muni.fi.pa165.carpark.web.config;
//
//import javax.inject.Inject;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
///**
// * Represents security configuration.
// * 
// * @author Tomas Svoboda
// */
//@Configuration
//@EnableWebMvcSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter
//{
//    @Inject
//    private UserDetailsService userDetailsService;
//    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception
//    {
//
//        http.authorizeRequests()
//                .antMatchers("/auth/**").authenticated()
//                .antMatchers("/assets/**").permitAll()
//            .and()
//                .formLogin()
//                
////                .successHandler(savedRequestAwareAuthenticationSuccessHandler())
//                .loginPage("/login")
//                .failureUrl("/login?error")
//            .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")            
//            .and()
//                .csrf().disable();
//    }    
//    
//    @Bean
//    public PasswordEncoder getPasswordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }
//    
//    @Inject
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
//    {
//        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
//   }
//}
