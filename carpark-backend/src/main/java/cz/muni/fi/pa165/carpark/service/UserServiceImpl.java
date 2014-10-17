/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.UserDao;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Tomáš
 */
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    } 
    
    @Override
    public void add(UserDto userDto) {
        User userEntity = userDto.createEntity();
        userDao.add(userEntity);
    }

    @Override
    public UserDto get(Long id) {
        User userEntity = userDao.get(id);
        return userEntity.createDto(); 
    }

    @Override
    public void edit(UserDto userDto) {
        User userEntity = userDto.createEntity();
        userDao.edit(userEntity);
    }

    @Override
    public void delete(UserDto userDto) {
        User userEntity = userDto.createEntity();
        userDao.delete(userEntity);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userDao.getAll();
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : users) {
            userDtoList.add(user.createDto());
        }
        return userDtoList;
    }

    @Override
    public List<UserDto> getAllWithRent() {
        List<User> users = userDao.getAllWithRent();
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : users) {
            userDtoList.add(user.createDto());
        }
        return userDtoList;
    }

    @Override
    public List<UserDto> getAllWithoutRent() {
        List<User> users = userDao.getAllWithoutRent();
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : users) {
            userDtoList.add(user.createDto());
        }
        return userDtoList;
    }
    
}
