/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Rental;
import java.util.ArrayList;
import java.util.Collection;
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
    public void deleteCar(){
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
        Assert.assertTrue("Car found after deletion.", !cars.contains(car));
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
        Assert.assertTrue("Missing expected user. ", cars.containsAll(rightCars));   
    }
    
    @Test
    public void getRentedCarsTest(){
        
    }
    
    @Test
    public void getFreeCarsTest(){
        
    }
    
}
