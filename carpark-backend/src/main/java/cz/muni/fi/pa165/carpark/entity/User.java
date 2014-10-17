/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.carpark.entity;

import cz.muni.fi.pa165.carpark.dto.UserDto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 * Entity representing a user.
 * 
 * @author Tomáš Vašíček
 */

@Entity
@Table(name = "users")
public class User implements Serializable {
    
    @Id    
    @GeneratedValue
    private Long Id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
        
    @Column(nullable = false, unique = true)
    private String birthNumber;
    
    private String address;    
    
    @Enumerated(EnumType.STRING)
    private Position position;
    

    @Override
    public String toString() {
        return "User{" + "Id=" + Id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthNumber=" + birthNumber + ", address=" + address + ", position=" + position + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.Id);
        return hash;
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
        final User other = (User) obj;
        if (!Objects.equals(this.Id, other.Id))
        {
            return false;
        }
        return true;
    }
    

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
            
    public static enum Position
    {
        EMPLOYEE,
        MANAGER,
        ADMIN
    }      
    
    /**
     * creates DTO object from User entity
     * @return UserDto object
     */
    public UserDto createDto() {
        UserDto userDto = new UserDto();
        
        userDto.setId(Id);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setAddress(address);
        userDto.setBirthNumber(birthNumber);
        userDto.setPosition(position);
        
        return userDto;
    }   
}
