/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.carpark;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Car.mBrand;
import cz.muni.fi.pa165.carpark.entity.Car.mEngine;
import cz.muni.fi.pa165.carpark.entity.Car.mType;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.Rental.State;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.Arrays;
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
        rental.setToDate(toDate);

        return rental;
    }
    
    public static User createUser(String firstName, String LastName, String address, String birthNumber) {
        User user = new User();
        
        user.setFirstName(firstName);
        user.setLastName(LastName);
        user.setAddress(address);
        user.setBirthNumber(birthNumber);

        return user;
    }
    
    public static Car createCar(mBrand brand, mType type, mEngine engine, String licencePlate, String VIN, boolean rented)
    {
        Car car = new Car();
        
        car.setBrand(brand);
        car.setEngine(engine);
        car.setLicencePlate(licencePlate);
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
    
    public static Office createSampleOffice(){

        User empl1m = createUser("Jiří", "Jirkovič", "Adresa 1", "321bn");
        User empl2 = createUser("Jan", "Jirkovič", "Adresa 1", "123bn");
        User empl3 = createUser("Karel", "Karlovič", "Adresa 3", "456bn");
        User empl4 = createUser("Pepa", "Pepovič", "Adresa 4", "112bn");
        
        Office office = new Office();
        office.setAddress("Polní 22");
        office.setEmployees(Arrays.asList(empl1m, empl2, empl3, empl4));
        office.setManager(empl1m);
        
        Car car1 = createCar(mBrand.SKODA_FABIA, mType.COMBI, mEngine.PETROL, "LP1", "VIN1", false);
        Car car2 = createCar(mBrand.SKODA_OCTAVIA, mType.COMBI, mEngine.PETROL, "LP2", "VIN2", false);
        Car car3 = createCar(mBrand.SKODA_SUPERB, mType.COMBI, mEngine.PETROL, "LP3", "VIN3", false);
        Car car4 = createCar(mBrand.SKODA_OCTAVIA, mType.COMBI, mEngine.PETROL, "LP4", "VIN4", false);
        Car car5 = createCar(mBrand.SKODA_FABIA, mType.COMBI, mEngine.PETROL, "LP5", "VIN5", false);
        
        office.setCars(Arrays.asList(car1, car2, car3, car4));
        
        return office;
    }
    
    public static Date dateNow(Long more) {
        return new Date(new Date().getTime() + more);
    }
}
