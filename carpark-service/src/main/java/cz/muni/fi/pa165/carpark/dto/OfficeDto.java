/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.List;
import java.util.Objects;

/**
 * DTO for office 
 * 
 * @author Karolina Burska
 */
public class OfficeDto {
    
    private Long iD;
    private String address;
    private User manager;
    private List<User> employees;

    public OfficeDto(String address, User manager, List<User> employees, List<Car> cars) 
    {
        this.address = address;
        this.manager = manager;
        this.employees = employees;
        this.cars = cars;
    }
    private List<Car> cars;

    /**
     * @return the iD
     */
    public Long getID() {
        return iD;
    }

    /**
     * @param iD the iD to set
     */
    public void setID(Long iD) {
        this.iD = iD;
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
    public User getManager() {
        return manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(User manager) {
        this.manager = manager;
    }

    /**
     * @return the employees
     */
    public List<User> getEmployees() {
        return employees;
    }

    /**
     * @param employees the employees to set
     */
    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    /**
     * @return the cars
     */
    public List<Car> getCars() {
        return cars;
    }

    /**
     * @param cars the cars to set
     */
    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.iD);
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
        if (!Objects.equals(this.iD, other.iD)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OfficeDto{" + "iD=" + iD + ", address=" + address + ", manager=" + manager + ", employees=" + employees + ", cars=" + cars + '}';
    }
}
