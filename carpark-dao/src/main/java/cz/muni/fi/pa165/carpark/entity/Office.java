/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Karolina Burska
 */

@Entity
public class Office implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private String address;
    
    @OneToOne
    private User manager;
    
    @OneToMany(mappedBy = "office", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<User> employees;
    
    @OneToMany(mappedBy = "office", cascade = {CascadeType.REMOVE, CascadeType.DETACH, CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Car> cars;
        
    /**
     * @return the id
     */
    public Long getId() {
	return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @return the address
     */
    public String getAddress() {
	return this.address;
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
	return this.manager;
    }

    /**
     * @param manager the manager to set
     */
    public void setManager(User manager) {
        this.manager = manager;
    }
        
    /**
     * @return the empoyees
     */
    public List<User> getEmployees() {
        return employees;
    }

    /**
     * @param employees the empoyees to set
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
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        final Office office = (Office) obj;
        return Objects.equals(this.id, office.id);
    }

    @Override
    public int hashCode(){
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);        
        return hash;
    }

    @Override
    public String toString(){
        String string = "Addresss of office with id " + id + " is: " + address + ". ";
        string = string.concat("Manager is " + manager + ". ");
        string = string.concat("Office cars are: " + cars + ", office employess are: " + employees);
        return string;
    }
}
