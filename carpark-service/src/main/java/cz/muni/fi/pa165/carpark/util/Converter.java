/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.util;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.Car;
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
    
    public static Rental getEntity(cz.muni.fi.pa165.carpark.dto.RentalDto rental)
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
    
    public static cz.muni.fi.pa165.carpark.dto.RentalDto getTransferObject(Rental entity)
    {        
        if(entity == null)
        {
            return null;
        }
        
        cz.muni.fi.pa165.carpark.dto.RentalDto dto = new cz.muni.fi.pa165.carpark.dto.RentalDto();
        
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
    
    public static UserCredentials getEntity(cz.muni.fi.pa165.carpark.dto.UserCredentialsDto credentials)
    {
        if(credentials == null)
        {
            return null;
        }
        
        User userEntity = credentials.getUser().createEntity();
        Set<UserRole> rolesEntity = new HashSet<>();
        
        for(cz.muni.fi.pa165.carpark.dto.UserRoleDto role : credentials.getRoles())
        {
            rolesEntity.add(getEntity(role));
        }
        
        UserCredentials entity = new UserCredentials(credentials.getUsername(),credentials.getPassword(),credentials.isEnabled(),userEntity,rolesEntity);
        //TODO rest
        
        
        return entity;
    }
    
    public static cz.muni.fi.pa165.carpark.dto.UserCredentialsDto getTransferObject(UserCredentials credentials)
    {
        if(credentials == null)
        {
            return null;
        }
        
        //TODO
        UserDto userDto = getTransferObject(credentials.getUser());
        Set<cz.muni.fi.pa165.carpark.dto.UserRoleDto> rolesDto = new HashSet<>();
        
        for(UserRole role : credentials.getRoles())
        {
            rolesDto.add(getTransferObject(role));
        }
        
        cz.muni.fi.pa165.carpark.dto.UserCredentialsDto dto = new cz.muni.fi.pa165.carpark.dto.UserCredentialsDto(credentials.getUsername(),credentials.getPassword(),credentials.isEnabled(),userDto,rolesDto);
        //TODO rest
        
        
        return dto;
    }
    
    public static UserRole getEntity(cz.muni.fi.pa165.carpark.dto.UserRoleDto role)
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
    
    public static cz.muni.fi.pa165.carpark.dto.UserRoleDto getTransferObject(UserRole role)
    {
        if(role == null)
        {
            return null;
        }
        
        cz.muni.fi.pa165.carpark.dto.UserRoleDto dto = new cz.muni.fi.pa165.carpark.dto.UserRoleDto();
     
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setUserCredentials(getTransferObject(role.getUserCredentials()));
        
        return dto;
    }
    
    public static Car getEntity(CarDto car)
    {
        if(car == null)
        {
            return null;
        }
        
        Car entity = new Car();
        entity.setID(car.getID());
        entity.setBrand(car.getBrand());
        entity.setType(car.getType());
        entity.setColor(car.getColor());
        entity.setEngine(car.getEngine());
        entity.setLicencePlate(car.getLicencePlate());
        entity.setModel(car.getModel());
        entity.setRented(car.getRented());
        entity.setVIN(car.getVIN());
        
        return entity;
    }
      
    public static CarDto getTransferObject(Car car)
    {
        if(car == null)
        {
            return null;
        }
        
        CarDto dto = new CarDto(car.getBrand(), car.getType(), car.getColor(), car.getEngine(), 
            car.getModel(), car.getLicencePlate(), car.getVIN(), car.getRented());
     
        dto.setID(car.getID());
        
        return dto;
    }
}
