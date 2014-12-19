/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.UserDao;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.exception.UserAlreadyExists;
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
    
    @Autowired
    private OfficeService officeService;

    @Transactional
    @Override
    public Long add(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User is null");
        }
        User userEntity = Converter.getEntity(userDto);
        throwExceptionIfBirthNumberExists(userEntity.getBirthNumber());
        return userDao.add(userEntity);
    }

    @Transactional
    @Override
    public UserDto get(Long id) {
        User userEntity = userDao.get(id);
        return Converter.getTransferObject(userEntity);
    }

    private void throwExceptionIfBirthNumberExists(String birthNumber) throws UserAlreadyExists {
        Long id = userDao.getIdByBirthNumber(birthNumber);
        if (id > 0) {
            throw new UserAlreadyExists("User already exists!");
        }
    }

    @Transactional
    @Override
    public void edit(UserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User is null");
        }
        User userEntity = Converter.getEntity(userDto);
        UserDto persistedUser = get(userDto.getId());
        if (userDto.getBirthNumber().equals(persistedUser.getBirthNumber())) {
            userDao.edit(userEntity);
        } else {
            throwExceptionIfBirthNumberExists(userEntity.getBirthNumber());
            userDao.edit(userEntity);
        }
    }

    @Transactional
    @Override
    public void delete(UserDto userDto) {
        User userEntity = Converter.getEntity(userDto);
        
        OfficeDto office = officeService.getOffice(userDto.getOfficeDto().getId());
        if(office.getManager() != null && office.getManager().getId() == userDto.getId())
        {
            office.setManager(null);
            officeService.editOffice(office);
        }
        
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
