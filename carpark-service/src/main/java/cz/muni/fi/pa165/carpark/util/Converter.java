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
        if (rental == null)
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
        if (entity == null)
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
        switch (rental)
        {
            case NEW:
                return Rental.State.NEW;
            case APPROVED:
                return Rental.State.APPROVED;
            case ACTIVE:
                return Rental.State.ACTIVE;
            case FINISHED:
                return Rental.State.FINISHED;
            default:
                throw new IllegalArgumentException("unknown state.");
        }
    }

    public static Car.mEngine getEntity(CarDto.mEngine mEngine)
    {
        switch (mEngine)
        {
            case PETROL:
                return Car.mEngine.PETROL;
            case DIESEL:
                return Car.mEngine.DIESEL;
            default:
                throw new IllegalArgumentException("unknown engine.");
        }
    }

    public static Car.mBrand getEntity(CarDto.mBrand mBrand)
    {
        switch (mBrand)
        {
            case FORD_FOCUS:
                return Car.mBrand.FORD_FOCUS;
            case FORD_MONDEO:
                return Car.mBrand.FORD_MONDEO;
            case SKODA_FABIA:
                return Car.mBrand.SKODA_FABIA;
            case SKODA_OCTAVIA:
                return Car.mBrand.SKODA_OCTAVIA;
            case SKODA_SUPERB:
                return Car.mBrand.SKODA_SUPERB;
            default:
                throw new IllegalArgumentException("unknown brand");
        }
    }

    public static Car.mType getEntity(CarDto.mType mType)
    {
        switch (mType)
        {
            case COMBI:
                return Car.mType.COMBI;
            case SEDAN:
                return Car.mType.SEDAN;
            case HATCHBACK:
                return Car.mType.HATCHBACK;
            default:
                throw new IllegalArgumentException("unknown type");
        }
    }

    public static RentalDto.State getTransferObject(Rental.State entity)
    {
        switch (entity)
        {
            case NEW:
                return RentalDto.State.NEW;
            case APPROVED:
                return RentalDto.State.APPROVED;
            case ACTIVE:
                return RentalDto.State.ACTIVE;
            case FINISHED:
                return RentalDto.State.FINISHED;
            default:
                throw new IllegalArgumentException("unknown state.");
        }
    }

    public static CarDto.mEngine getTransferObject(Car.mEngine mEngine)
    {
        switch (mEngine)
        {
            case PETROL:
                return CarDto.mEngine.PETROL;
            case DIESEL:
                return CarDto.mEngine.DIESEL;
            default:
                throw new IllegalArgumentException("unknown engine.");
        }
    }

    public static CarDto.mBrand getTransferObject(Car.mBrand mBrand)
    {
        switch (mBrand)
        {
            case FORD_FOCUS:
                return CarDto.mBrand.FORD_FOCUS;
            case FORD_MONDEO:
                return CarDto.mBrand.FORD_MONDEO;
            case SKODA_FABIA:
                return CarDto.mBrand.SKODA_FABIA;
            case SKODA_OCTAVIA:
                return CarDto.mBrand.SKODA_OCTAVIA;
            case SKODA_SUPERB:
                return CarDto.mBrand.SKODA_SUPERB;
            default:
                throw new IllegalArgumentException("unknown brand");
        }
    }

    public static CarDto.mType getTransferObject(Car.mType mType)
    {
        switch (mType)
        {
            case COMBI:
                return CarDto.mType.COMBI;
            case SEDAN:
                return CarDto.mType.SEDAN;
            case HATCHBACK:
                return CarDto.mType.HATCHBACK;
            default:
                throw new IllegalArgumentException("unknown type");
        }
    }

    public static User getEntity(UserDto userDto)
    {
        if (userDto == null)
        {
            return null;
        }

        User entity = new User();

        entity.setId(userDto.getId());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setAddress(userDto.getAddress());
        entity.setBirthNumber(userDto.getBirthNumber());
        entity.setOffice(getEntity(userDto.getOfficeDto()));

        return entity;
    }

    public static UserDto getTransferObject(User entity)
    {
        if (entity == null)
        {
            return null;
        }

        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setAddress(entity.getAddress());
        dto.setBirthNumber(entity.getBirthNumber());
        dto.setOfficeDto(getTransferObject(entity.getOffice()));

        return dto;
    }

    public static UserCredentials getEntity(cz.muni.fi.pa165.carpark.dto.UserCredentialsDto credentials)
    {
        if (credentials == null)
        {
            return null;
        }

        User userEntity = getEntity(credentials.getUser());
        Set<UserRole> rolesEntity = new HashSet<>();

        for (cz.muni.fi.pa165.carpark.dto.UserRoleDto role : credentials.getRoles())
        {
            rolesEntity.add(getEntity(role));
        }

        UserCredentials entity = new UserCredentials(credentials.getUsername(), credentials.getPassword(), credentials.isEnabled(), userEntity, rolesEntity);
        entity.setUserId(credentials.getUserId());

        return entity;
    }

    public static cz.muni.fi.pa165.carpark.dto.UserCredentialsDto getTransferObject(UserCredentials credentials)
    {
        if (credentials == null)
        {
            return null;
        }

        UserDto userDto = getTransferObject(credentials.getUser());
        Set<cz.muni.fi.pa165.carpark.dto.UserRoleDto> rolesDto = new HashSet<>();

        for (UserRole role : credentials.getRoles())
        {
            rolesDto.add(getTransferObject(role));
        }

        cz.muni.fi.pa165.carpark.dto.UserCredentialsDto dto = new cz.muni.fi.pa165.carpark.dto.UserCredentialsDto(credentials.getUsername(), credentials.getPassword(), credentials.isEnabled(), userDto, rolesDto);
        dto.setUserId(credentials.getUserId());

        return dto;
    }

    public static UserRole getEntity(cz.muni.fi.pa165.carpark.dto.UserRoleDto role)
    {
        if (role == null)
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
        if (role == null)
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
        if (car == null)
        {
            return null;
        }

        Car entity = new Car();
        entity.setID(car.getId());
        entity.setBrand(getEntity(car.getBrand()));
        entity.setType(getEntity(car.getType()));
        entity.setEngine(getEntity(car.getEngine()));
        entity.setLicencePlate(car.getLicencePlate());
        entity.setRented(car.isRented());
        entity.setVIN(car.getVIN());
        entity.setOffice(getEntity(car.getOfficeDto()));

        return entity;
    }

    public static CarDto getTransferObject(Car car)
    {
        if (car == null)
        {
            return null;
        }

        CarDto dto = new CarDto(getTransferObject(car.getBrand()), getTransferObject(car.getType()),
                getTransferObject(car.getEngine()), car.getLicencePlate(), car.getVIN(), car.isRented(), getTransferObject(car.getOffice()));

        dto.setID(car.getID());

        return dto;
    }

    public static Office getEntity(OfficeDto office)
    {
        if (office == null)
        {
            return null;
        }

        Office officeEntity = new Office();

        officeEntity.setId(office.getId());
        officeEntity.setAddress(office.getAddress());
        officeEntity.setManager(null);

        if (office.getManager() != null)
        {
            User manager = new User();

            manager.setId(office.getManager().getId());
            manager.setFirstName(office.getManager().getFirstName());
            manager.setLastName(office.getManager().getLastName());
            manager.setAddress(office.getManager().getAddress());
            manager.setBirthNumber(office.getManager().getBirthNumber());
            manager.setOffice(officeEntity);
            officeEntity.setManager(manager);
        }

        List<User> users = new ArrayList<>();

        for (UserDto userDto : office.getEmployees())
        {
            User entity = new User();

            entity.setId(userDto.getId());
            entity.setFirstName(userDto.getFirstName());
            entity.setLastName(userDto.getLastName());
            entity.setAddress(userDto.getAddress());
            entity.setBirthNumber(userDto.getBirthNumber());
            entity.setOffice(officeEntity);

            users.add(entity);
        }

        List<Car> cars = new ArrayList<>();

        for (CarDto car : office.getCars())
        {
            Car entity = new Car();
            entity.setID(car.getId());
            entity.setBrand(getEntity(car.getBrand()));
            entity.setType(getEntity(car.getType()));
            entity.setEngine(getEntity(car.getEngine()));
            entity.setLicencePlate(car.getLicencePlate());
            entity.setRented(car.isRented());
            entity.setVIN(car.getVIN());
            entity.setOffice(officeEntity);

            cars.add(entity);
        }

        officeEntity.setCars(cars);
        officeEntity.setEmployees(users);

        return officeEntity;
    }

    public static OfficeDto getTransferObject(Office office)
    {
        if (office == null)
        {
            return null;
        }

        OfficeDto officeDto = new OfficeDto(office.getAddress(), null, null, null);

        if (office.getManager() != null)
        {
            UserDto manager = new UserDto();

            manager.setId(office.getManager().getId());
            manager.setFirstName(office.getManager().getFirstName());
            manager.setLastName(office.getManager().getLastName());
            manager.setAddress(office.getManager().getAddress());
            manager.setBirthNumber(office.getManager().getBirthNumber());
            manager.setOfficeDto(officeDto);
            officeDto.setManager(manager);
        }

        List<UserDto> usersDto = new ArrayList<>();

        for (User u : office.getEmployees())
        {
            UserDto dto = new UserDto();

            dto.setId(u.getId());
            dto.setFirstName(u.getFirstName());
            dto.setLastName(u.getLastName());
            dto.setAddress(u.getAddress());
            dto.setBirthNumber(u.getBirthNumber());
            dto.setOfficeDto(officeDto);
            usersDto.add(dto);
        }

        officeDto.setEmployees(usersDto);

        List<CarDto> carsDto = new ArrayList<>();

        for (Car car : office.getCars())
        {
            CarDto dto = new CarDto(getTransferObject(car.getBrand()), getTransferObject(car.getType()),
                    getTransferObject(car.getEngine()), car.getLicencePlate(), car.getVIN(), car.isRented(), officeDto);
            dto.setID(car.getID());

            carsDto.add(dto);
        }
        officeDto.setCars(carsDto);

        officeDto.setId(office.getId());

        return officeDto;
    }

    public static List<UserDto> getTransferObjectUserList(List<User> users)
    {
        if (users == null)
        {
            return null;
        }

        List<UserDto> usersDto = new ArrayList<>();

        for (User u : users)
        {
            usersDto.add(Converter.getTransferObject(u));
        }

        return usersDto;
    }

    public static List<CarDto> getTransferObjectCarList(List<Car> cars)
    {
        if (cars == null)
        {
            return null;
        }

        List<CarDto> carsDto = new ArrayList<>();

        for (Car c : cars)
        {
            carsDto.add(Converter.getTransferObject(c));
        }

        return carsDto;
    }

    public static List<User> getEntityUserList(List<UserDto> usersDto)
    {
        List<User> users = new ArrayList<>();

        for (UserDto userDto : usersDto)
        {
            users.add(Converter.getEntity(userDto));
        }

        return users;
    }

    public static List<Office> getEntityOfficeList(List<OfficeDto> officesDto)
    {
        List<Office> offices = new ArrayList<>();

        for (OfficeDto officeDto : officesDto)
        {
            offices.add(Converter.getEntity(officeDto));
        }

        return offices;
    }

    public static List<Car> getEntityCarList(List<CarDto> carsDto)
    {
        List<Car> cars = new ArrayList<>();

        for (CarDto carDto : carsDto)
        {
            cars.add(Converter.getEntity(carDto));
        }

        return cars;
    }
}
