/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.servicefacade;

import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.UserCredentialsService;
import cz.muni.fi.pa165.carpark.service.UserService;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomas Svoboda
 */
@Service
public class UserAccountServiceFacadeImpl implements UserAccountServiceFacade
{
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserCredentialsService credentialsService;
    
    
    @Transactional
    @Override
    public void registerUser(UserCredentialsDto credentials)
    {
        UserDto userDto = credentials.getUser();
        
        Long userId =  userService.add(userDto);
        userDto.setId(userId);
        
        credentialsService.create(credentials);
    }
    
    @Transactional
    @Override
    public void removeUserAccount(UserCredentialsDto credentials)
    {
        UserDto userDto = credentials.getUser();
        credentialsService.delete(credentials);
                       
        userService.delete(userDto); 
    }
}
