/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.dao.CarDao;
import cz.muni.fi.pa165.carpark.dao.OfficeDao;
import cz.muni.fi.pa165.carpark.dao.RentalDao;
import cz.muni.fi.pa165.carpark.dao.UserDao;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Jiri Dockal
 */

@RunWith(MockitoJUnitRunner.class)
public class OfficeServiceImplTest
{
    @InjectMocks
    private OfficeService officeService =  new OfficeServiceImpl();
    
    @Mock
    private OfficeDao officeDaoMocked;
    @Mock
    private CarDao carDaoMocked;
    @Mock
    private UserDao userDaoMocked;

      
    @Test(expected = DataAccessException.class)
    //@Ignore
    public void wrongAddOfficeTest()
    {
        Mockito.doThrow(IllegalArgumentException.class).when(officeDaoMocked).addOffice(null);
        officeService.addOffice(null);
    }
       
    @Test
    //@Ignore
    public void addGetOfficeTest()
    {
        
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        Office office = Converter.getEntity(officeDto);
          
        officeService.addOffice(officeDto);
        
        Mockito.verify(officeDaoMocked,Mockito.times(1)).addOffice(office);
        
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(office.getID());
        
        OfficeDto result = officeService.getOffice(1L);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(officeDto, result);
        Assert.assertEquals(officeDto.getID(), result.getID());
        Assert.assertEquals(officeDto.getAddress(),result.getAddress());
        Assert.assertEquals(officeDto.getCars(),result.getCars());
        Assert.assertEquals(officeDto.getEmployees(),result.getEmployees());
        Assert.assertEquals(officeDto.getManager(),result.getManager());
    }
    
    
    @Test
    @Ignore
    public void getOfficeEmployees()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        Office office = Converter.getEntity(officeDto);
        List<User> employees = office.getEmployees();
        Mockito.doReturn(employees).when(officeDaoMocked).getEmployees(office);
        
        List<UserDto> employeesDto = Converter.getTransferObject(employees);
        
        
        List<UserDto> result = officeService.getEmployees(officeDto);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(employeesDto, result);
        
    }
//    
//    @Test
//    @Ignore
//    public void getAllOfficesTest()
//    {
//        Office office1 = TestUtils.createOffice("Adresa 1", null, null, null);
//        Office office2 = TestUtils.createOffice("Adresa 2", null, null, null);
//        Office office3 = TestUtils.createOffice("Adresa 3", null, null, null);
//        
//        List<Office> offices = new ArrayList<Office>();
//        offices.add(office1);
//        offices.add(office2);
//        offices.add(office3);
//        
//        dao.addOffice(office1);
//        dao.addOffice(office2);
//        dao.addOffice(office3);
//        
//        Assert.assertEquals(dao.getAllOffices(),offices);
//    }
//    
    @Test
    @Ignore
    public void deleteOfficeTest()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        Office office = Converter.getEntity(officeDto);
        
        officeService.addOffice(officeDto);    
        
        officeService.deleteOffice(officeDto);
        
        Mockito.verify(officeDaoMocked,Mockito.times(1)).deleteOffice(office);
        
        Assert.assertNull(officeDaoMocked.getOffice(1L));
    }
