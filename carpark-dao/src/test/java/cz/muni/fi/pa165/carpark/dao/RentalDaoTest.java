/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.config.DaoTestConfig;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.Rental.State;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Tomáš Vašíček
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Transactional
public class RentalDaoTest
{
    @Inject
    private RentalDao rentalDao;
    @Inject
    private OfficeDao officeDao;
    @Inject
    private CarDao carDao; 
    @Inject
    private UserDao userDao; 

    @Test(expected = DataAccessException.class)
    public void testCreateRentalWithNull() {
        System.out.println("create rental with null");
        
        rentalDao.create(null);
        fail("Exception expected - rental is null");
    }


    @Test
    public void testCreateRental() {
        System.out.println("create rental");

        Office office = TestUtils.createSampleOffice();
        persistSampleOffice(office);
        
        Car car = office.getCars().get(0);  
        Date from = new Date();
        Date to = TestUtils.dateNow(3500L);
        State rentalState = State.FINISHED;
        User user = office.getEmployees().get(1);

        Rental rental = TestUtils.createRental(car, rentalState, user, from, to);

        rentalDao.create(rental);
        assertNotNull(rental.getId());

        assertEquals(rental.getCar(), car);
        assertEquals(rental.getFromDate(), from);
        assertEquals(rental.getToDate(), to);
        assertEquals(rental.getRentalState(), rentalState);

        try {
            rentalDao.create(rental);
            fail("Exception expected - rental is already created");
        } catch (DataAccessException ex) {
        }
    }

    private void persistSampleOffice(Office office) {
        for (Car car : office.getCars()) {
            carDao.AddCar(car);
        }
        for (User user : office.getEmployees()) {
            userDao.add(user);
        }
        officeDao.addOffice(office);
    }

    @Test
    public void testGetAllRental() {
        System.out.println("getAll");

        assertNotNull(rentalDao.getAll());
        assertEquals(rentalDao.getAll().size(), 0);
        
        Office office = TestUtils.createSampleOffice();       
        persistSampleOffice(office);    
       
        List<Rental> expectedRentals = new ArrayList<>();
        expectedRentals.add(TestUtils.createRental(
                office.getCars().get(0), State.FINISHED, office.getEmployees().get(0),
                new Date(), TestUtils.dateNow(3000L))
        );
        expectedRentals.add(TestUtils.createRental(
                office.getCars().get(1), State.NEW, office.getEmployees().get(1),
                new Date(), TestUtils.dateNow(2000L))
        );
        expectedRentals.add(TestUtils.createRental(
                office.getCars().get(2), State.ACTIVE, office.getEmployees().get(2),
                new Date(), TestUtils.dateNow(2850L))
        );
        expectedRentals.add(TestUtils.createRental(
                office.getCars().get(3), State.APPROVED, office.getEmployees().get(3),
                new Date(), TestUtils.dateNow(990L))
        );

        for (Rental rental : expectedRentals) {
            rentalDao.create(rental);
        }
        
        List<Rental> actualRentals = rentalDao.getAll();

        assertNotNull(actualRentals);
        assertEquals(expectedRentals, actualRentals);
        
        expectedRentals.removeAll(actualRentals);
        assertTrue(expectedRentals.isEmpty());
    }

    @Test(expected = DataAccessException.class)
    public void testGetAllRentalsByUserWithNull() {
        System.out.println("getAllByUser with null");

        rentalDao.getAllByUser(null);
        fail("Exception expected - user is null");
    }
    
    @Test(expected = DataAccessException.class)
    public void testGetAllRentalsByUserWithoutId() {
        System.out.println("getAllByUser without id");

        User user = TestUtils.createUser("Tomáš", "Tester", "Polní 25/8a, 755 00 Brno 5", "R1023456");
        rentalDao.getAllByUser(user);
        fail("Exception expected - user id is null");
    }

    @Test
    public void testGetAllRentalsByUser() {
        System.out.println("getAllByUser");

        assertEquals(rentalDao.getAll().size(), 0);
          
        Office office = TestUtils.createSampleOffice();       
        persistSampleOffice(office);
     
        User user = office.getEmployees().get(1);
        
        List<Rental> rentals = new ArrayList<Rental>();
        rentals.add(TestUtils.createRental(
                office.getCars().get(0), State.FINISHED, user,
                new Date(), TestUtils.dateNow(1000L))
        );
        rentals.add(TestUtils.createRental(
                office.getCars().get(1), State.NEW, user,
                new Date(), TestUtils.dateNow(2000L))
        );
        rentals.add(TestUtils.createRental(
                office.getCars().get(2), State.ACTIVE, user,
                new Date(), TestUtils.dateNow(3020L))
        );

        Rental notExpectedRental = TestUtils.createRental(
                office.getCars().get(3), State.APPROVED, office.getManager(),
                new Date(), TestUtils.dateNow(1000L)
        );
        rentals.add(notExpectedRental);

        for (Rental rental : rentals) {
            rentalDao.create(rental);
        }
        
        List<Rental> actualRentals = rentalDao.getAllByUser(user);
        assertNotNull(actualRentals);
        
        rentals.removeAll(actualRentals);
        assertTrue(rentals.contains(notExpectedRental));
        
        rentals.remove(notExpectedRental);
        assertTrue(rentals.isEmpty());
    }

    @Test(expected = DataAccessException.class)
    public void testGetWithNull() {
        System.out.println("get with null");
        
        rentalDao.get(null);
        fail("Exception expected - id is null");
    }

    @Test(expected = DataAccessException.class)
    public void testGetWithNegativeId() {
        System.out.println("get with negative id");
        
        rentalDao.get(1L - 255);
        fail("Exception expected - id is less than 0 is null");
    }
    
    @Test
    public void testGet() {
        System.out.println("get");

        Office office = TestUtils.createSampleOffice();
        persistSampleOffice(office);

        Car car = office.getCars().get(0);
        Date from = new Date();
        User user = office.getEmployees().get(1);
        Date to = TestUtils.dateNow(3000L);
        State rentalState = State.FINISHED;

        Rental rental = TestUtils.createRental(car, rentalState, user, from, to);
        rentalDao.create(rental);
        assertNotNull(rental.getId());

        Rental actualRental = rentalDao.get(rental.getId());

        assertEquals(rental.getId(), actualRental.getId());
        assertEquals(rental.getCar(), actualRental.getCar());
        assertEquals(rental.getRentalState(), actualRental.getRentalState());
        assertEquals(rental.getFromDate(), actualRental.getFromDate());
        assertEquals(rental.getToDate(), actualRental.getToDate());
        assertEquals(rental.getUser(), actualRental.getUser());
    }
    
    @Test(expected = DataAccessException.class)
    public void testDeleteWithNull() {
        System.out.println("delete with null");
        
        rentalDao.delete(null);
        fail("Exception expected - rental is null");
    }

    @Test
    public void testDelete() {
        System.out.println("delete");

        Office office = TestUtils.createSampleOffice();
        persistSampleOffice(office);       
        
        User user = office.getEmployees().get(1);
        Car car = office.getCars().get(0);
        
        Rental rental = TestUtils.createRental(car, State.FINISHED, user, new Date(), TestUtils.dateNow(3000L));
        rentalDao.create(rental);
        rentalDao.delete(rental);

        assertNull(rentalDao.get(rental.getId()));
    }
}
