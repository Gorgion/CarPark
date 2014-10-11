/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.Rental.State;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.entity.User.Position;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Tomáš Vašíček
 */
public class RentalDaoTest {

    private RentalDao dao;
    private EntityManagerFactory entityManagerFactory;

    public RentalDaoTest() {
    }

    @Before
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestPU");
        dao = new RentalDaoImpl();
        dao.setEmf(entityManagerFactory);
    }

    @After
    public void tearDown() {
        dao = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRentalWithNull() {
        System.out.println("create rental with null");
        
        dao.create(null);
        fail("Exception expected - rental is null");
    }


    @Test
    public void testCreateRental() {
        System.out.println("create rental");

        Office office = TestUtils.createSampleOffice();
        Car car = office.getCars().get(0);
        
        Date from = new Date();
        User user = TestUtils.createUser("Tomáš", "Tester", "Polní 25/8a, 755 00 Brno 5", Position.EMPLOYEE, "R1023456");
        Date to = TestUtils.dateNow(3500L);
        State rentalState = State.FINISHED;

        Rental rental = TestUtils.createRental(car, rentalState, user, from, to);

        dao.create(rental);
        assertNotNull(rental.getId());

        assertEquals(rental.getCar(), car);
        assertEquals(rental.getFromDate(), from);
        assertEquals(rental.getToDate(), to);
        assertEquals(rental.getRentalState(), rentalState);

        //create rental again - with id
        try {
            dao.create(rental);
            fail("Exception expected - rental is already created");
        } catch (IllegalArgumentException ex) {
            //assertEquals(ex.getClass(), IllegalArgumentException.class);
        }
    }

    @Test
    public void testGetAllRental() {
        System.out.println("getAll");

        assertNotNull(dao.getAll());
        assertEquals(dao.getAll().size(), 0);
        
        Office office = TestUtils.createSampleOffice();
        
        OfficeDao officeDao = new OfficeDaoImpl();
        officeDao.setEMF(entityManagerFactory);
        officeDao.addOffice(office);
        
        Car car = office.getCars().get(0);
       
        //TODO set car and address instead null 
        Set<Rental> setOfRentals = new HashSet<Rental>();
        setOfRentals.add(TestUtils.createRental(
                office.getCars().get(0), State.FINISHED, office.getEmployees().get(0),
                new Date(), TestUtils.dateNow(3000L))
        );
        setOfRentals.add(TestUtils.createRental(
                office.getCars().get(1), State.NEW, office.getEmployees().get(1),
                new Date(), TestUtils.dateNow(2000L))
        );
        setOfRentals.add(TestUtils.createRental(
                office.getCars().get(2), State.ACTIVE, office.getEmployees().get(2),
                new Date(), TestUtils.dateNow(2850L))
        );
        setOfRentals.add(TestUtils.createRental(
                office.getCars().get(3), State.APPROVED, office.getEmployees().get(3),
                new Date(), TestUtils.dateNow(990L))
        );

        for (Rental rental : setOfRentals) {
            dao.create(rental);
        }

        //assertEquals(setOfRentals.size(), dao.getAll().size());
        assertEquals(setOfRentals, new HashSet(dao.getAll()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetAllRentalsByUserWithNull() {
        System.out.println("getAllByUser with null");

        dao.getAllByUser(null);
        fail("Exception expected - user is null");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllRentalsByUserWithoutId() {
        System.out.println("getAllByUser without id");

        User user = TestUtils.createUser("Tomáš", "Tester", "Polní 25/8a, 755 00 Brno 5", Position.EMPLOYEE, "R1023456");
        dao.getAllByUser(user);
        fail("Exception expected - user id is null");
    }

    @Ignore
    @Test
    public void testGetAllRentalsByUser() {
        System.out.println("getAllByUser");

        assertEquals(dao.getAll().size(), 0);

        UserDao userDao = new UserDaoImpl();
        userDao.setEmf(entityManagerFactory);
        
        User user = TestUtils.createUser("Tomáš", "Tester", "Polní 25/8a, 755 00 Brno 5", Position.EMPLOYEE, "R1023456");
        userDao.add(user);

        //TODO set car and address instead null 
        Set<Rental> setOfRentals = new HashSet<Rental>();
        setOfRentals.add(TestUtils.createRental(
                null, State.FINISHED, user,
                new Date(), TestUtils.dateNow(1000L))
        );
        setOfRentals.add(TestUtils.createRental(
                null, State.NEW, user,
                new Date(), TestUtils.dateNow(2000L))
        );
        setOfRentals.add(TestUtils.createRental(
                null, State.ACTIVE, user,
                new Date(), TestUtils.dateNow(3020L))
        );

        User otherUser = TestUtils.createUser("Kateřina", "Tester4", "Mincovní 15, 759 00 Brno", Position.MANAGER, "Z1623456");
        setOfRentals.add(TestUtils.createRental(
                null, State.APPROVED, otherUser,
                new Date(), TestUtils.dateNow(1000L))
        );

        for (Rental rental : setOfRentals) {
            dao.create(rental);
        }
        setOfRentals.remove(otherUser);

        assertEquals(setOfRentals, new HashSet(dao.getAllByUser(user)));
        assertEquals(1, dao.getAllByUser(otherUser).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWithNull() {
        System.out.println("get with null");
        
        dao.get(null);
        fail("Exception expected - id is null");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetWithNegativeId() {
        System.out.println("get with negative id");
        
        dao.get(1L - 255);
        fail("Exception expected - id is less than 0 is null");
    }
    
    @Test
    public void testGet() {
        System.out.println("get");

        Office office = TestUtils.createSampleOffice();
        Car car = office.getCars().get(0);
        Date from = new Date();
        User user = office.getEmployees().get(1);
        Date to = TestUtils.dateNow(3000L);
        State rentalState = State.FINISHED;

        Rental rental = TestUtils.createRental(car, rentalState, user, from, to);
        dao.create(rental);
        assertNotNull(rental.getId());

        Rental persistRental = dao.get(rental.getId());

        assertEquals(rental.getId(), persistRental.getId());
        assertEquals(rental.getCar(), persistRental.getCar());
        assertEquals(rental.getRentalState(), persistRental.getRentalState());
        assertEquals(rental.getFromDate(), persistRental.getFromDate());
        assertEquals(rental.getToDate(), persistRental.getToDate());
        assertEquals(rental.getUser(), persistRental.getUser());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteWithNull() {
        System.out.println("delete with null");
        
        dao.delete(null);
        fail("Exception expected - rental is null");
    }

    @Test
    public void testDelete() {
        System.out.println("delete");

        Office office = TestUtils.createSampleOffice();
        User user = office.getEmployees().get(1);
        Car car = office.getCars().get(0);
        
        Rental rental = TestUtils.createRental(car, State.FINISHED, user, new Date(), TestUtils.dateNow(3000L));
        dao.create(rental);
        dao.delete(rental);

        assertNull(dao.get(rental.getId()));
    }
}
