/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Karolina Burska
 */
public class CarDaoTest {
    private CarDaoImpl carImpl;  
    
    public CarDaoTest(){  
    }
    
    @Before
    public void setup(){
        carImpl = new CarDaoImpl();
        carImpl.setEmf(Persistence.createEntityManagerFactory("TestPU"));
    }
    
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
        
        carImpl.AddCar(car);

        Car secondCar = carImpl.getCar(car.getID());

        Assert.assertNotNull(secondCar);
        Assert.assertEquals(car, secondCar);
        Assert.assertNotSame(car, secondCar);
    }
    /*
    @Test(expected = IllegalArgumentException.class)
    public void addCarWithNullTest(){
        
    }*/
    
    @Test(expected = IllegalArgumentException.class)
    public void getCarWithWrongIdTest(){
        carImpl.getCar(Long.getLong("-54"));
        carImpl.getCar(Long.getLong("0"));
        carImpl.AddCar(null);
    }
    
    @Test
    @Ignore
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
        
        carImpl.AddCar(car);
        
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
        
        carImpl.EditCar(carForEditation);
        
        Car carForCheck = new Car();
        
        carForCheck.setID(car.getID());
        
        Assert.assertNotNull(carForCheck);
        Assert.assertEquals(carForCheck, carForEditation);
        Assert.assertNotSame(car, carForCheck);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void editCarWithNullTest(){
        carImpl.EditCar(null);
    }
    
    @Test
    public void deleteCarTest(){
        Car car = new Car();
        
        car.setBrand(Car.mBrand.DAEWOO);
        //car.setID(Long.getLong("2"));
        car.setColor(Car.mColor.BLUE);
        car.setEngine(Car.mEngine.ELECTRIC);
        car.setLicencePlate("4G5-PA165");
        car.setModel(Car.mModel.MATIZ);
        car.setRented(Boolean.FALSE);
        car.setType(Car.mType.CABRIOLET);
        car.setVIN("SomeVIN");
        
        carImpl.AddCar(car);
        
        carImpl.DeleteCar(car);
        
        Collection cars = carImpl.getAllCars();
        
        Assert.assertNotNull(cars);
        Assert.assertTrue("Some car found after deletion.", cars.isEmpty());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void deleteCarWithNullTest()
    {
        carImpl.DeleteCar(null);
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
        
        carImpl.AddCar(car1);
        carImpl.AddCar(car2);
        carImpl.AddCar(car3);

        Collection cars = carImpl.getAllCars();
        
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
        
        carImpl.AddCar(car1);
        carImpl.AddCar(car2);
        carImpl.AddCar(car3);
        
        Collection cars = carImpl.getRentedCars();
        
        List<Car> rentedCars = new ArrayList<>();
        rentedCars.add(car1);
        rentedCars.add(car3);

        Assert.assertTrue("Missing expected car. ", rentedCars.containsAll(cars));   
        Assert.assertTrue("Missing car1. ", cars.contains(car1));
        Assert.assertTrue("Car2 should not be in the collection. ", !cars.contains(car2));
        Assert.assertTrue("Missing car3. ", cars.contains(car3));
        Assert.assertTrue("Size of collection does not fit. ", cars.size() == 2);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void getFreeCarsTest() throws ParseException {
        Car car = new Car();
        
        car.setBrand(Car.mBrand.CHEVROLET);
        car.setLicencePlate("4G5-PA161");
        car.setRented(Boolean.TRUE);
        car.setVIN("SomeVIN1"); 
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        
        String fromString1 = "01-08-2014 10:20:30";
        String toString1 = "31-08-2014 10:20:30";
	Date from1 = sdf.parse(fromString1);
        Date to1 = sdf.parse(toString1); 
        Rental rental1 = TestUtils.createRental(car, Rental.State.ACTIVE, null, from1, to1);
        
        String fromString2 = "02-09-2014 10:20:30";
        String toString2 = "10-09-2014 10:20:30";
	Date from2 = sdf.parse(fromString2);
        Date to2 = sdf.parse(toString2); 
        //Rental rental2 = TestUtils.createRental(car, Rental.State.NEW, null, from2, to2);
        
        carImpl.AddCar(car);
        
        carImpl.getFreeCars(to1, from1);
        
        //Collection cars = carImpl.getFreeCars(from1, to1);
        
        //List<Car> freeCars = new ArrayList<>();
        //freeCars.add(car);
        Assert.assertTrue("Car is not avaliable", !(carImpl.getFreeCars(from1, to1).contains(car)));
        Assert.assertTrue("Car is avaliable", carImpl.getFreeCars(from2, to2).contains(car));
        Assert.assertTrue("Car is not avaliable", !(carImpl.getFreeCars(from1, to2).contains(car)));
    }
    
}
