/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.UserCredentialsDao;
import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.dto.UserRoleDto;
import cz.muni.fi.pa165.carpark.entity.UserCredentials;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.HashSet;
import java.util.Set;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Tomáš Vašíček
 */
@RunWith(MockitoJUnitRunner.class)
public class UserCredentialsServiceImplTest {
    
    @InjectMocks
    private UserCredentialsServiceImpl userCredentialsService = new UserCredentialsServiceImpl();
    
    @Mock
    private UserCredentialsDao mockedUserCredentials; 
    
    @Test
    public void testCreateAndGet()
    {
        UserCredentialsDto userCredentialsDto = getSampleUserCredentialsDto("root");
        UserCredentials userCredentials = Converter.getEntity(userCredentialsDto);       
        userCredentials.setUserId(1L);
        
        Mockito.doNothing().when(mockedUserCredentials).create(userCredentials);
        userCredentialsService.create(userCredentialsDto);
        userCredentialsDto.setUserId(userCredentials.getUserId());
        
        Mockito.doReturn(userCredentials).when(mockedUserCredentials).getByUsername(userCredentials.getUsername());
        
        UserCredentialsDto actualUserCredentialsDto = userCredentialsService.getByUsername(userCredentials.getUsername());
        
        Assert.assertNotNull(actualUserCredentialsDto);
        Assert.assertEquals(userCredentialsDto, actualUserCredentialsDto);
        Assert.assertEquals(userCredentialsDto.getUserId(), actualUserCredentialsDto.getUserId());
        Assert.assertEquals(userCredentialsDto.getPassword(), actualUserCredentialsDto.getPassword());
        Assert.assertEquals(userCredentialsDto.getUser(), actualUserCredentialsDto.getUser());
        Assert.assertEquals(userCredentialsDto.getUsername(), actualUserCredentialsDto.getUsername());
        Assert.assertEquals(userCredentialsDto.getRoles(), actualUserCredentialsDto.getRoles());
    }
    
    @Test
    public void testDelete()
    {
        UserCredentialsDto userCredentialsDto = getSampleUserCredentialsDto("root");
        userCredentialsDto.setUserId(1L); 
        
        UserCredentials userCredentials = Converter.getEntity(userCredentialsDto);
        
        userCredentialsService.create(userCredentialsDto);    
        userCredentialsService.delete(userCredentialsDto);
        
        Mockito.verify(mockedUserCredentials, Mockito.times(1)).delete(userCredentials);
        
        Assert.assertNull(mockedUserCredentials.getByUsername(userCredentials.getUsername()));
    }
    
    @Test
    public void testUpdate()
    {
        UserCredentialsDto userCredentialsDto = getSampleUserCredentialsDto("root");
        UserCredentials userCredentials = Converter.getEntity(userCredentialsDto);       
        userCredentials.setUserId(1L);

        Mockito.doNothing().when(mockedUserCredentials).update(userCredentials);

        userCredentialsService.update(userCredentialsDto);

        userCredentialsDto.setUserId(userCredentials.getUserId());

        Mockito.doReturn(userCredentials).when(mockedUserCredentials).getByUsername(userCredentials.getUsername());

        UserCredentialsDto actualUserCredentialsDto = userCredentialsService.getByUsername(userCredentials.getUsername());

        Assert.assertNotNull(actualUserCredentialsDto);
        Assert.assertEquals(userCredentialsDto, actualUserCredentialsDto);
        Assert.assertEquals(userCredentialsDto.getUserId(), actualUserCredentialsDto.getUserId());
        Assert.assertEquals(userCredentialsDto.getPassword(), actualUserCredentialsDto.getPassword());
        Assert.assertEquals(userCredentialsDto.getUser(), actualUserCredentialsDto.getUser());
        Assert.assertEquals(userCredentialsDto.getUsername(), actualUserCredentialsDto.getUsername());
        Assert.assertEquals(userCredentialsDto.getRoles(), actualUserCredentialsDto.getRoles());
    }
    
    private UserCredentialsDto getSampleUserCredentialsDto(String username)
    {
        UserDto userDto = new UserDto();
        userDto.setFirstName("name");
        userDto.setLastName("surname");
        userDto.setAddress("address");
        userDto.setBirthNumber("9875698/4587");

        Set<UserRoleDto> roles = new HashSet<>();

        UserCredentialsDto userCredentialsDto = new UserCredentialsDto();
        
        userCredentialsDto.setUsername(username);
        userCredentialsDto.setPassword("passwd");
        userCredentialsDto.setEnabled(true);
        userCredentialsDto.setUser(userDto);

        UserRoleDto role1 = new UserRoleDto();
        role1.setRoleName(UserRoleDto.RoleType.ADMIN.toString());

        UserRoleDto role2 = new UserRoleDto();
        role2.setRoleName(UserRoleDto.RoleType.USER.toString());

        roles.add(role1);
        roles.add(role2);
        
        userCredentialsDto.setRoles(roles);

        return userCredentialsDto;
    }
}
