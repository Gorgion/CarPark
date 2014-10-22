/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.config.TestConfig;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for user credentials dao.
 *
 * @author Tomas Svoboda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
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

        List<UserRole> expectedRoles = new ArrayList<>(expectedUC.getRoles());

        Assert.assertNotNull(actualUC.getRoles());
        Assert.assertTrue("Expected roles are not present.", expectedRoles.containsAll(actualUC.getRoles()));

        for (UserRole role : actualUC.getRoles())
        {
            expectedRoles.remove(role);
        }

        Assert.assertTrue("Unexpected roles are present.", expectedRoles.isEmpty());

        Assert.assertEquals(expectedUC.isAccountNonExpired(), actualUC.isAccountNonExpired());
        Assert.assertEquals(expectedUC.isAccountNonLocked(), actualUC.isAccountNonLocked());
        Assert.assertEquals(expectedUC.isCredentialsNonExpired(), actualUC.isCredentialsNonExpired());
        Assert.assertEquals(expectedUC.isEnabled(), actualUC.isEnabled());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserCredentialsWithNullArg()
    {
        userCredentialsDao.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserCredentialsWithNullUsername()
    {
        UserCredentials expectedUC = getSampleUserCredentials(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserCredentialsWithNullPassword()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setPassword(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserCredentialsWithNullUser()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setUser(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateUserCredentialsWithNullRoles()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setRoles(null);

        userCredentialsDao.create(expectedUC);
    }

    @Test
    public void testEditUserCredentials()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        userCredentialsDao.create(expectedUC);

        expectedUC.setPassword("noPassword");
        expectedUC.setEnabled(false);
        expectedUC.setAccountNonLocked(false);

        userCredentialsDao.update(expectedUC);

        UserCredentials actualUC = userCredentialsDao.getByUsername(expectedUC.getUsername());

        Assert.assertNotNull("user credentials cannot be null", actualUC);

        Assert.assertEquals(expectedUC, actualUC);
        Assert.assertEquals(expectedUC.getUsername(), actualUC.getUsername());
        Assert.assertEquals(expectedUC.getPassword(), actualUC.getPassword());
        Assert.assertEquals(expectedUC.getUser(), actualUC.getUser());
        
        List<UserRole> expectedRoles = new ArrayList<>(expectedUC.getRoles());

        Assert.assertNotNull(actualUC.getRoles());
        Assert.assertTrue("Expected roles are not present.", expectedRoles.containsAll(actualUC.getRoles()));

        for (UserRole role : actualUC.getRoles())
        {
            expectedRoles.remove(role);
        }

        Assert.assertTrue("Unexpected roles are present.", expectedRoles.isEmpty());
        
        Assert.assertEquals(expectedUC.isAccountNonExpired(), actualUC.isAccountNonExpired());
        Assert.assertEquals(expectedUC.isAccountNonLocked(), actualUC.isAccountNonLocked());
        Assert.assertEquals(expectedUC.isCredentialsNonExpired(), actualUC.isCredentialsNonExpired());
        Assert.assertEquals(expectedUC.isEnabled(), actualUC.isEnabled());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserCredentialsWithNullArg()
    {
        userCredentialsDao.update(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserCredentialsWithNullUsername()
    {
        UserCredentials expectedUC = getSampleUserCredentials(null);

        userCredentialsDao.update(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserCredentialsWithNullPassword()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setPassword(null);

        userCredentialsDao.update(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserCredentialsWithNullUser()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setUser(null);

        userCredentialsDao.update(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateUserCredentialsWithNullRoles()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setRoles(null);

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

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserCredentialsWithNullArg()
    {
        userCredentialsDao.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserCredentialsWithNullUsername()
    {
        UserCredentials expectedUC = getSampleUserCredentials(null);

        userCredentialsDao.delete(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserCredentialsWithNullPassword()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setPassword(null);

        userCredentialsDao.delete(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserCredentialsWithNullUser()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setUser(null);

        userCredentialsDao.delete(expectedUC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteUserCredentialsWithNullRoles()
    {
        UserCredentials expectedUC = getSampleUserCredentials("root");

        expectedUC.setRoles(null);

        userCredentialsDao.delete(expectedUC);
    }

    private UserCredentials getSampleUserCredentials(String username)
    {
        User user = new User();
        user.setFirstName("name");
        user.setLastName("surname");
        user.setAddress("address");
        user.setBirthNumber("9875698/4587");
        user.setPosition(User.Position.EMPLOYEE);

        userDao.add(user);

        Set<UserRole> roles = new HashSet<>();

        UserCredentials userCredentials = new UserCredentials(username, "passwd", true, user, roles);

        UserRole role1 = new UserRole();
        role1.setRoleName(UserRole.RoleType.ADMIN.toString());
        role1.setUserCredentials(userCredentials);

        UserRole role2 = new UserRole();
        role2.setRoleName(UserRole.RoleType.USER.toString());
        role2.setUserCredentials(userCredentials);

        roles.add(role1);
        roles.add(role2);

        return userCredentials;
    }
}
