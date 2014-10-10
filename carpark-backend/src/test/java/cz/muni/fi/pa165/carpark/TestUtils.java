/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.carpark;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Car.mBrand;
import cz.muni.fi.pa165.carpark.entity.Car.mColor;
import cz.muni.fi.pa165.carpark.entity.Car.mEngine;
import cz.muni.fi.pa165.carpark.entity.Car.mModel;
import cz.muni.fi.pa165.carpark.entity.Car.mType;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.Rental.State;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.entity.User.Position;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Tomáš Vašíček
 */
public class TestUtils {
    public static Rental createRental(Car car, State rentalState, User user, Date fromDate, Date toDate) {
        Rental rental = new Rental();
        
        rental.setCar(car);
        rental.setRentalState(rentalState);
        rental.setUser(user);
        rental.setFromDate(fromDate);
        rental.setFromDate(toDate);

        return rental;
    }
    
    public static User createUser(String firstName, String LastName, String address, Position position, String birthNumber) {
        User user = new User();
        
        user.setFirstName(firstName);
        user.setLastName(LastName);
        user.setAddress(address);
        user.setPosition(position);
        user.setBirthNumber(birthNumber);

        return user;
    }
    
    public static Car createCar(mBrand brand, mType type, mColor color, mEngine engine, mModel model, String licencePlate, String VIN, boolean rented, Office office, Rental rent)
    {
        Car car = new Car();
        
        car.setBrand(brand);
        car.setColor(color);
        car.setEngine(engine);
        car.setLicencePlate(licencePlate);
        car.setModel(model);
        car.setOffice(office);
        car.setRent(rent);
        car.setRented(rented);
        car.setType(type);
        car.setVIN(VIN);
        
        return car;
    }
    
    public static Office createOffice(String address, User manager, List<Car> cars, List<User> employees)
    {
        Office office = new Office();
        
        office.setAddress(address);
        office.setManager(manager);
        office.setCars(cars);
        office.setEmployees(employees);
        
        return office;
    }
    
    public static Date dateNow(Long more) {
        return new Date(new Date().getTime() + more);
    }
}
