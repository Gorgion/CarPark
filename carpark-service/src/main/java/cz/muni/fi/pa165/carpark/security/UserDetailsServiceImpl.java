/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.security;

import cz.muni.fi.pa165.carpark.dto.UserCredentials;
import cz.muni.fi.pa165.carpark.dto.UserRole;
import cz.muni.fi.pa165.carpark.service.UserCredentialsService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author Tomas Svoboda
 */
@Named
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Inject
    private UserCredentialsService userCredentialsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        try
        {
            UserCredentials credentials = userCredentialsService.getByUsername(username).get();
            
            if(credentials == null)
            {
                throw  new UsernameNotFoundException("User for username '" + username + "' not found.");
            }
            
            return buildUserDetails(credentials);
        } catch (InterruptedException | ExecutionException ex)
        {
            throw  new UsernameNotFoundException("User for username '" + username + "' not found.");
        }
    }
    
    private static UserDetails buildUserDetails(UserCredentials credentials)
    { 
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        for(UserRole role : credentials.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        
        UserDetails details = new User(credentials.getUsername(), credentials.getPassword(), authorities);
        
        return details;
    }
}
