/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.UserDao;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;

/**
 * Tests for UserServiceImpl class
 *
 * @author Tomas Svoboda
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest
{
    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserDao mockedUserDao;

    @Test
    public void testCreateGet()
    {
        UserDto userDto = createUserDto();
        User user = userDto.createEntity();
        user.setId(1L);

        Mockito.doNothing().when(mockedUserDao).add(user);

        userService.add(userDto);

        userDto.setId(user.getId());

        Mockito.doReturn(user).when(mockedUserDao).get(user.getId());

        UserDto actualDto = userService.get(user.getId());

        Assert.assertNotNull(actualDto);
        Assert.assertEquals(userDto, actualDto);
        Assert.assertEquals(userDto.getId(), actualDto.getId());
        Assert.assertEquals(userDto.getFirstName(), actualDto.getFirstName());
        Assert.assertEquals(userDto.getLastName(), actualDto.getLastName());
        Assert.assertEquals(userDto.getAddress(), actualDto.getAddress());
        Assert.assertEquals(userDto.getBirthNumber(), actualDto.getBirthNumber());
        Assert.assertEquals(userDto.getPosition(), actualDto.getPosition());
    }

    @Test(expected = DataAccessException.class)
    public void testCreateWithNullArg()
    {
        Mockito.doThrow(IllegalArgumentException.class).when(mockedUserDao).add(null);

        userService.add(null);
    }

    @Test
    public void testUpdate()
    {
        UserDto userDto = createUserDto();
        User user = userDto.createEntity();
        user.setId(1L);

        Mockito.doNothing().when(mockedUserDao).edit(user);

        userService.edit(userDto);

        userDto.setId(user.getId());

        Mockito.doReturn(user).when(mockedUserDao).get(user.getId());

        UserDto actualDto = userService.get(user.getId());

        Assert.assertNotNull(actualDto);
        Assert.assertEquals(userDto, actualDto);
        Assert.assertEquals(userDto.getId(), actualDto.getId());
        Assert.assertEquals(userDto.getFirstName(), actualDto.getFirstName());
        Assert.assertEquals(userDto.getLastName(), actualDto.getLastName());
        Assert.assertEquals(userDto.getAddress(), actualDto.getAddress());
        Assert.assertEquals(userDto.getBirthNumber(), actualDto.getBirthNumber());
        Assert.assertEquals(userDto.getPosition(), actualDto.getPosition());
    }

    @Test(expected = DataAccessException.class)
    public void testEditWithNullArg()
    {
        Mockito.doThrow(IllegalArgumentException.class).when(mockedUserDao).edit(null);

        userService.edit(null);
    }

    @Test(expected = DataAccessException.class)
    public void testGetNegativeId()
    {
        Mockito.doThrow(IllegalArgumentException.class).when(mockedUserDao).get(Long.MIN_VALUE);

        userService.get(Long.MIN_VALUE);
    }

    @Test
    public void testGetMaxId()
    {
        Mockito.doReturn(null).when(mockedUserDao).get(Long.MAX_VALUE);

        Assert.assertNull(userService.get(Long.MAX_VALUE));
    }

    @Test
    public void testGetAll()
    {
        UserDto user1 = createUserDto();
        UserDto user2 = createUserDto();
        UserDto user3 = createUserDto();
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);

        Mockito.doReturn(Arrays.asList(user1.createEntity(), user2.createEntity(), user3.createEntity()))
                .when(mockedUserDao).getAll();

        List<UserDto> users = userService.getAll();
        List<UserDto> expectedUsers = Arrays.asList(user1, user2, user3);

        Assert.assertNotSame(expectedUsers, users);

        Assert.assertTrue("Missing expected user.", users.containsAll(expectedUsers));
        users.removeAll(users);
        Assert.assertTrue("Containg foreign user.", users.isEmpty());
    }

    @Test
    public void testGetAllWithRent()
    {
        UserDto user1 = createUserDto();
        UserDto user2 = createUserDto();
        UserDto user3 = createUserDto();
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);

        Mockito.doReturn(Arrays.asList(user1.createEntity(), user2.createEntity()))
                .when(mockedUserDao).getAllWithRent();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);

        List<UserDto> users = userService.getAllWithRent();//daoImpl.getAllWithRent();
        List<UserDto> expectedUsers = Arrays.asList(user1, user2);

        Assert.assertNotSame(expectedUsers, users);

        Assert.assertTrue("Missing expected user.", users.containsAll(expectedUsers));
        users.removeAll(users);
        Assert.assertTrue("Containg foreign user.", users.isEmpty());
    }

    @Test
    public void testGetAllWithoutRent()
    {
        UserDto user1 = createUserDto();
        UserDto user2 = createUserDto();
        UserDto user3 = createUserDto();
        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);

        Mockito.doReturn(Arrays.asList(user2.createEntity(), user3.createEntity()))
                .when(mockedUserDao).getAllWithoutRent();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);

        List<UserDto> actualUsers = userService.getAllWithoutRent();
        List<UserDto> expectedUsers = Arrays.asList(user2, user3);

        Assert.assertNotSame(expectedUsers, actualUsers);

        Assert.assertTrue("Missing expected user.", actualUsers.containsAll(expectedUsers));
        actualUsers.removeAll(actualUsers);
        Assert.assertTrue("Containg foreign user.", actualUsers.isEmpty());
    }

    private static UserDto createUserDto()
    {
        UserDto userDto = new UserDto();

        userDto.setFirstName("Name");
        userDto.setLastName("Surname");
        userDto.setAddress("Address");
        userDto.setBirthNumber("958456/8524");
        userDto.setPosition(User.Position.EMPLOYEE);

        return userDto;
    }
}
