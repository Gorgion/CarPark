/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

import java.util.List;
import java.util.Objects;

/**
 * DTO for office 
 * 
 * @author Karolina Burska
 */
public class OfficeDto {
    
    private Long id;
    private String address;
    private UserDto manager;
    private List<UserDto> employees;

    public OfficeDto(String address, UserDto manager, List<UserDto> employees, List<CarDto> cars) 
    {
        this.address = address;
        this.manager = manager;
        this.employees = employees;
        this.cars = cars;
    }
    private List<CarDto> cars;

    /**
     * @return the iD
     */
    public Long getId() {
        return id;
    }

    /**
     * @param iD the iD to set
     */
    public void setId(Long iD) {
        this.id = iD;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the manager
     */
    public UserDto getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(UserDto manager) {
        this.manager = manager;
    }

    /**
     * @return the employees
     */
    public List<UserDto> getEmployees() {
        return employees;
    }

    /**
     * @param employees the employees to set
     */
    public void setEmployees(List<UserDto> employees) {
        this.employees = employees;
    }

    /**
     * @return the cars
     */
    public List<CarDto> getCars() {
        return cars;
    }

    /**
     * @param cars the cars to set
     */
    public void setCars(List<CarDto> cars) {
        this.cars = cars;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OfficeDto other = (OfficeDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "OfficeDto{" + "iD=" + id + ", address=" + address + ", manager=" + manager + ", employees=" + employees + ", cars=" + cars + '}';
    }
}
