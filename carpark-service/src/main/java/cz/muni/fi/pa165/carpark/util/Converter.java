/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.util;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.entity.UserCredentials;
import cz.muni.fi.pa165.carpark.entity.UserRole;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    
    public static Rental getEntity(RentalDto rental)
    {
        if(rental == null)
        {
            return null;
        }
        
        Rental entity = new Rental();
        
        entity.setId(rental.getId());
        entity.setFromDate(rental.getFromDate());
        entity.setToDate(rental.getToDate());
        entity.setUser(getEntity(rental.getUser()));
        entity.setCar(getEntity(rental.getCar()));
        entity.setRentalState(getEntity(rental.getRentalState()));
        
        return entity;
    }
    
    public static RentalDto getTransferObject(Rental entity)
    {        
        if(entity == null)
        {
            return null;
        }
        
       RentalDto dto = new RentalDto();
        
        dto.setId(entity.getId());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setUser(getTransferObject(entity.getUser()));
        dto.setCar(getTransferObject(entity.getCar()));
        dto.setRentalState(getTransferObject(entity.getRentalState()));
        
        return dto;
    }
    
    public static Rental.State getEntity(RentalDto.State rental)
    {
        switch(rental)
        {
            case NEW: return Rental.State.NEW;
            case APPROVED: return Rental.State.APPROVED;
            case ACTIVE: return Rental.State.ACTIVE;
            case FINISHED: return Rental.State.FINISHED;
            default: throw new IllegalArgumentException("unknown state.");
        }
    }
    
    public static Car.mEngine getEntity(CarDto.mEngine mEngine)
    {
        switch(mEngine)
        {
            case PETROL: return Car.mEngine.PETROL;
            case DIESEL: return Car.mEngine.DIESEL;
            case ELECTRIC: return Car.mEngine.ELECTRIC;
            default: throw new IllegalArgumentException("unknown engine.");
        }
    }
    
    public static Car.mBrand getEntity(CarDto.mBrand mBrand)
    {
        switch(mBrand)
        {
            case CHEVROLET: return Car.mBrand.CHEVROLET;
            case DAEWOO: return Car.mBrand.DAEWOO;
            case FORD: return Car.mBrand.FORD;
            case SKODA: return Car.mBrand.SKODA;
            case TESLA: return Car.mBrand.TESLA;
            default: throw new IllegalArgumentException("unknown brand");
        }
    }

    public static Car.mType getEntity(CarDto.mType mType)   
    {
        switch(mType)
        {
            case COMBI: return Car.mType.COMBI;
            case SEDAN: return Car.mType.SEDAN;
            case HATCHBACK: return Car.mType.HATCHBACK;
            case CABRIOLET: return Car.mType.CABRIOLET;
            default: throw new IllegalArgumentException("unknown type");
        }
    }

    public static Car.mColor getEntity(CarDto.mColor mColor)
    {
        switch(mColor)
        {
            case YELLOW: return Car.mColor.YELLOW;
            case BLACK: return Car.mColor.BLACK;
            case BLUE: return Car.mColor.BLUE;
            case RED: return Car.mColor.RED;
            case GREEN: return Car.mColor.GREEN;
            case WHITE: return Car.mColor.WHITE;
            default: throw new IllegalArgumentException("unknown color");
        }
    }

    public static Car.mModel getEntity(CarDto.mModel mModel)
    {
        switch(mModel)
        {
            case OCTAVIA: return Car.mModel.OCTAVIA;
            case FABIA: return Car.mModel.FABIA;
            case MATIZ: return Car.mModel.MATIZ;
            case FOCUS: return Car.mModel.FOCUS;
            case MONDEO: return Car.mModel.MONDEO;
            case MODEL_S: return Car.mModel.MODEL_S;
            case CAMARO: return Car.mModel.CAMARO;
            default: throw new IllegalArgumentException("unknown model"); 
        }
    }
    
    public static RentalDto.State getTransferObject(Rental.State entity)
    {
        switch(entity)
        {
            case NEW: return RentalDto.State.NEW;
            case APPROVED: return RentalDto.State.APPROVED;
            case ACTIVE: return RentalDto.State.ACTIVE;
            case FINISHED: return RentalDto.State.FINISHED;
            default: throw new IllegalArgumentException("unknown state.");
        }
    }
    
    public static CarDto.mEngine getTransferObject(Car.mEngine mEngine)
    {
        switch(mEngine)
        {
            case PETROL: return CarDto.mEngine.PETROL;
            case DIESEL: return CarDto.mEngine.DIESEL;
            case ELECTRIC: return CarDto.mEngine.ELECTRIC;
            default: throw new IllegalArgumentException("unknown engine.");
        }
    }
    
    public static CarDto.mBrand getTransferObject(Car.mBrand mBrand)
    {
        switch(mBrand)
        {
            case CHEVROLET: return CarDto.mBrand.CHEVROLET;
            case DAEWOO: return CarDto.mBrand.DAEWOO;
            case FORD: return CarDto.mBrand.FORD;
            case SKODA: return CarDto.mBrand.SKODA;
            case TESLA: return CarDto.mBrand.TESLA;
            default: throw new IllegalArgumentException("unknown brand");
        }
    }

    public static CarDto.mType getTransferObject(Car.mType mType)   
    {
        switch(mType)
        {
            case COMBI: return CarDto.mType.COMBI;
            case SEDAN: return CarDto.mType.SEDAN;
            case HATCHBACK: return CarDto.mType.HATCHBACK;
            case CABRIOLET: return CarDto.mType.CABRIOLET;
            default: throw new IllegalArgumentException("unknown type");
        }
    }

    public static CarDto.mColor getTransferObject(Car.mColor mColor)
    {
        switch(mColor)
        {
            case YELLOW: return CarDto.mColor.YELLOW;
            case BLACK: return CarDto.mColor.BLACK;
            case BLUE: return CarDto.mColor.BLUE;
            case RED: return CarDto.mColor.RED;
            case GREEN: return CarDto.mColor.GREEN;
            case WHITE: return CarDto.mColor.WHITE;
            default: throw new IllegalArgumentException("unknown color");
        }
    }

    public static CarDto.mModel getTransferObject(Car.mModel mModel)
    {
        switch(mModel)
        {
            case OCTAVIA: return CarDto.mModel.OCTAVIA;
            case FABIA: return CarDto.mModel.FABIA;
            case MATIZ: return CarDto.mModel.MATIZ;
            case FOCUS: return CarDto.mModel.FOCUS;
            case MONDEO: return CarDto.mModel.MONDEO;
            case MODEL_S: return CarDto.mModel.MODEL_S;
            case CAMARO: return CarDto.mModel.CAMARO;
            default: throw new IllegalArgumentException("unknown model"); 
        }
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
        
        return dto;
    }
    
    public static UserCredentials getEntity(cz.muni.fi.pa165.carpark.dto.UserCredentialsDto credentials)
    {
        if(credentials == null)
        {
            return null;
        }
        
        User userEntity = getEntity(credentials.getUser());
        Set<UserRole> rolesEntity = new HashSet<>();
        
        for(cz.muni.fi.pa165.carpark.dto.UserRoleDto role : credentials.getRoles())
        {
            rolesEntity.add(getEntity(role));
        }
        
        UserCredentials entity = new UserCredentials(credentials.getUsername(),credentials.getPassword(),credentials.isEnabled(),userEntity,rolesEntity);
        entity.setUserId(credentials.getUserId());
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
        dto.setUserId(credentials.getUserId());        
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
        entity.setBrand(getEntity(car.getBrand()));
        entity.setType(getEntity(car.getType()));
        entity.setColor(getEntity(car.getColor()));
        entity.setEngine(getEntity(car.getEngine()));
        entity.setLicencePlate(car.getLicencePlate());
        entity.setModel(getEntity(car.getModel()));
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
        
        CarDto dto = new CarDto(getTransferObject(car.getBrand()), getTransferObject(car.getType()), 
                getTransferObject(car.getColor()),getTransferObject(car.getEngine()),getTransferObject(car.getModel()),
                car.getLicencePlate(), car.getVIN(), car.getRented());
     
        dto.setID(car.getID());
        
        return dto;
    }
    
        public static Office getEntity(OfficeDto office)
    {
        if(office == null) {
            return null;
        }
        
        Office officeEntity = new Office();
        
        officeEntity.setID(office.getID());
        officeEntity.setAddress(office.getAddress());
        officeEntity.setManager(office.getManager());
        officeEntity.setCars(office.getCars());
        officeEntity.setEmployees(office.getEmployees());
        
        return officeEntity;
    }
    
    public static OfficeDto getTransferObject(Office office)
    {
        if(office == null) {
            return null;
        }
       
        OfficeDto officeDto = new OfficeDto(
                office.getAddress(), 
                office.getManager(), 
                office.getEmployees(), 
                office.getCars()
        );
        
        officeDto.setID(office.getID());
        
        return officeDto;
    }
    
    public static List<UserDto> getTransferObjectUserList(List<User> users)
    {
        if(users == null) {
            return null;
        }
       
        List<UserDto> usersDto = new ArrayList<>();
        
        for(User u : users)
        {
            usersDto.add(Converter.getTransferObject(u));
        }
        
        return usersDto;
    }
    
    public static List<CarDto> getTransferObjectCarList(List<Car> cars)
    {
        if(cars == null) {
            return null;
        }
       
        List<CarDto> carsDto = new ArrayList<>();
        
        for(Car c : cars)
        {
            carsDto.add(Converter.getTransferObject(c));
        }
        
        return carsDto;
    }
    
    public static List<Office> getEntityOfficeList(List<OfficeDto> officesDto)
    {
        List<Office> offices = new ArrayList<>();
        
        for(OfficeDto officeDto : officesDto)
        {
            offices.add(Converter.getEntity(officeDto));
        }
        
        return offices;
    }
    
    public static List<Car> getEntityCarList(List<CarDto> carsDto)
    {
        List<Car> cars = new ArrayList<>();
        
        for(CarDto carDto : carsDto)
        {
            cars.add(Converter.getEntity(carDto));
        }
        
        return cars;
    }  
}