//    
//    @Test
//    @Ignore
//    public void editOfficeTest()
//    {
//        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false);
//        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false);
//        Car car3 = TestUtils.createCar(mBrand.FORD, mType.HATCHBACK, mColor.RED, mEngine.DIESEL, mModel.FOCUS, "1A11111", "VIN222", false);
//        
//        carDao.AddCar(car1);
//        carDao.AddCar(car2);
//        carDao.AddCar(car3);
//        
//        List<Car> cars = new ArrayList<Car>();       
//        cars.add(car3);
//        cars.add(car2);
//        cars.add(car1);
//        
//        
//        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
//        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
//                
//        userDao.add(manager);
//        userDao.add(employee);
//        
//        List<User> employees = new ArrayList<User>();
//        employees.add(manager);
//        employees.add(employee);
//        
//        
//        String address = "Adresa 123";
//        Office office = TestUtils.createOffice(address, manager, null, employees);
//        
//                
//        User employee2 = TestUtils.createUser("Pepa", "Kounil", "Někde hodně daleko", User.Position.EMPLOYEE, "901212/1");
//        User manager2 = TestUtils.createUser("Honza", "Navrhal", "Někde úplně jinde", User.Position.MANAGER, "820101/4");
//        
//        userDao.add(manager2);
//        userDao.add(employee2);
//        
//        List<User> employees2 = new ArrayList<User>();
//        employees2.add(manager2);
//        employees2.add(employee2);
//        
//        
//        dao.addOffice(office);
//        
//        String address2 = "Adresa 321";
//                      
//        Office officeExp = TestUtils.createOffice(address2, manager2, cars, employees2);
//
//        officeExp.setID(office.getID());
//        
//        dao.editOffice(officeExp);
//        
//        Office officeUpdated = dao.getOffice(office.getID());
//        
//        Assert.assertNotNull(officeUpdated);
//        
//        Assert.assertEquals(officeExp, officeUpdated);
//        Assert.assertNotSame(officeExp, officeUpdated);
//
//        Assert.assertEquals(officeExp.getAddress(), officeUpdated.getAddress());
//        Assert.assertEquals(officeExp.getCars(), officeUpdated.getCars());
//        Assert.assertEquals(officeExp.getEmployees(), officeUpdated.getEmployees());
//        
//    }
//    
//    @Test
//    @Ignore
//    public void addEmployeeToOfficeTest()
//    {
//        String address = "Adresa 123";
//        Office office = TestUtils.createOffice(address, null, null, null);
//           
//        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
//        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
//        
//        List<User> employees = new ArrayList<User>();
//        
//        employees.add(manager);
//        employees.add(employee);
//        
//        dao.addOffice(office);
//        
//        userDao.add(manager);
//        userDao.add(employee);
//        
//        dao.addEmployeeToOffice(office, manager);
//        dao.addEmployeeToOffice(office, employee);
//        
//        Office gotOffice = dao.getOffice(office.getID());
//        
//        Assert.assertEquals(employees, gotOffice.getEmployees());
//    }
//    
//    @Test
//    @Ignore
//    public void addCarToOfficeTest()
//    {
//        String address = "Adresa 123";
//        Office office = TestUtils.createOffice(address, null, null, null);
//           
//        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false);
//        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false);
//
//        List<Car> cars = new ArrayList<Car>();
//        
//        cars.add(car1);
//        cars.add(car2);
//        
//        carDao.AddCar(car1);
//        carDao.AddCar(car2);
//        
//        dao.addOffice(office);
//        
//        dao.addCarToOffice(office, car1);
//        dao.addCarToOffice(office, car2);
//        
//        Office gotOffice = dao.getOffice(office.getID());
//        
//        Assert.assertEquals(cars, gotOffice.getCars());
//    }
//    
//    @Test
//    @Ignore
//    public void deleteEmployeeFromOfficeTest()
//    {
//        String address = "Adresa 123";
//        Office office = TestUtils.createOffice(address, null, null, null);
//           
//        User manager = TestUtils.createUser("Jiří", "Dočkal", "Někde daleko", User.Position.MANAGER, "901212/1234");
//        User employee = TestUtils.createUser("Honza", "Pracovník", "Někde jinde", User.Position.EMPLOYEE, "820101/4321");
//        
//        userDao.add(manager);
//        userDao.add(employee);
//        
//        List<User> employees = new ArrayList<User>();
//        
//        employees.add(manager);
//        
//        dao.addOffice(office);
//        
//        dao.addEmployeeToOffice(office, manager);
//        dao.addEmployeeToOffice(office, employee);
//        
//        dao.deleteEmployeeFromOffice(office, employee);
//        Office gotOffice = dao.getOffice(office.getID());
//        
//        Assert.assertEquals(employees, gotOffice.getEmployees());
//    }
//    
//    @Test
//    @Ignore
//    public void deleteCarFromOfficeTest()
//    {
//        String address = "Adresa 123";
//        Office office = TestUtils.createOffice(address, null, null, null);
//           
//        Car car1 = TestUtils.createCar(mBrand.SKODA, mType.COMBI, mColor.BLACK, mEngine.PETROL, mModel.FABIA, "TRB1962", "VIN123", false);
//        Car car2 = TestUtils.createCar(mBrand.CHEVROLET, mType.SEDAN, mColor.YELLOW, mEngine.PETROL, mModel.CAMARO, "1B21234", "VIN321", false);
//
//        carDao.AddCar(car1);
//        carDao.AddCar(car2);
//        
//        List<Car> cars = new ArrayList<Car>();
//        
//        cars.add(car1);
//        
//        dao.addOffice(office);
//        
//        dao.addCarToOffice(office, car1);
//        dao.addCarToOffice(office, car2);
//        
//        dao.deleteCarFromOffice(office, car2);
//        
//        Office gotOffice = dao.getOffice(office.getID());
//        
//        Assert.assertEquals(cars, gotOffice.getCars());
//    }
//    
    
}
