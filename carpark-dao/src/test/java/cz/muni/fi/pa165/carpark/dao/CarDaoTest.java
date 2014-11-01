/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.config.TestConfig;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Persistence;
import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This class has unit tests for CarDaoImpl.java. 
 *
 * @author Karolina Burska
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class CarDaoTest {
    @Inject
    private CarDao carDao;
    @Inject
    private UserDao userDao;
    @Inject
    private RentalDao rentalDao;
    
    @Test
    public void addNewCarTest()
    {  
        Car car = new Car();
        
        car.setBrand(Car.mBrand.DAEWOO);
        car.setColor(Car.mColor.BLUE);
        car.setEngine(Car.mEngine.ELECTRIC);
        car.setLicencePlate("4G5-PA165");
        car.setModel(Car.mModel.MATIZ);
        car.setRented(Boolean.FALSE);
        car.setType(Car.mType.CABRIOLET);
        car.setVIN("SomeVIN");
        
        carDao.AddCar(car);

        Car secondCar = carDao.getCar(car.getID());

        Assert.assertNotNull(secondCar);
        Assert.assertEquals(car, secondCar);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void addCarWithNullTest(){
        carDao.AddCar(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void getCarWithWrongIdTest(){
        carDao.getCar(Long.getLong("-54"));
        carDao.getCar(Long.getLong("0"));  
    }
    
    @Test
    public void editCarTest(){
        
        Car car = new Car();
        car.setBrand(Car.mBrand.DAEWOO);
        car.setColor(Car.mColor.BLUE);
        car.setEngine(Car.mEngine.ELECTRIC);
        car.setLicencePlate("4G5-PA165");
        car.setModel(Car.mModel.MATIZ);
        car.setRented(Boolean.FALSE);
        car.setType(Car.mType.CABRIOLET);
        car.setVIN("SomeVIN");
        
        carDao.AddCar(car);
        
        Car carForEditation = new Car();
        carForEditation.setBrand(Car.mBrand.DAEWOO);
        carForEditation.setColor(Car.mColor.BLUE);
        carForEditation.setEngine(Car.mEngine.ELECTRIC);
        carForEditation.setModel(Car.mModel.MATIZ);
        carForEditation.setType(Car.mType.CABRIOLET);
        carForEditation.setID(car.getID());
        carForEditation.setLicencePlate("differentLicencePlate");
        car.setRented(Boolean.FALSE);
        carForEditation.setVIN("SomeOtherVIN");
        
        carDao.EditCar(carForEditation);
        
        Car carForCheck = new Car(); 
        carForCheck.setID(car.getID());
        
        Assert.assertNotNull(carForCheck);
        Assert.assertEquals(carForCheck, carForEditation);
        Assert.assertNotSame(car, carForCheck);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void editCarWithNullTest(){
        carDao.EditCar(null);
    }
    
    @Test
    public void deleteCarTest(){
        
        Car car = new Car();
        car.setBrand(Car.mBrand.DAEWOO);
        car.setColor(Car.mColor.BLUE);
        car.setEngine(Car.mEngine.ELECTRIC);
        car.setLicencePlate("4G5-PA165");
        car.setModel(Car.mModel.MATIZ);
        car.setRented(Boolean.FALSE);
        car.setType(Car.mType.CABRIOLET);
        car.setVIN("SomeVIN");
        
        carDao.AddCar(car);
        
        carDao.DeleteCar(car);
        
        Collection cars = carDao.getAllCars();
        
        Assert.assertNotNull(cars);
        Assert.assertTrue("Some car found after deletion.", cars.isEmpty());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deleteCarWithNullTest(){
        carDao.DeleteCar(null);
    }
    
    @Test
    public void getAllCarsTest(){
        
        Car car1 = new Car();
        car1.setBrand(Car.mBrand.CHEVROLET);
        car1.setLicencePlate("4G5-PA161");
        car1.setRented(Boolean.FALSE);
        car1.setVIN("SomeVIN1");
        
        Car car2 = new Car();
        car2.setBrand(Car.mBrand.FORD);
        car2.setLicencePlate("4G5-PA162");
        car2.setRented(Boolean.FALSE);
        car2.setVIN("SomeVIN2");
        
        Car car3 = new Car();
        car3.setBrand(Car.mBrand.TESLA);
        car3.setLicencePlate("4G5-PA163");
        car3.setRented(Boolean.FALSE);
        car3.setVIN("SomeVIN3");
        
        carDao.AddCar(car1);
        carDao.AddCar(car2);
        carDao.AddCar(car3);

        Collection cars = carDao.getAllCars();
        
        List<Car> rightCars = new ArrayList<>();
        rightCars.add(car1);
        rightCars.add(car2);
        rightCars.add(car3);
        
        Assert.assertNotSame(cars, rightCars);
        Assert.assertTrue("Missing expected car. ", rightCars.containsAll(cars)); 
        Assert.assertTrue("Missing car1. ", cars.contains(car1));
        Assert.assertTrue("Missing car2. ", cars.contains(car2));
        Assert.assertTrue("Missing car3. ", cars.contains(car3));
        Assert.assertTrue("Size of collection does not fit. ", cars.size() == 3);
    }
  
    @Test
    public void getRentedCarsTest(){
        
        Car car1 = new Car();
        car1.setBrand(Car.mBrand.CHEVROLET);
        car1.setLicencePlate("4G5-PA161");
        car1.setRented(Boolean.TRUE);
        car1.setVIN("SomeVIN1");
        
        Car car2 = new Car();
        car2.setBrand(Car.mBrand.FORD);
        car2.setLicencePlate("4G5-PA162");
        car2.setRented(Boolean.FALSE);
        car2.setVIN("SomeVIN2");
        
        Car car3 = new Car(); 
        car3.setBrand(Car.mBrand.TESLA);
        car3.setLicencePlate("4G5-PA163");
        car3.setRented(Boolean.TRUE);
        car3.setVIN("SomeVIN3");
        
        carDao.AddCar(car1);
        carDao.AddCar(car2);
        carDao.AddCar(car3);
        
        Collection cars = carDao.getRentedCars();
        
        List<Car> rentedCars = new ArrayList<>();
        rentedCars.add(car1);
        rentedCars.add(car3);

        Assert.assertTrue("Missing expected car. ", rentedCars.containsAll(cars));   
        Assert.assertTrue("Missing car1. ", cars.contains(car1));
        Assert.assertTrue("Car2 should not be in the collection. ", !cars.contains(car2));
        Assert.assertTrue("Missing car3. ", cars.contains(car3));
        Assert.assertTrue("Size of collection does not fit. ", cars.size() == 2);
    }
    
    @Test
    public void getFreeCarsTest() throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        
	Date from1 = sdf.parse("01-08-2014 10:20:30");
        Date to1 = sdf.parse("31-08-2014 10:20:30"); 
        
	Date from2 = sdf.parse("02-09-2014 10:20:30");
        Date to2 = sdf.parse("10-09-2014 10:20:30"); 
        
        Car car = TestUtils.createCar(Car.mBrand.CHEVROLET, Car.mType.CABRIOLET, Car.mColor.BLACK, Car.mEngine.PETROL, Car.mModel.CAMARO, "4G5-PA161", "SomeVIN1", true);
        
        carDao.AddCar(car);
        
        User user1 = TestUtils.createUser("John", "User", "Somewhere", User.Position.EMPLOYEE, "432516/7894");
        
        userDao.add(user1);
        
        Rental rental1 = TestUtils.createRental(car, Rental.State.ACTIVE, user1, from1, to1);
        
        rentalDao.create(rental1);
        
        Assert.assertTrue("Car is not avaliable", !(carDao.getFreeCars(from1, to1).contains(car)));
        Assert.assertTrue("Car is avaliable", carDao.getFreeCars(from2, to2).contains(car));
        Assert.assertTrue("Car is not avaliable", !(carDao.getFreeCars(from1, to2).contains(car)));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void getFreeCarsWitWrongArgTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        
	Date from = sdf.parse("01-08-2014 10:20:30");
        Date to = sdf.parse("31-08-2014 10:20:30"); 
        
        Car car = TestUtils.createCar(Car.mBrand.CHEVROLET, Car.mType.CABRIOLET, Car.mColor.BLACK, Car.mEngine.PETROL, Car.mModel.CAMARO, "4G5-PA161", "SomeVIN1", true);
        
        carDao.AddCar(car);
        
        User user1 = TestUtils.createUser("John", "User", "Somewhere", User.Position.EMPLOYEE, "432516/7894");
        
        userDao.add(user1);
        
        Rental rental1 = TestUtils.createRental(car, Rental.State.ACTIVE, user1, from, to);
        
        rentalDao.create(rental1);
        
        carDao.getFreeCars(to, from);
    }
    
}
