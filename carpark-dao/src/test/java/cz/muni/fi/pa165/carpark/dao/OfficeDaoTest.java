/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.config.DaoTestConfig;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Car.mBrand;
import cz.muni.fi.pa165.carpark.entity.Car.mEngine;
import cz.muni.fi.pa165.carpark.entity.Car.mType;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Jiri Dockal
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Transactional
public class OfficeDaoTest
{
    @Inject
    private OfficeDao dao;
    @Inject
    private CarDao carDao;
    @Inject
    private UserDao userDao;
    
    @Test(expected = DataAccessException.class)
    public void wrongAddOfficeTest()
    {
        dao.addOffice(null);
    }
       
    @Test(expected = DataAccessException.class)
    public void addGetOfficeTest()
    {
        String address = "Adresa 123";
                
        Office office = TestUtils.createOffice(address, null, null, null);
        
        dao.addOffice(office);
               
        Assert.assertNotNull(dao.getAllOffices());
        
        Assert.assertEquals(office, dao.getOffice(office.getID()));
        Assert.assertEquals(office.getAddress(), address);
                
        
        dao.addOffice(office);
        Assert.fail("Office already added - exception expected");
        
    }
    
    @Test
    public void getOfficeCars()
    {
        String address = "Adresa 123";
        
        Car car1 = TestUtils.createCar(mBrand.SKODA_OCTAVIA, mType.COMBI, mEngine.PETROL, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.FORD_MONDEO, mType.SEDAN, mEngine.PETROL, "1B21234", "VIN321", false);
        Car car3 = TestUtils.createCar(mBrand.FORD_FOCUS, mType.HATCHBACK, mEngine.DIESEL, "1A11111", "VIN222", false);
        
        List<Car> cars = new ArrayList<Car>();
        
        cars.add(car3);
        cars.add(car2);
        cars.add(car1);        
        
        Office office = TestUtils.createOffice(address, null, cars, null);
        
        dao.addOffice(office);
        
        car1.setOffice(office);
        car2.setOffice(office);
        car3.setOffice(office);
        
        carDao.AddCar(car1);
        carDao.AddCar(car2);
        carDao.AddCar(car3);
        
        
        Assert.assertEquals(dao.getOfficeCars(office),cars);
    }
    
    @Test
    public void getOfficeEmployees()
    {
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", "820101/4321");
        
        List<User> employees = new ArrayList<User>();
        employees.add(manager);
        employees.add(employee);
        
        String address = "Adresa 123";
        
        Office office = TestUtils.createOffice(address, manager, null, employees);
        
        manager.setOffice(office);
        employee.setOffice(office);
        
        dao.addOffice(office);
        
        userDao.add(manager);
        userDao.add(employee);        
        
        Assert.assertEquals(dao.getEmployees(office),employees);
    }
    
    @Test
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
    
    @Test(expected = DataAccessException.class)
    public void deleteOfficeTest()
    {
        
        dao.deleteOffice(null);
        Assert.fail("Office cannot be null - exception expected");
        
        
        Office office = TestUtils.createOffice("Adresa 1", null, null, null);
        
        dao.addOffice(office);
        dao.deleteOffice(office);
       
        Assert.assertNull(dao.getOffice(office.getID()));
              
    }
    
    @Test
    public void editOfficeTest()
    {
        Car car1 = TestUtils.createCar(mBrand.SKODA_FABIA, mType.COMBI, mEngine.PETROL, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.SKODA_SUPERB, mType.SEDAN, mEngine.PETROL, "1B21234", "VIN321", false);
        Car car3 = TestUtils.createCar(mBrand.FORD_MONDEO, mType.HATCHBACK, mEngine.DIESEL, "1A11111", "VIN222", false);
        
        carDao.AddCar(car1);
        carDao.AddCar(car2);
        carDao.AddCar(car3);
        
        List<Car> cars = new ArrayList<Car>();       
        cars.add(car3);
        cars.add(car2);
        cars.add(car1);
        
        
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", "820101/4321");
                
        userDao.add(manager);
        userDao.add(employee);
        
        List<User> employees = new ArrayList<User>();
        employees.add(manager);
        employees.add(employee);
        
        
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, manager, null, employees);
        
                
        User employee2 = TestUtils.createUser("Pepa", "Kounil", "Někde hodně daleko", "901212/1");
        User manager2 = TestUtils.createUser("Honza", "Navrhal", "Někde úplně jinde", "820101/4");
        
        userDao.add(manager2);
        userDao.add(employee2);
        
        List<User> employees2 = new ArrayList<User>();
        employees2.add(manager2);
        employees2.add(employee2);
        
        
        dao.addOffice(office);
        
        String address2 = "Adresa 321";
                      
        Office officeExp = TestUtils.createOffice(address2, manager2, cars, employees2);

        officeExp.setID(office.getID());
        
        dao.editOffice(officeExp);
        
        Office officeUpdated = dao.getOffice(office.getID());
        
        Assert.assertNotNull(officeUpdated);
        
        Assert.assertEquals(officeExp, officeUpdated);
        Assert.assertNotSame(officeExp, officeUpdated);

        Assert.assertEquals(officeExp.getAddress(), officeUpdated.getAddress());
        Assert.assertEquals(officeExp.getCars(), officeUpdated.getCars());
        Assert.assertEquals(officeExp.getEmployees(), officeUpdated.getEmployees());
        
    }
    
    @Test
    public void addEmployeeToOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", "820101/4321");
        
        List<User> employees = new ArrayList<User>();
        
        employees.add(manager);
        employees.add(employee);
        
        dao.addOffice(office);
        
        userDao.add(manager);
        userDao.add(employee);
        
        dao.addEmployeeToOffice(office, manager);
        dao.addEmployeeToOffice(office, employee);
        
        Office gotOffice = dao.getOffice(office.getID());
        
        Assert.assertEquals(employees, gotOffice.getEmployees());
    }
    
    @Test
    public void addCarToOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        Car car1 = TestUtils.createCar(mBrand.SKODA_FABIA, mType.COMBI, mEngine.PETROL, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.SKODA_OCTAVIA, mType.SEDAN, mEngine.PETROL, "1B21234", "VIN321", false);

        List<Car> cars = new ArrayList<Car>();
        
        cars.add(car1);
        cars.add(car2);
        
        carDao.AddCar(car1);
        carDao.AddCar(car2);
        
        dao.addOffice(office);
        
        dao.addCarToOffice(office, car1);
        dao.addCarToOffice(office, car2);
        
        Office gotOffice = dao.getOffice(office.getID());
        
        Assert.assertEquals(cars, gotOffice.getCars());
    }
    
    @Test
    public void deleteEmployeeFromOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", "901212/1234");
        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", "820101/4321");
        
        userDao.add(manager);
        userDao.add(employee);
        
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
    public void deleteCarFromOfficeTest()
    {
        String address = "Adresa 123";
        Office office = TestUtils.createOffice(address, null, null, null);
           
        Car car1 = TestUtils.createCar(mBrand.SKODA_OCTAVIA, mType.COMBI, mEngine.PETROL, "TRB1962", "VIN123", false);
        Car car2 = TestUtils.createCar(mBrand.SKODA_SUPERB, mType.SEDAN, mEngine.PETROL, "1B21234", "VIN321", false);

        carDao.AddCar(car1);
        carDao.AddCar(car2);
        
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
