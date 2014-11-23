/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;



import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.dao.RentalDao;
import cz.muni.fi.pa165.carpark.dao.UserDao;
import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Tomáš Vašíček
 */
@RunWith(MockitoJUnitRunner.class)
public class RentalServiceImplTest {
    
    @InjectMocks
    private RentalService rentalService = new RentalServiceImpl();

    @Mock
    private RentalDao mockedRentalDao;
    
    @Mock
    private static UserDao mockedUserDao;
   
    @Test
    public void testCreateAndGet()
    {
        RentalDto rentalDto = createRentalSampleDto();
        Rental rental = Converter.getEntity(rentalDto);
          
        Mockito.doNothing().when(mockedRentalDao).create(rental);    
        rentalService.create(rentalDto);
        rentalDto.setId(rental.getId());

        Mockito.doReturn(rental).when(mockedRentalDao).get(rental.getId());    
        RentalDto actualDto = rentalService.get(rental.getId());

        Assert.assertNotNull(actualDto);
        Assert.assertEquals(rentalDto, actualDto);
        Assert.assertEquals(rentalDto.getId(), actualDto.getId());
        Assert.assertEquals(rentalDto.getCar(), actualDto.getCar());
        Assert.assertEquals(rentalDto.getFromDate(), actualDto.getFromDate());
        Assert.assertEquals(rentalDto.getRentalState(), actualDto.getRentalState());
        Assert.assertEquals(rentalDto.getToDate(), actualDto.getToDate());
        Assert.assertEquals(rentalDto.getUser(), actualDto.getUser());
    }
       
    @Test(expected = DataAccessException.class)
    public void testCreateWithNullArg()
    {
        Mockito.doThrow(new DataAccessException("") {}).when(mockedRentalDao).create(null);
        rentalService.create(null);
    }
    
    @Test
    public void testEdit()
    {
        RentalDto rentalDto = createRentalSampleDto();
        Rental rental = Converter.getEntity(rentalDto);
        rental.setId(1L);
        
        Mockito.doNothing().when(mockedRentalDao).edit(rental);
        rentalService.edit(rentalDto);
        
        rentalDto.setId(rental.getId());
        Mockito.doReturn(rental).when(mockedRentalDao).get(rental.getId());      
        RentalDto actualDto = rentalService.get(rental.getId());

        Assert.assertNotNull(actualDto);
        Assert.assertEquals(rentalDto, actualDto);
        Assert.assertEquals(rentalDto.getId(), actualDto.getId());
        Assert.assertEquals(rentalDto.getCar(), actualDto.getCar());
        Assert.assertEquals(rentalDto.getFromDate(), actualDto.getFromDate());
        Assert.assertEquals(rentalDto.getToDate(), actualDto.getToDate());
        Assert.assertEquals(rentalDto.getRentalState(), actualDto.getRentalState());
        Assert.assertEquals(rentalDto.getUser(), actualDto.getUser());
    }
    
    @Test(expected = DataAccessException.class)
    public void testEditWithNullArg()
    {
        Mockito.doThrow(new DataAccessException("") {}).when(mockedRentalDao).edit(null);
        rentalService.edit(null);
    }
    
    @Test(expected = DataAccessException.class)
    public void testGetNegativeId()
    {
        Mockito.doThrow(new DataAccessException("") {}).when(mockedRentalDao).get(Long.MIN_VALUE);
        rentalService.get(Long.MIN_VALUE);
    }
    
    @Test
    public void testDelete()          
    {        
        RentalDto rentalDto = createRentalSampleDto();
        rentalDto.setId(1L);
        
        Rental rental = Converter.getEntity(rentalDto);             
        rentalService.create(rentalDto);         
        rentalService.delete(rentalDto);     
        
        Mockito.verify(mockedRentalDao, Mockito.times(1)).delete(rental);       
        Assert.assertNull(mockedRentalDao.get(1L));
    }
    
    @Test(expected = DataAccessException.class)
    public void testDeleteWithNullArg()
    {
        Mockito.doThrow(new DataAccessException("") {}).when(mockedRentalDao).delete(null);
        rentalService.delete(null);
    }
    
    @Test
    public void testGetAll()
    {
        RentalDto rental1 = createRentalSampleDto();
        RentalDto rental2 = createRentalSampleDto();
        RentalDto rental3 = createRentalSampleDto();
        rental1.setId(1L);
        rental2.setId(2L);
        rental3.setId(3L);
        
        Mockito.doReturn(Arrays.asList(Converter.getEntity(rental1), Converter.getEntity(rental2), Converter.getEntity(rental3)))
        .when(mockedRentalDao).getAll();
        
        List<RentalDto> rentals = rentalService.getAll();
        Assert.assertNotNull(rentals);
        List<RentalDto> expectedRentals = Arrays.asList(rental1, rental2, rental3);
        Assert.assertEquals(expectedRentals, rentals);
    }
    
    
    //TODO
    @Test
    public void testGetAllByUser()
    {        
        RentalDto rental1 = createRentalSampleDto();
        RentalDto rental2 = createRentalSampleDto();
        RentalDto rental3 = createRentalSampleDto();
        rental1.setId(1L);
        rental2.setId(2L);
        rental3.setId(3L);
        
        Mockito.doReturn(Arrays.asList(Converter.getEntity(rental1), Converter.getEntity(rental2)))
        .when(mockedRentalDao).getAllByUser(Converter.getEntity(rental2.getUser()));
       
        UserDto userDto = rental2.getUser();
        
        List<RentalDto> actualRentals = rentalService.getAllByUser(userDto);
        Assert.assertNotNull(actualRentals);
        List<RentalDto> expectedRentals = Arrays.asList(rental1, rental2);
        Assert.assertEquals(expectedRentals, actualRentals);
    }
    
    
    private static RentalDto createRentalSampleDto()
    { 
        RentalDto rentalDto = new RentalDto();

        rentalDto.setCar(TestUtils.createSampleDtoCar());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);   
        rentalDto.setFromDate(calendar.getTime());
        calendar.add(Calendar.HOUR, 2);   
        rentalDto.setToDate(calendar.getTime());
        rentalDto.setRentalState(RentalDto.State.ACTIVE);
        
        User user = new User();
        user.setFirstName("name");
        user.setLastName("surname");
        user.setAddress("address");
        user.setBirthNumber("9875698/4587");
        
        rentalDto.setUser(Converter.getTransferObject(user));

        return rentalDto;
    }
    
}
