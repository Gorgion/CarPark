/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

//import cz.muni.fi.pa165.*;//carpark.dao.*;//UserDaoImpl;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Tomas Svoboda
 */
public class TestUserDaoImpl
{
    private UserDaoImpl daoImpl;  
    
    public TestUserDaoImpl()
    {
        
    }
    
    @Before
    public void setUp()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        daoImpl = new UserDaoImpl();
        daoImpl.setEmf(emf);
    }
    
    @After
    public void tearDown()
    {
    }
    
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
        Assert.assertNotSame(user, foundUser);
    }
    
    @Test(expected = NullPointerException.class)
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
        Assert.assertNotSame(user, foundUser);
    }
    
    @Test(expected = NullPointerException.class)
    public void testGetNulllId()
    {
        daoImpl.get(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetNegativeId()
    {
        daoImpl.get(Long.MIN_VALUE);
    }
    
    @Ignore
    @Test//CHANGE IMPL
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
        
        Assert.assertTrue("Missing expected user.",users.containsAll(expectedUsers));
        users.removeAll(users);
        Assert.assertTrue("Containg foreign user.",users.isEmpty());        
    }
    
    @Ignore //+ without
    @Test
    public void testGetAllWithRent()
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
        
        Assert.assertTrue("Missing expected user.",users.containsAll(expectedUsers));
        users.removeAll(users);
        Assert.assertTrue("Containg foreign user.",users.isEmpty());        
    }
    
    @Test
    @Ignore
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
        user.setLastName("Surname");
        user.setFirstName("NoName");
        user.setAddress("noAddress");
        user.setPosition(User.Position.ADMIN);
        user.setBirthNumber("1/0001");
        
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
    
    @Test(expected = NullPointerException.class)
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
    
    @Test(expected = NullPointerException.class)
    public void testDeleteWithNullArg()
    {
        daoImpl.delete(null);
    }
}
