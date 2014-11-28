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
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tomáš Vašíček
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Long add(UserDto userDto) {
        User userEntity = Converter.getEntity(userDto);
        return userDao.add(userEntity);
    }

    @Transactional
    @Override
    public UserDto get(Long id) {
        User userEntity = null;
        userEntity = userDao.get(id);
        return Converter.getTransferObject(userEntity);
    }

    @Transactional
    @Override
    public void edit(UserDto userDto) {
        User userEntity = Converter.getEntity(userDto);
        userDao.edit(userEntity);
    }

    @Transactional
    @Override
    public void delete(UserDto userDto) {
        User userEntity = Converter.getEntity(userDto);
        userDao.delete(userEntity);
    }

    @Transactional
    @Override
    public List<UserDto> getAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userDao.getAll()) {
            userDtoList.add(Converter.getTransferObject(user));
        }
        return userDtoList;
    }

    @Transactional
    @Override
    public List<UserDto> getAllWithRent() {
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : userDao.getAllWithRent()) {
            userDtoList.add(Converter.getTransferObject(user));
        }
        return userDtoList;
    }

    @Transactional
    @Override
    public List<UserDto> getAllWithoutRent() {
        List<UserDto> userDtoList = new ArrayList<UserDto>();
        for (User user : userDao.getAllWithoutRent()) {
            userDtoList.add(Converter.getTransferObject(user));
        }
        return userDtoList;
    }

}
