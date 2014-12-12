/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.dao.CarDao;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.mockito.Mockito;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Arrays;
import java.util.Date;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Karolina Burska
 */

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {
    
    @InjectMocks
    private final CarService carService =  new CarServiceImpl();
    
    @Mock
    private CarDao carDM;

    @Test
    public void addNewCarTest() { 
        CarDto carDto = new CarDto(CarDto.mBrand.SKODA_FABIA, CarDto.mType.COMBI, CarDto.mEngine.PETROL, "LP", "VIN", false, null);
        carDto.setId(1L);
        
        Car car = Converter.getEntity(carDto);
        
        Mockito.doReturn(1L).when(carDM).addCar(car);

        carService.addCar(carDto);

        carDto.setId(car.getId());

        Mockito.doReturn(car).when(carDM).getCar(carDto.getId());

        CarDto actualDto = carService.getCar(car.getId());
        
        Assert.assertNotNull(actualDto);
        Assert.assertEquals(carDto, actualDto);
        Assert.assertEquals(carDto.getId(), actualDto.getId());
        Assert.assertEquals(carDto.getLicencePlate(), actualDto.getLicencePlate());
        Assert.assertEquals(carDto.getVIN(), actualDto.getVIN());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addCarWithNullTest() {
        Mockito.doThrow(new IllegalArgumentException("Car is null") {}).when(carDM).addCar(null);
        carService.addCar(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void getCarWithWrongIdTest(){
        Mockito.doThrow(new DataAccessException("") {}).when(carDM).getCar(Long.MIN_VALUE);
        carService.getCar(Long.MIN_VALUE);
    }
    
    @Test
    public void editCarTest(){
        CarDto carDto = new CarDto(CarDto.mBrand.FORD_MONDEO, CarDto.mType.COMBI, CarDto.mEngine.PETROL, "LP", "VIN", false, null);
        carDto.setId(1L);
        
        Car car = Converter.getEntity(carDto);
        
        Mockito.doNothing().when(carDM).editCar(car);

        carService.editCar(carDto);

        carDto.setId(car.getId());

        Mockito.doReturn(car).when(carDM).getCar(car.getId());

        CarDto actualDto = carService.getCar(car.getId());

        Assert.assertNotNull(actualDto);
        Assert.assertEquals(carDto, actualDto);
        Assert.assertEquals(carDto.getId(), actualDto.getId());
        Assert.assertEquals(carDto.getId(), actualDto.getId());
        Assert.assertEquals(carDto.getLicencePlate(), actualDto.getLicencePlate());
        Assert.assertEquals(carDto.getVIN(), actualDto.getVIN());
    }
    
    @Test(expected = DataAccessException.class)
    public void editCarWithNullTest(){
        Mockito.doThrow(new DataAccessException("") {}).when(carDM).editCar(null);
        carDM.editCar(null);
    }
    @Test
    public void deleteCarTest(){
        CarDto carDto = new CarDto(CarDto.mBrand.SKODA_OCTAVIA, CarDto.mType.COMBI, CarDto.mEngine.PETROL, "LP", "VIN", false, null);
        carDto.setId(1L);
        
        Car car = Converter.getEntity(carDto);             
        carService.addCar(carDto);         
        carService.deleteCar(carDto);     
        
        Mockito.verify(carDM, Mockito.times(1)).deleteCar(car);   
        
        Assert.assertNull(carDM.getCar(1L));
    }
    
    @Test(expected = DataAccessException.class)
    public void deleteCarWithNullTest(){
        Mockito.doThrow(new DataAccessException("") {}).when(carDM).deleteCar(null);
        carDM.deleteCar(null);
    }
    
    @Test
    public void getAllCarsTest(){
        CarDto carDto1 = new CarDto(CarDto.mBrand.SKODA_FABIA, CarDto.mType.COMBI, CarDto.mEngine.PETROL, "LP", "VIN", false, null);
        CarDto carDto2 = new CarDto(CarDto.mBrand.FORD_MONDEO, CarDto.mType.HATCHBACK, CarDto.mEngine.DIESEL, "LP2", "VIN2", false, null);
        CarDto carDto3 = new CarDto(CarDto.mBrand.SKODA_OCTAVIA, CarDto.mType.COMBI, CarDto.mEngine.DIESEL, "LP3", "VIN3", false, null);
        carDto1.setId(1L);
        carDto2.setId(2L);
        carDto3.setId(3L);
        
        Mockito.doReturn(Arrays.asList(Converter.getEntity(carDto1), Converter.getEntity(carDto2), Converter.getEntity(carDto3))).when(carDM).getAllCars();

        Collection<CarDto> cars = carService.getAllCars();
        List<CarDto> rightCars = new ArrayList<>();
        rightCars.add(carDto1);
        rightCars.add(carDto2);
        rightCars.add(carDto3);

        Assert.assertNotSame(cars, rightCars);
        Assert.assertTrue("Missing expected car. ", rightCars.containsAll(cars)); 
        Assert.assertTrue("Missing car1. ", cars.contains(carDto1));
        Assert.assertTrue("Missing car2. ", cars.contains(carDto2));
        Assert.assertTrue("Missing car3. ", cars.contains(carDto3));
        Assert.assertTrue("Size of collection does not fit. ", cars.size() == 3);
    }
    
    @Test
    public void getRentedCarsTest(){
        CarDto carDto1 = new CarDto(CarDto.mBrand.FORD_FOCUS, CarDto.mType.COMBI, CarDto.mEngine.PETROL, "LP", "VIN", true, null);
        CarDto carDto2 = new CarDto(CarDto.mBrand.SKODA_OCTAVIA, CarDto.mType.HATCHBACK, CarDto.mEngine.DIESEL, "LP2", "VIN2", false, null);
        CarDto carDto3 = new CarDto(CarDto.mBrand.SKODA_SUPERB, CarDto.mType.COMBI, CarDto.mEngine.DIESEL, "LP3", "VIN3", true, null);
        carDto1.setId(1L);
        carDto2.setId(2L);
        carDto3.setId(3L);
        
        Mockito.doReturn(Arrays.asList(Converter.getEntity(carDto1), Converter.getEntity(carDto3))).when(carDM).getRentedCars();
        
        Collection<CarDto> cars = carService.getRentedCars();
        System.out.println(cars);

        List<CarDto> rentedCars = new ArrayList<>();
        rentedCars.add(carDto1);
        rentedCars.add(carDto3);
        
        Assert.assertTrue("Missing expected car. ", rentedCars.containsAll(cars));   
        Assert.assertTrue("Missing car1. ", cars.contains(carDto1));
        Assert.assertTrue("Car2 should not be in the collection. ", !cars.contains(carDto2));
        Assert.assertTrue("Missing car3. ", cars.contains(carDto3));
        Assert.assertTrue("Size of collection does not fit. ", cars.size() == 2);
    }
    
    @Test
    public void getFreeCarsTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        
	Date from1 = sdf.parse("01-08-2014 10:20:30");
        Date to1 = sdf.parse("31-08-2014 10:20:30"); 
        
	Date from2 = sdf.parse("02-09-2014 10:20:30");
        Date to2 = sdf.parse("10-09-2014 10:20:30"); 
        
        CarDto carDto = TestUtils.createSampleDtoCar();
        carDto.setId(1L);

        Mockito.doReturn(Arrays.asList(Converter.getEntity(carDto))).when(carDM).getFreeCars(from2, to2);
        
        Assert.assertTrue("Car is not avaliable", !(carService.getFreeCars(from1, to1).contains(carDto)));
        Assert.assertTrue("Car is avaliable", carService.getFreeCars(from2, to2).contains(carDto));
        Assert.assertTrue("Car is not avaliable", !(carService.getFreeCars(from1, to2).contains(carDto)));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void getFreeCarsWitWrongArgTest() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        
	Date from = sdf.parse("01-08-2014 10:20:30");
        Date to = sdf.parse("31-08-2014 10:20:30"); 
        
        CarDto carDto = TestUtils.createSampleDtoCar();
        carDto.setId(1L);
        
        Car car = Converter.getEntity(carDto);
        
        Mockito.doThrow(IllegalArgumentException.class).when(carDM).getFreeCars(to, from);
        
        carDM.getFreeCars(to, from);
    }
}
