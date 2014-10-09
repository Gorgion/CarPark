/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;
import cz.muni.fi.pa165.carpark.entity.User;
//import cz.muni.fi.pa165.carpark.dao.OfficeDao;
//import java.util.Collections;
import java.util.Objects;
/**
 *
 * @author Lorain
 */
public class Office {
        private Long iD;
	private String address;
	private User manager;

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

	public void setManager(Object user) {
		throw new UnsupportedOperationException();
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
