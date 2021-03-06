/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.config.DaoTestConfig;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.entity.UserRole;
import cz.muni.fi.pa165.carpark.entity.UserCredentials;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for user credentials dao.
 *
 * @author Tomas Svoboda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class)
@Transactional
public class UserCredentialsDaoTest
{

    @Inject
    private UserCredentialsDao userCredentialsDao;

    @Inject
    private UserDao userDao;

    @Test
    public void testCreateUserCredentialsGetUserCredentials()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        userCredentialsDao.create(expectedUC);

        UserCredentials actualUC = userCredentialsDao.getByUsername(expectedUC.getUsername());

        Assert.assertNotNull("user credentials cannot be null", actualUC);
        Assert.assertEquals(expectedUC, actualUC);
        Assert.assertEquals(expectedUC.getUsername(), actualUC.getUsername());
        Assert.assertEquals(expectedUC.getPassword(), actualUC.getPassword());
        Assert.assertEquals(expectedUC.getUser(), actualUC.getUser());

        Assert.assertEquals(expectedUC.getRole(), actualUC.getRole());
        Assert.assertEquals(expectedUC.isEnabled(), actualUC.isEnabled());
    }

    @Test(expected = DataAccessException.class)
    public void testCreateUserCredentialsWithNullArg()
    {
        userCredentialsDao.create(null);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateUserCredentialsWithNullUsername()
    {
        UserCredentials expectedUC = getSampleUserCredentials(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateUserCredentialsWithNullPassword()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setPassword(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateUserCredentialsWithNullUser()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setUser(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateUserCredentialsWithNullRoles()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setRole(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test
    public void testEditUserCredentials()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        userCredentialsDao.create(expectedUC);

        expectedUC.setPassword("noPassword");
        expectedUC.setEnabled(false);

        userCredentialsDao.update(expectedUC);

        UserCredentials actualUC = userCredentialsDao.getByUsername(expectedUC.getUsername());

        Assert.assertNotNull("user credentials cannot be null", actualUC);

        Assert.assertEquals(expectedUC, actualUC);
        Assert.assertEquals(expectedUC.getUsername(), actualUC.getUsername());
        Assert.assertEquals(expectedUC.getPassword(), actualUC.getPassword());
        Assert.assertEquals(expectedUC.getUser(), actualUC.getUser());
        Assert.assertEquals(expectedUC.getRole(), actualUC.getRole());

        Assert.assertEquals(expectedUC.isEnabled(), actualUC.isEnabled());
    }

    @Test(expected = DataAccessException.class)
    public void testUpdateUserCredentialsWithNullArg()
    {
        userCredentialsDao.update(null);
    }

    @Test(expected = DataAccessException.class)
    public void testUpdateUserCredentialsWithNullUsername()
    {
        UserCredentials expectedUC = getSampleUserCredentials(null);

        userCredentialsDao.update(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testUpdateUserCredentialsWithNullPassword()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setPassword(null);

        userCredentialsDao.update(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testUpdateUserCredentialsWithNullUser()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setUser(null);

        userCredentialsDao.update(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testUpdateUserCredentialsWithNullRoles()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setRole(null);

        userCredentialsDao.update(expectedUC);
    }

    @Test
    public void testDeleteUserCredentials()
    {

        UserCredentials expectedUC = getSampleUserCredentials("root");

        userCredentialsDao.create(expectedUC);

        userCredentialsDao.delete(expectedUC);

        Assert.assertNull("user credentials should be null", userCredentialsDao.getByUsername(expectedUC.getUsername()));
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteUserCredentialsWithNullArg()
    {
        userCredentialsDao.delete(null);
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteUserCredentialsWithNullUsername()
    {
        UserCredentials expectedUC = getSampleUserCredentials(null);

        userCredentialsDao.delete(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteUserCredentialsWithNullPassword()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setPassword(null);

        userCredentialsDao.delete(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteUserCredentialsWithNullUser()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setUser(null);

        userCredentialsDao.delete(expectedUC);
    }

    @Test(expected = DataAccessException.class)
    public void testDeleteUserCredentialsWithNullRoles()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setRole(null);

        userCredentialsDao.delete(expectedUC);
    }

    private UserCredentials getSampleUserCredentials(String username)
    {
        User user = new User();
        user.setFirstName("name");
        user.setLastName("surname");
        user.setAddress("address");
        user.setBirthNumber("9875698/4587");

        userDao.add(user);

        UserRole role1 = new UserRole();
        role1.setRoleName(UserRole.RoleType.ADMIN.toString());
        UserCredentials userCredentials = new UserCredentials(username, "passwd", true, user, role1);

        return userCredentials;
    }
}
