/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;
import java.util.Objects;

/**
 * DTO for User entity
 * 
 * @author Tomáš Vašíček
 */
public class UserDto {

    private Long Id;
    private String firstName;
    private String lastName;        
    private String birthNumber;
    private String address;    

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthNumber() {
        return birthNumber;
    }

    public void setBirthNumber(String birthNumber) {
        this.birthNumber = birthNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.Id);
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
        final UserDto other = (UserDto) obj;
        if (!Objects.equals(this.Id, other.Id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserDto{" + "Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthNumber=" + birthNumber + ", address=" + address + '}';
    }
}
