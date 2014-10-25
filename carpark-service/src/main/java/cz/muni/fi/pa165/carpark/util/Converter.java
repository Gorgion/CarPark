/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.util;

import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.entity.UserCredentials;
import cz.muni.fi.pa165.carpark.entity.UserRole;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity - DTO converter.
 *
 * @author Tomas Svoboda
 */
public class Converter
{

    private Converter()
    {
    }
    
    public static Rental getEntity(cz.muni.fi.pa165.carpark.dto.Rental rental)
    {
        if(rental == null)
        {
            return null;
        }
        
        Rental entity = new Rental();
        
        entity.setId(rental.getId());
        entity.setFromDate(rental.getFromDate());
        entity.setToDate(rental.getToDate());
        entity.setUser(rental.getUser());
        entity.setCar(rental.getCar());
        entity.setRentalState(rental.getRentalState());
        
        return entity;
    }
    
    public static cz.muni.fi.pa165.carpark.dto.Rental getTransferObject(Rental entity)
    {        
        if(entity == null)
        {
            return null;
        }
        
        cz.muni.fi.pa165.carpark.dto.Rental dto = new cz.muni.fi.pa165.carpark.dto.Rental();
        
        dto.setId(entity.getId());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setUser(entity.getUser());
        dto.setCar(entity.getCar());
        dto.setRentalState(entity.getRentalState());
        
        return dto;
    }
    
        public static User getEntity(UserDto userDto)
    {
        if(userDto == null)
        {
            return null;
        }
        
        User entity = new User();
        
        entity.setId(userDto.getId());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setAddress(userDto.getAddress());
        entity.setBirthNumber(userDto.getBirthNumber());
        entity.setPosition(userDto.getPosition());
        
        return entity;
    }

    public static UserDto getTransferObject(User entity)
    {        
        if(entity == null)
        {
            return null;
        }
        
        UserDto dto = new UserDto();
        
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAddress(entity.getAddress());
        dto.setBirthNumber(entity.getBirthNumber());
        dto.setPosition(entity.getPosition());
        
        return dto;
    }
    
    public static UserCredentials getEntity(cz.muni.fi.pa165.carpark.dto.UserCredentials credentials)
    {
        if(credentials == null)
        {
            return null;
        }
        
        User userEntity = credentials.getUser().createEntity();
        Set<UserRole> rolesEntity = new HashSet<>();
        
        for(cz.muni.fi.pa165.carpark.dto.UserRole role : credentials.getRoles())
        {
            rolesEntity.add(getEntity(role));
        }
        
        UserCredentials entity = new UserCredentials(credentials.getUsername(),credentials.getPassword(),credentials.isEnabled(),userEntity,rolesEntity);
        //TODO rest
        
        
        return entity;
    }
    
    public static cz.muni.fi.pa165.carpark.dto.UserCredentials getTransferObject(UserCredentials credentials)
    {
        if(credentials == null)
        {
            return null;
        }
        
        //TODO
        UserDto userDto = getTransferObject(credentials.getUser());
        Set<cz.muni.fi.pa165.carpark.dto.UserRole> rolesDto = new HashSet<>();
        
        for(UserRole role : credentials.getRoles())
        {
            rolesDto.add(getTransferObject(role));
        }
        
        cz.muni.fi.pa165.carpark.dto.UserCredentials dto = new cz.muni.fi.pa165.carpark.dto.UserCredentials(credentials.getUsername(),credentials.getPassword(),credentials.isEnabled(),userDto,rolesDto);
        //TODO rest
        
        
        return dto;
    }
    
    public static UserRole getEntity(cz.muni.fi.pa165.carpark.dto.UserRole role)
    {
        if(role == null)
        {
            return null;
        }
        
        UserRole entity = new UserRole();
        entity.setId(role.getId());
        entity.setRoleName(role.getRoleName());
        entity.setUserCredentials(getEntity(role.getUserCredentials()));
        
        return entity;
    }
    
    public static cz.muni.fi.pa165.carpark.dto.UserRole getTransferObject(UserRole role)
    {
        if(role == null)
        {
            return null;
        }
        
        cz.muni.fi.pa165.carpark.dto.UserRole dto = new cz.muni.fi.pa165.carpark.dto.UserRole();
     
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setUserCredentials(getTransferObject(role.getUserCredentials()));
        
        return dto;
    }
        
}
