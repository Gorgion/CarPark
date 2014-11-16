/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.config.DaoTestConfig;
import cz.muni.fi.pa165.carpark.TestUtils;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
 * Unit tests of userDaoImpl class.
 *
 * @author Tomas Svoboda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Transactional
public class UserDaoTest
{
    @Inject
    private UserDao daoImpl;

    @Inject
    private CarDao carDao;

    @Inject
    private RentalDao rentalDao;

    @Test
    public void createUserTest()
    {
        User user = new User();
        user.setFirstName("Name");
        user.setLastName("Lastname");
        user.setPosition(User.Position.EMPLOYEE);
        user.setBirthNumber("999999/9547");
        user.setAddress("NY 401/20");

        daoImpl.add(user);

        User foundUser = daoImpl.getAll().get(0);

        Assert.assertNotNull(foundUser);
        Assert.assertEquals(user, foundUser);
    }

    @Test(expected = DataAccessException.class)//NullPointerException.class)
    public void testCreateUserWithNullArg()
    {
        daoImpl.add(null);
    }

    @Test
    public void testGet()
    {
        User user = new User();
        user.setFirstName("Name");
        user.setLastName("Lastname");
        user.setPosition(User.Position.EMPLOYEE);
        user.setBirthNumber("999999/9547");
        user.setAddress("NY 401/20");

        daoImpl.add(user);

        User foundUser = daoImpl.get(user.getId());

        Assert.assertNotNull(foundUser);
        Assert.assertEquals(user, foundUser);
    }

    @Test(expected = DataAccessException.class)//NullPointerException.class)
    public void testGetNulllId()
    {
        daoImpl.get(null);
    }

    @Test(expected = DataAccessException.class)//IllegalArgumentException.class)
    public void testGetNegativeId()
    {
        daoImpl.get(Long.MIN_VALUE);
    }

    @Test
    public void testGetMaxId()
    {
        Assert.assertNull(daoImpl.get(Long.MAX_VALUE));
    }

    @Test
    public void testGetAll()
    {
        User user = new User();
        user.setFirstName("Name");
        user.setLastName("Lastname");
        user.setPosition(User.Position.EMPLOYEE);
        user.setBirthNumber("999999/9547");
        user.setAddress("NY 401/20");

        User user2 = new User();
        user2.setFirstName("Noname");
        user2.setLastName("Surname");
        user2.setPosition(User.Position.ADMIN);
        user2.setBirthNumber("758999/9547");
        user2.setAddress("NY 101/20");

        User user3 = new User();
        user3.setFirstName("Name");
        user3.setLastName("Surname");
        user3.setPosition(User.Position.ADMIN);
        user3.setBirthNumber("755999/9547");
        user3.setAddress("NY 101/10");

        daoImpl.add(user);
        daoImpl.add(user2);
        daoImpl.add(user3);

        List<User> users = daoImpl.getAll();
        List<User> expectedUsers = Arrays.asList(user, user2, user3);

        Assert.assertNotSame(expectedUsers, users);

        Assert.assertTrue("Missing expected user.", users.containsAll(expectedUsers));
        users.removeAll(users);
        Assert.assertTrue("Containg foreign user.", users.isEmpty());
    }

    @Test
    public void testGetAllWithRent()
    {

        User user = TestUtils.createUser("Jiří", "Jirkovič", "Adresa 1", User.Position.MANAGER, "321bn");
        User user2 = TestUtils.createUser("Jan", "Jirkovič", "Adresa 1", User.Position.EMPLOYEE, "123bn");
        User user3 = TestUtils.createUser("Karel", "Karlovič", "Adresa 3", User.Position.EMPLOYEE, "456bn");
        User user4 = TestUtils.createUser("Pepa", "Pepovič", "Adresa 4", User.Position.EMPLOYEE, "112bn");

        Car car = TestUtils.createCar(Car.mBrand.SKODA, Car.mType.COMBI, Car.mColor.BLACK, Car.mEngine.PETROL, Car.mModel.FABIA, "LP1", "VIN1", false);

        daoImpl.add(user);
        daoImpl.add(user2);
        daoImpl.add(user3);
        daoImpl.add(user4);

        carDao.AddCar(car);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);

        Rental rental1 = TestUtils.createRental(car, Rental.State.FINISHED, user, new Date(), calendar.getTime());
        rentalDao.create(rental1);

