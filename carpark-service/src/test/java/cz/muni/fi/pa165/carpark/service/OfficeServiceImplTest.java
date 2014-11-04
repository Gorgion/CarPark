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
import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.Arrays;
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
    //@Ignore
    public void getOfficeEmployeesTest()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        officeService.addOffice(officeDto);
        
        Office office = Converter.getEntity(officeDto);
        List<User> employees = office.getEmployees();
        Mockito.doReturn(employees).when(officeDaoMocked).getEmployees(office);
        List<UserDto> employeesDto = Converter.getTransferObjectUserList(employees);
        
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(1L);
        List<UserDto> result = officeService.getEmployees(officeDto);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(employeesDto, result);
        
    }
    
    @Test
    //@Ignore
    public void getOfficeCarsTest()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        officeService.addOffice(officeDto);
        
        Office office = Converter.getEntity(officeDto);
        List<Car> cars = office.getCars();
        Mockito.doReturn(cars).when(officeDaoMocked).getOfficeCars(office);
        List<CarDto> carsDto = Converter.getTransferObjectCarList(cars);
        
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(1L);
        List<CarDto> result = officeService.getOfficeCars(officeDto);
        
        Assert.assertNotNull(result);
        Assert.assertEquals(carsDto, result);
        
    }
    
    @Test
    //@Ignore
    public void getAllOfficesTest()
    {
        OfficeDto office1 = TestUtils.createSampleDtoOffice();
        office1.setID(1L);
        OfficeDto office2 = TestUtils.createSampleDtoOffice();
        office1.setID(2L);
        OfficeDto office3 = TestUtils.createSampleDtoOffice();
        office1.setID(3L);
        
        List<OfficeDto> offices = new ArrayList<OfficeDto>();
        offices.add(office1);
        offices.add(office2);
        offices.add(office3);
        
        Mockito.doReturn(Converter.getEntityOfficeList(offices)).when(officeDaoMocked).getAllOffices();
        
        List<OfficeDto> result = officeService.getAllOffices();
        
        Assert.assertEquals(offices,result);
    }
    
    @Test
    //@Ignore
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
    
    @Test
    //@Ignore
    public void editOfficeTest()
    {
        CarDto car1 = TestUtils.createSampleDtoCar();
        car1.setID(5L);
        CarDto car2 = TestUtils.createSampleDtoCar();
        car1.setID(55L);
        
        
        List<CarDto> newCars = new ArrayList<>();       
        newCars.add(car1);
        newCars.add(car2);
        
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        Office office = Converter.getEntity(officeDto);
        
        Mockito.doNothing().when(officeDaoMocked).addOffice(office);
        
        officeService.addOffice(officeDto);
        
        officeDto.setCars(Converter.getEntityCarList(newCars));
        office = Converter.getEntity(officeDto);
        
        Mockito.doNothing().when(officeDaoMocked).editOffice(office);
        
        officeService.editOffice(officeDto);
        
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(1L);
        
        OfficeDto result = officeService.getOffice(1L);
        
        Assert.assertNotNull(result);
        
        Assert.assertEquals(officeDto, result);
        
        Assert.assertEquals(result.getAddress(), result.getAddress());
        Assert.assertEquals(officeDto.getCars(), result.getCars());
        Assert.assertEquals(officeDto.getEmployees(), result.getEmployees());
        
    }
    
    @Test
    //@Ignore
    public void addEmployeeToOfficeTest()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        Office office = Converter.getEntity(officeDto);
        UserDto employeeDto = TestUtils.createSampleDtoUser();
        
        List<UserDto> employees = new ArrayList<UserDto>();
        employees.add(employeeDto);
        
        
        Mockito.doNothing().when(officeDaoMocked).addOffice(office);
        officeService.addOffice(officeDto);
        Mockito.doNothing().when(userDaoMocked).add(Converter.getEntity(employeeDto));
        officeService.addEmployeeToOffice(officeDto, employeeDto);
         
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(1L);
        Mockito.doReturn(Arrays.asList(Converter.getEntity(employeeDto))).when(officeDaoMocked).getEmployees(office);
        
        List<UserDto> result = officeService.getEmployees(officeDto);
        
        Assert.assertEquals(employees, result);
    }
    
    @Test
    //@Ignore
    public void addCarToOfficeTest()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        Office office = Converter.getEntity(officeDto);
        CarDto carDto = TestUtils.createSampleDtoCar();
        
        List<CarDto> cars = new ArrayList<CarDto>();
        cars.add(carDto);
        
        
        Mockito.doNothing().when(officeDaoMocked).addOffice(office);
        officeService.addOffice(officeDto);
        Mockito.doNothing().when(carDaoMocked).AddCar(Converter.getEntity(carDto));
        officeService.addCarToOffice(officeDto, carDto);
         
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(1L);
        Mockito.doReturn(Arrays.asList(Converter.getEntity(carDto))).when(officeDaoMocked).getOfficeCars(office);
        
        List<CarDto> result = officeService.getOfficeCars(officeDto);
        
        Assert.assertEquals(cars, result);
    }
    
    @Test
    //@Ignore
    public void deleteEmployeeFromOfficeTest()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        Office office = Converter.getEntity(officeDto);
        
        List<User> employees = new ArrayList<User>(officeDto.getEmployees());
        
        User user = employees.get(1);
        
        employees.remove(1);
        
        Mockito.doNothing().when(officeDaoMocked).addOffice(office);
        officeService.addOffice(officeDto);
        
        officeService.deleteEmployeeFromOffice(officeDto, Converter.getTransferObject(user));
        Mockito.verify(officeDaoMocked,Mockito.times(1)).deleteEmployeeFromOffice(office, user);
        
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(1L);
        Mockito.doReturn(employees).when(officeDaoMocked).getEmployees(office);
        
        List<UserDto> result = officeService.getEmployees(officeDto);
        
        Assert.assertEquals(Converter.getTransferObjectUserList(employees), result);
    }
    
    @Test
    //@Ignore
    public void deleteCarFromOfficeTest()
    {
        OfficeDto officeDto = TestUtils.createSampleDtoOffice();
        officeDto.setID(1L);
        
        Office office = Converter.getEntity(officeDto);
        
        List<Car> cars = new ArrayList<Car>(officeDto.getCars());
        
        Car car = cars.get(3);
        
        cars.remove(3);
        
        Mockito.doNothing().when(officeDaoMocked).addOffice(office);
        officeService.addOffice(officeDto);
        
        officeService.deleteCarFromOffice(officeDto, Converter.getTransferObject(car));
        Mockito.verify(officeDaoMocked,Mockito.times(1)).deleteCarFromOffice(office, car);
        
        Mockito.doReturn(office).when(officeDaoMocked).getOffice(1L);
        Mockito.doReturn(cars).when(officeDaoMocked).getOfficeCars(office);
        
        List<CarDto> result = officeService.getOfficeCars(officeDto);
        
        Assert.assertEquals(Converter.getTransferObjectCarList(cars), result);
    }
    
    
}
