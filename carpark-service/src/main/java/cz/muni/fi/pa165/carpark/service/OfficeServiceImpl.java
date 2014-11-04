/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.OfficeDao;
import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Karolina Burska
 */
@Named
public class OfficeServiceImpl implements OfficeService {

    @Inject
    private OfficeDao officeDao;

    public void setRentalDao(OfficeDao office) {
        officeDao = office;
    }
    
    @Transactional
    @Override
    public void addOffice(OfficeDto office) {
        try {
            Office officeEntity = Converter.getEntity(office);
            officeDao.addOffice(officeEntity);
        } 
        catch (Exception e) {
            throw new DataAccessException("Error occurred while office entity adding.", e){};
        }
    }

    @Transactional
    @Override
    public OfficeDto getOffice(Long id) {
        try {
            Office officeEntity = officeDao.getOffice(id);
            return Converter.getTransferObject(officeEntity);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while getting office.", e) {};
        }
    }

    @Transactional
    @Override
    public void editOffice(OfficeDto office) {
        try {
            Office officeEntity = Converter.getEntity(office);
            officeDao.editOffice(officeEntity);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while editing office.", e) {};
        }
    }

    @Transactional
    @Override
    public void deleteOffice(OfficeDto office) {
        try {
            Office officeEntity = Converter.getEntity(office);
            officeDao.deleteOffice(officeEntity);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while deleting office.", e) {};
        }
    }

    @Transactional
    @Override
    public List<OfficeDto> getAllOffices() {
        try {
            List<OfficeDto> officesDto = new ArrayList<>();
            List<Office> offices = new ArrayList<>(officeDao.getAllOffices());
            for (Office office : offices) {
                officesDto.add(Converter.getTransferObject(office));
            }
            
            return Collections.unmodifiableList(officesDto);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while getting a list of offices.", e) {};
        }
    }

    @Transactional
    @Override
    public List<CarDto> getOfficeCars(OfficeDto office) {
        try {
            List<CarDto> officeCarsDto = new ArrayList<>();
            List<Car> cars = new ArrayList<>(officeDao.getOfficeCars(officeDao.getOffice(office.getID())));
            for (Car car : cars) {
                officeCarsDto.add(Converter.getTransferObject(car));
            }
            
            return Collections.unmodifiableList(officeCarsDto);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while getting a list of office cars.", e) {};
        }
    }

    @Transactional
    @Override
    public void addCarToOffice(OfficeDto office, CarDto car) {
        try {
            Office officeEntity = Converter.getEntity(office);
            Car carEntity = Converter.getEntity(car);
            officeDao.addCarToOffice(officeEntity, carEntity);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while adding car into office.", e) {};
        }  
    }

    @Transactional
    @Override
    public void deleteCarFromOffice(OfficeDto office, CarDto car) {
        try {
            Office officeEntity = Converter.getEntity(office);
            Car carEntity = Converter.getEntity(car);
            officeDao.deleteCarFromOffice(officeEntity, carEntity);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while adding car into office.", e) {};
        }  
    }

    @Transactional
    @Override
    public List<UserDto> getEmployees(OfficeDto office) {
        try {
            List<UserDto> officeEmployeesDto = new ArrayList<>();
            List<User> employees = new ArrayList<>(officeDao.getEmployees(officeDao.getOffice(office.getID())));
            for (User employee : employees) {
                officeEmployeesDto.add(Converter.getTransferObject(employee));
            }
            
            return Collections.unmodifiableList(officeEmployeesDto);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while getting a list of office employees.", e) {};
        }
    }

    @Transactional
    @Override
    public void addEmployeeToOffice(OfficeDto office, UserDto user) {
        try {
            Office officeEntity = Converter.getEntity(office);
            User userEntity = Converter.getEntity(user);
            officeDao.addEmployeeToOffice(officeEntity, userEntity);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while adding car into office.", e) {};
        }  
    }

    @Transactional
    @Override
    public void deleteEmployeeFromOffice(OfficeDto office, UserDto user) {
        try {
            Office officeEntity = Converter.getEntity(office);
            User userEntity = Converter.getEntity(user);
            officeDao.deleteEmployeeFromOffice(officeEntity, userEntity);
        }
        catch(Exception e) {
            throw new DataAccessException("Error occurred while adding car into office.", e) {};
        } 
    }
    
}
