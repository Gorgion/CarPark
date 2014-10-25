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
import org.springframework.dao.DataAccessException;

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
        try {
            User userEntity = userDto.createEntity();
            userDao.add(userEntity);
        } catch (Exception e) {
            throw new DataAccessException("Some error occured during creating user entity.", e) {};
        }
    }

    @Transactional
    @Override
    public UserDto get(Long id) {
        User userEntity = null;

        try {
            userEntity = userDao.get(id);
        } catch (Exception e) {
            throw new DataAccessException("Some error occured during retrieving user entity.", e) {};
        }
        return Converter.getTransferObject(userEntity);
    }

    @Transactional
    @Override
    public void edit(UserDto userDto) {
        try {
            User userEntity = userDto.createEntity();
            userDao.edit(userEntity);
        } catch (Exception e) {
            throw new DataAccessException("Some error occured during editing user entity.", e) {};
        }
    }

    @Transactional
    @Override
    public void delete(UserDto userDto) {
        try {
            User userEntity = userDto.createEntity();
            userDao.delete(userEntity);
        } catch (Exception e) {
            throw new DataAccessException("Some error occured during removing rental entity.", e) {};
        }
    }

    @Transactional
    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        try {
            for (User user : userDao.getAll()) {
                userDtoList.add(Converter.getTransferObject(user));
            }
        } catch (Exception e) {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e) {};
        }

        return userDtoList;
    }

    @Transactional
    @Override
    public List<UserDto> getAllWithRent() {
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        try {
            for (User user : userDao.getAllWithRent()) {
                userDtoList.add(Converter.getTransferObject(user));
            }
        } catch (Exception e) {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e) {};
        }

        return userDtoList;
    }

    @Transactional
    @Override
    public List<UserDto> getAllWithoutRent() {
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        try {
            for (User user : userDao.getAllWithoutRent()) {
                userDtoList.add(Converter.getTransferObject(user));
            }
        } catch (Exception e) {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e) {};
        }
        return userDtoList;
    }

}
