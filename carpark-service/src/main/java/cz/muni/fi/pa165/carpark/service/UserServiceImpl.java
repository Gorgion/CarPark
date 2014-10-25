/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.UserDao;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Tomáš Vašíček
 */
@Named
public class UserServiceImpl implements UserService {
    
    @Inject
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    } 
    
    @Transactional
    @Override
    public void add(UserDto userDto) {
        User userEntity = userDto.createEntity();
        userDao.add(userEntity);
    }
    
    @Transactional
    @Override
    public UserDto get(Long id) {
        User userEntity = userDao.get(id);
        return Converter.getTransferObject(userEntity);
    }
    
    @Transactional
    @Override
    public void edit(UserDto userDto) {
        User userEntity = userDto.createEntity();
        userDao.edit(userEntity);
    }
    
    @Transactional
    @Override
    public void delete(UserDto userDto) {
        User userEntity = userDto.createEntity();
        userDao.delete(userEntity);
    }
    
    @Transactional
    @Override
    public List<UserDto> getAll() {
        List<User> users = userDao.getAll();
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : users) {
            userDtoList.add(Converter.getTransferObject(user));
        }
        return userDtoList;
    }

    @Transactional
    @Override
    public List<UserDto> getAllWithRent() {
        List<User> users = userDao.getAllWithRent();
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : users) {
            userDtoList.add(Converter.getTransferObject(user));
        }
        return userDtoList;
    }
    
    @Transactional
    @Override
    public List<UserDto> getAllWithoutRent() {
        List<User> users = userDao.getAllWithoutRent();
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : users) {
            userDtoList.add(Converter.getTransferObject(user));
        }
        return userDtoList;
    }
    
}
