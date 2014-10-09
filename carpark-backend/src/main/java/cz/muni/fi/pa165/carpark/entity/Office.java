/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;
import java.util.Collection;
import java.util.Objects;
/**
 *
 * @author Karolina Burska
 */
public class Office {
        private Long iD;
	private String address;
	private User manager;
        private Collection employees;
        private Collection cars;
        
	public Long getID() {
		return this.iD;
	}

	public void setID(Long iD) {
		this.iD = iD;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getManager() {
		return this.manager;
	}

	public void setManager(User manager) {
            this.manager = manager;
	}
        
        /**
         * @return the empoyees
         */
        public Collection getEmployees() {
            return employees;
        }

        /**
         * @param empoyees the empoyees to set
         */
        public void setEmployees(Collection empoyees) {
            this.employees = employees;
        }

        /**
         * @return the cars
         */
        public Collection getCars() {
            return cars;
        }

        /**
         * @param cars the cars to set
         */
        public void setCars(Collection cars) {
            this.cars = cars;
        }
 
        @Override
        public boolean equals(Object obj)
        {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Office office = (Office) obj;
        return Objects.equals(this.iD, office.iD);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Objects.hashCode(this.iD);
            hash = 79 * hash + Objects.hashCode(this.address);
            return hash;
    }

        @Override
        public String toString()
        {
            return "Addresss of office with id " + iD + " is: " + address + ". Manager is " + manager;
        }

    
}
