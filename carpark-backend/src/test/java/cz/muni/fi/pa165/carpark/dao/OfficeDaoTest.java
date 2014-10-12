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
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;

/**
 *
 * @author Jiri Dockal
 */
public class OfficeDaoTest
{
    private OfficeDao dao;
    private CarDao carDao;
    private UserDao userDao;
    
    @Before
    public void setup()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        
        dao = new OfficeDaoImpl();
        dao.setEMF(emf);
        
        carDao = new CarDaoImpl();
        carDao.setEmf(emf);
        
        userDao = new UserDaoImpl();
        userDao.setEmf(emf);
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
//    @Ignore
    public void addGetOfficeTest()
    {
        String address = "Adresa 123";
                
        Office office = TestUtils.createOffice(address, null, null, null);
        
        try
        {
            dao.addOffice(office);
        }
        catch(IllegalArgumentException ex)
        {
            Assert.fail("Expected office addition");
        }
        
        Assert.assertNotNull(dao.getAllOffices());
        
        Assert.assertEquals(office, dao.getOffice(office.getID()));
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
//    @Ignore
    public void getOfficeCars()
    {
        String address = "Adresa 123";
        
        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false);
        Car car3 = TestUtils.createCar(mBrand.FORD, mType.HATCHBACK, mColor.RED, mEngine.DIESEL, mModel.FOCUS, "1A11111", "VIN222", false);
        
        List<Car> cars = new ArrayList<Car>();
        
        cars.add(car3);
        cars.add(car2);
        cars.add(car1);        
        
        Office office = TestUtils.createOffice(address, null, cars, null);
        
        carDao.AddCar(car1);
        carDao.AddCar(car2);
        carDao.AddCar(car3);
        
        dao.addOffice(office);
        
        Assert.assertEquals(dao.getOfficeCars(office),cars);
    }
    
    @Test
//    @Ignore
    public void getOfficeEmployees()
    {
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
        
        List<User> employees = new ArrayList<User>();
        employees.add(manager);
        employees.add(employee);
        
        String address = "Adresa 123";
        
        Office office = TestUtils.createOffice(address, manager, null, employees);
        
        userDao.add(manager);
        userDao.add(employee);
        
        dao.addOffice(office);
        
        Assert.assertEquals(dao.getEmployees(office),employees);
    }
    
    @Test
//    @Ignore
    public void getAllOfficesTest()
    {
        Office office1 = TestUtils.createOffice("Adresa 1", null, null, null);
        Office office2 = TestUtils.createOffice("Adresa 2", null, null, null);
        Office office3 = TestUtils.createOffice("Adresa 3", null, null, null);
        
        List<Office> offices = new ArrayList<Office>();
        offices.add(office1);
        offices.add(office2);
        offices.add(office3);
        
        dao.addOffice(office1);
        dao.addOffice(office2);
        dao.addOffice(office3);
        
        Assert.assertEquals(dao.getAllOffices(),offices);
    }
    
    @Test
    @Ignore
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
    
    @Test
    @Ignore
    public void editOfficeTest()
    {
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
        
        List<User> employees = new ArrayList<User>();
        employees.add(manager);
        employees.add(employee);
        
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, manager, null, employees);
        
        dao.addOffice(office);
        
        User employee2 = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.EMPLOYEE, "901212/1234");
        User manager2 = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.MANAGER, "820101/4321");
        
        List<User> employees2 = new ArrayList<User>();
        employees2.add(manager);
        employees2.add(employee);
        
        String address2 = "Adresa 321";
        
        
        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false);
        Car car3 = TestUtils.createCar(mBrand.FORD, mType.HATCHBACK, mColor.RED, mEngine.DIESEL, mModel.FOCUS, "1A11111", "VIN222", false);
        
        List<Car> cars = new ArrayList<Car>();
        cars.add(car3);
        cars.add(car2);
        cars.add(car1);
        
        Office officeNew = TestUtils.createOffice(address2, manager2, cars, employees2);

        officeNew.setID(office.getID());
        
        dao.editOffice(officeNew);
        
        Assert.assertEquals(officeNew, dao.getOffice(office.getID()));
        Assert.assertNotEquals(dao.getOffice(office.getID()), office);
    }
    
    @Test
//    @Ignore
    public void addEmployeeToOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
        
        List<User> employees = new ArrayList<User>();
        
        employees.add(manager);
        employees.add(employee);
        
        dao.addOffice(office);
        
        dao.addEmployeeToOffice(office, manager);
        dao.addEmployeeToOffice(office, employee);
        
        Office gotOffice = dao.getOffice(office.getID());
        
        Assert.assertEquals(employees, gotOffice.getEmployees());
    }
    
    @Test
    @Ignore
    public void addCarToOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false);

        List<Car> cars = new ArrayList<Car>();
        
        cars.add(car1);
        cars.add(car2);
        
        dao.addOffice(office);
        
        dao.addCarToOffice(office, car1);
        dao.addCarToOffice(office, car2);
        
        Office gotOffice = dao.getOffice(office.getID());
        
        Assert.assertEquals(cars, gotOffice.getCars());
    }
    
    @Test
    @Ignore
    public void deleteEmployeeToOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
        
        List<User> employees = new ArrayList<User>();
        
        employees.add(manager);
        
        dao.addOffice(office);
        
        dao.addEmployeeToOffice(office, manager);
        dao.addEmployeeToOffice(office, employee);
        
        dao.deleteEmployeeFromOffice(office, employee);
        Office gotOffice = dao.getOffice(office.getID());
        
        Assert.assertEquals(employees, gotOffice.getEmployees());
    }
    
    @Test
    @Ignore
    public void deleteCarToOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false);

        List<Car> cars = new ArrayList<Car>();
        
        cars.add(car1);
        
        dao.addOffice(office);
        
        dao.addCarToOffice(office, car1);
        dao.addCarToOffice(office, car2);
        
        dao.deleteCarFromOffice(office, car2);
        
        Office gotOffice = dao.getOffice(office.getID());
        
        Assert.assertEquals(cars, gotOffice.getCars());
    }
}