        Rental rental2 = TestUtils.createRental(car, Rental.State.FINISHED, user2, new Date(), calendar.getTime());
        rentalDao.create(rental2);

        List<User> users = daoImpl.getAllWithRent();
        List<User> expectedUsers = Arrays.asList(user, user2);

        Assert.assertNotSame(expectedUsers, users);

        Assert.assertTrue("Missing expected user.", users.containsAll(expectedUsers));
        users.removeAll(users);
        Assert.assertTrue("Containg foreign user.", users.isEmpty());
    }

    @Test
    public void testGetAllWithoutRent()
    {
        User user = TestUtils.createUser("Jiří", "Jirkovič", "Adresa 1", User.Position.MANAGER, "321bn");
        User user2 = TestUtils.createUser("Jan", "Jirkovič", "Adresa 1", User.Position.EMPLOYEE, "123bn");
        User user3 = TestUtils.createUser("Karel", "Karlovič", "Adresa 3", User.Position.EMPLOYEE, "456bn");
        User user4 = TestUtils.createUser("Pepa", "Pepovič", "Adresa 4", User.Position.EMPLOYEE, "112bn");

        Car car = TestUtils.createCar(Car.mBrand.SKODA, Car.mType.COMBI, Car.mColor.BLACK, Car.mEngine.PETROL, Car.mModel.FABIA, "LP1", "VIN1", false);

        daoImpl.add(user);
        daoImpl.add(user2);
        daoImpl.add(user3);
        daoImpl.add(user4);

        carDao.AddCar(car);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);

        Rental rental1 = TestUtils.createRental(car, Rental.State.FINISHED, user, new Date(), calendar.getTime());
        rentalDao.create(rental1);

        Rental rental2 = TestUtils.createRental(car, Rental.State.FINISHED, user2, new Date(), calendar.getTime());
        rentalDao.create(rental2);

        List<User> actualUsers = daoImpl.getAllWithoutRent();
        List<User> expectedUsers = Arrays.asList(user3, user4);

        Assert.assertNotSame(expectedUsers, actualUsers);

        Assert.assertTrue("Missing expected user.", actualUsers.containsAll(expectedUsers));
        actualUsers.removeAll(actualUsers);
        Assert.assertTrue("Containg foreign user.", actualUsers.isEmpty());
    }

    @Test
    public void testEdit()
    {
        User user = new User();
        user.setFirstName("Name");
        user.setLastName("Lastname");
        user.setPosition(User.Position.EMPLOYEE);
        user.setBirthNumber("999999/9547");
        user.setAddress("NY 401/20");

        daoImpl.add(user);

        User updatedUser = new User();

        updatedUser.setId(user.getId());
        updatedUser.setLastName("Surname");
        updatedUser.setFirstName("NoName");
        updatedUser.setAddress("noAddress");
        updatedUser.setPosition(User.Position.ADMIN);
        updatedUser.setBirthNumber("1/0001");

        daoImpl.edit(updatedUser);

        User foundUser = daoImpl.get(user.getId());

        Assert.assertNotNull(foundUser);
        Assert.assertEquals(updatedUser, foundUser);
        Assert.assertNotSame(updatedUser, foundUser);

        Assert.assertEquals(updatedUser.getAddress(), foundUser.getAddress());
        Assert.assertEquals(updatedUser.getBirthNumber(), foundUser.getBirthNumber());
        Assert.assertEquals(updatedUser.getFirstName(), foundUser.getFirstName());
        Assert.assertEquals(updatedUser.getLastName(), foundUser.getLastName());
        Assert.assertEquals(updatedUser.getPosition(), foundUser.getPosition());
    }

    @Test(expected = DataAccessException.class)//NullPointerException.class)
    public void testEditWithNullArg()
    {
        daoImpl.edit(null);
    }

    @Test
    public void testDelete()
    {
        User user = new User();
        user.setFirstName("Name");
        user.setLastName("Lastname");
        user.setPosition(User.Position.EMPLOYEE);
        user.setBirthNumber("999999/9547");
        user.setAddress("NY 401/20");

        daoImpl.add(user);

        daoImpl.delete(user);

        List<User> foundUsers = daoImpl.getAll();

        Assert.assertNotNull(foundUsers);

        Assert.assertTrue("User found after deletion.", foundUsers.isEmpty());
    }

    @Test(expected = DataAccessException.class)//NullPointerException.class)
    public void testDeleteWithNullArg()
    {
        daoImpl.delete(null);
    }
}
