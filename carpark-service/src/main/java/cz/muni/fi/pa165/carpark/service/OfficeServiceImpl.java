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
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 * @author Karolina Burska
 */
@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeDao officeDao;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void addOffice(OfficeDto office) {
            Office officeEntity = Converter.getEntity(office);
            officeDao.addOffice(officeEntity);
            office.setId(officeEntity.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public OfficeDto getOffice(Long id) {
            OfficeDto office = Converter.getTransferObject(officeDao.getOffice(id));
            return office;
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public void editOffice(OfficeDto office) {
            Office officeEntity = Converter.getEntity(office);
            officeDao.editOffice(officeEntity);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void deleteOffice(OfficeDto office) {
            Office officeEntity = Converter.getEntity(office);
            officeDao.deleteOffice(officeEntity);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public List<OfficeDto> getAllOffices() {
            List<OfficeDto> officesDto = new ArrayList<>();
            List<Office> offices = new ArrayList<>(officeDao.getAllOffices());
            for (Office office : offices) {
                officesDto.add(Converter.getTransferObject(office));
            }           
            return Collections.unmodifiableList(officesDto);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public List<CarDto> getOfficeCars(OfficeDto office) {
            List<CarDto> officeCarsDto = new ArrayList<>();
            List<Car> cars = new ArrayList<>(officeDao.getOfficeCars(officeDao.getOffice(office.getId())));
            for (Car car : cars) {
                officeCarsDto.add(Converter.getTransferObject(car));
            }          
            return Collections.unmodifiableList(officeCarsDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void addCarToOffice(OfficeDto office, CarDto car) {
            Office officeEntity = Converter.getEntity(office);
            Car carEntity = Converter.getEntity(car);
            officeDao.addCarToOffice(officeEntity, carEntity); 
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void deleteCarFromOffice(OfficeDto office, CarDto car) {
            Office officeEntity = Converter.getEntity(office);
            Car carEntity = Converter.getEntity(car);
            officeDao.deleteCarFromOffice(officeEntity, carEntity); 
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public List<UserDto> getEmployees(OfficeDto office) {
            List<UserDto> officeEmployeesDto = new ArrayList<>();
            List<User> employees = new ArrayList<>(officeDao.getEmployees(officeDao.getOffice(office.getId())));
            for (User employee : employees) {
                officeEmployeesDto.add(Converter.getTransferObject(employee));
            }       
            return Collections.unmodifiableList(officeEmployeesDto);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void addEmployeeToOffice(OfficeDto office, UserDto user) {
            Office officeEntity = Converter.getEntity(office);
            User userEntity = Converter.getEntity(user);
            officeDao.addEmployeeToOffice(officeEntity, userEntity); 
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void deleteEmployeeFromOffice(OfficeDto office, UserDto user) {
            Office officeEntity = Converter.getEntity(office);
            User userEntity = Converter.getEntity(user);
            officeDao.deleteEmployeeFromOffice(officeEntity, userEntity);
    }
    
}
