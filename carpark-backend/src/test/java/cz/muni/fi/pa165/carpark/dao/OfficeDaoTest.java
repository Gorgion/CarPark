/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Car.mBrand;
import cz.muni.fi.pa165.carpark.entity.Car.mColor;
import cz.muni.fi.pa165.carpark.entity.Car.mEngine;
import cz.muni.fi.pa165.carpark.entity.Car.mModel;
import cz.muni.fi.pa165.carpark.entity.Car.mType;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jiri Dockal
 */
public class OfficeDaoTest
{
    private OfficeDao dao;
    
    @Before
    public void setup()
    {
        dao = new OfficeDaoImpl();
        dao.setEMF(Persistence.createEntityManagerFactory("TestOfficePersistence"));
    }
    
    @Test
    public void wrongAddOfficeTest()
    {
        try
        {
            dao.addOffice(null);
            Assert.fail("Office can't be null - exception excepted!");
        }
        catch(IllegalArgumentException ex)
        {
        }
    }
       
    @Test
    public void addOfficeTest()
    {
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
        
        List<User> employees = new ArrayList<User>();
        employees.add(manager);
        employees.add(employee);
        
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, manager, null, employees);
           
        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false, office, null);
        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false, office, null);
        Car car3 = TestUtils.createCar(mBrand.FORD, mType.HATCHBACK, mColor.RED, mEngine.DIESEL, mModel.FOCUS, "1A11111", "VIN222", false, office, null);
        
        List<Car> cars = new ArrayList<Car>();
        cars.add(car3);
        cars.add(car2);
        cars.add(car1);
        
        office.setCars(cars);
        try
        {
            dao.addOffice(office);
        }
        catch(IllegalArgumentException ex)
        {
            Assert.fail("Expected office addition");
        }
        
        Assert.assertNotNull("Expected office addition", office.getID());
        Assert.assertEquals(office.getCars(),cars);
        Assert.assertEquals(office.getEmployees(), employees);
        Assert.assertEquals(office.getManager(), manager);
        Assert.assertEquals(office.getAddress(), address);
                
        try
        {
            dao.addOffice(office);
            Assert.fail("Office already added - exception expected");
        }
        catch(IllegalArgumentException ex)
        {
        }
    }
    
    @Test
    public void getAllOfficesTest()
    {
        User empl1 = TestUtils.createUser("Jan", "Jirkovič", "Adresa 1", User.Position.EMPLOYEE, "123bn");
        User empl2m = TestUtils.createUser("Jiří", "Jirkovič", "Adresa 1", User.Position.MANAGER, "321bn");
        User empl3m= TestUtils.createUser("Tomáš", "Tomášovič", "Adresa 2", User.Position.MANAGER, "111bn");
        User empl4 = TestUtils.createUser("Karel", "Karlovič", "Adresa 3", User.Position.EMPLOYEE, "456bn");
        User empl5m = TestUtils.createUser("Pepa", "Pepovič", "Adresa 4", User.Position.MANAGER, "112bn");
        
        List<User> employees1 = new ArrayList<User>();
        employees1.add(empl1);
        employees1.add(empl2m);
        
        List<User> employees2 = new ArrayList<User>();
        employees1.add(empl3m);
        employees1.add(empl4);
        
        List<User> employees3 = new ArrayList<User>();
        employees1.add(empl5m);
        
        Office office1 = TestUtils.createOffice("Adresa 1", empl2m, null, employees1);
        Office office2 = TestUtils.createOffice("Adresa 2", empl3m, null, employees2);
        Office office3 = TestUtils.createOffice("Adresa 3", empl5m, null, employees3);
        
        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "LP1", "VIN1", false, office1, null);
        Car car2 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "LP2", "VIN2", false, office1, null);
        Car car3 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "LP3", "VIN3", false, office2, null);
        Car car4 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "LP4", "VIN4", false, office2, null);
        Car car5 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "LP5", "VIN5", false, office3, null);

        List<Car> cars1 = new ArrayList<Car>();
        cars1.add(car1);
        cars1.add(car2);
        
        List<Car> cars2 = new ArrayList<Car>();
        cars1.add(car3);
        cars1.add(car4);
        
        List<Car> cars3 = new ArrayList<Car>();
        cars1.add(car5);
        
        office1.setCars(cars1);
        office2.setCars(cars2);
        office3.setCars(cars3);
        
        List<Office> offices = new ArrayList<Office>();
        offices.add(office1);
        offices.add(office2);
        offices.add(office3);
        
        Assert.assertEquals(dao.getAllOffices(),offices);
    }
    
    @Test
    public void getEmployeesTest()
    {
        
    }
    
    
    @Test
    public void deleteOfficeTest()
    {
        try
        {
            dao.deleteOffice(null);
            Assert.fail("Office cannot be null - exception expected");
        }catch(IllegalArgumentException ex)
        {
        }
        
        Office office = TestUtils.createOffice("Adresa 1", null, null, null);
        
        dao.addOffice(office);
        dao.deleteOffice(office);
        
        try
        {
            dao.getOffice(office.getID());
            Assert.fail("Office should be deleted - exception expected");
        }catch(IllegalArgumentException ex)
        {
        }
            
    }
}
