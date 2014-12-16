/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.security;

import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;
import cz.muni.fi.pa165.carpark.dto.UserRoleDto;
import cz.muni.fi.pa165.carpark.service.UserCredentialsService;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Named;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Spring user details service implementation.
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
            UserCredentialsDto credentials = userCredentialsService.getByUsername(username);

            if (credentials == null)
            {
                throw new UsernameNotFoundException("User for username '" + username + "' not found.");
            }

            return buildUserDetails(credentials);
        } catch (DataAccessException e)
        {
            throw new UsernameNotFoundException("User for username '" + username + "' not found.", e);
        }
    }

    private static UserDetails buildUserDetails(final UserCredentialsDto credentials)
    {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (UserRoleDto role : credentials.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        UserDetails details = new CustomUser(credentials.getUsername(), credentials.getPassword(), authorities, credentials.getUserId());        

        return details;
    }
    
    private static class CustomUser extends User
    {
        private Long id;
        
        private CustomUser(String username, String password, Collection<SimpleGrantedAuthority> authorities, Long userId)
        {
            super(username, password, authorities);
            
            this.id = id;
        }

        public Long getId()
        {
            return id;
        }

        public void setId(Long id)
        {
            this.id = id;
        }

        @Override
        public String toString()
        {
            return "CustomUser{" + "id=" + id + '}';
        }
        
    }
            
}
