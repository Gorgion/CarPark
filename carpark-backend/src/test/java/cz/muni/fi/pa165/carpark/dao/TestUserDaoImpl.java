/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

//import cz.muni.fi.pa165.*;//carpark.dao.*;//UserDaoImpl;
import cz.muni.fi.pa165.carpark.entity.User;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Tomas Svoboda
 */
public class TestUserDaoImpl
{
    private UserDaoImpl daoImpl;    
//    private Rental r;
    
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
////        user.setAddress(null);
//        
        daoImpl.add(user);
//        
        User foundUser = daoImpl.getAll().get(0);
//        
//        Assert.assertNotNull(foundUser);
//        Assert.assertEquals(user, foundUser);
//        Assert.assertNotSame(user, foundUser);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserWithNullArg()
    {
        daoImpl.add(null);
    }
}
