/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;
import com.fasterxml.jackson.annotation.JsonView;
import cz.muni.fi.pa165.carpark.web.rest.JsonViews;
import java.util.Objects;

/**
 * Redundant web dto due to stop infinite cycle in json in office-user maping. 
 * 
 * @author Tomáš Svoboda
 */
public class RestUserDto {

    @JsonView(value = {JsonViews.Offices.class, JsonViews.Users.class})
    private Long id;
    @JsonView(value = {JsonViews.Offices.class, JsonViews.Users.class})
    private String firstName;
    @JsonView(value = {JsonViews.Offices.class, JsonViews.Users.class})
    private String lastName;        
    @JsonView(value = {JsonViews.Offices.class, JsonViews.Users.class})
    private String birthNumber;
    @JsonView(value = {JsonViews.Offices.class, JsonViews.Users.class})
    private String address;
    @JsonView(value = JsonViews.Users.class)
    private RestOfficeDto office;

    public RestUserDto()
    {
    }

    public RestUserDto(Long id, String firstName, String lastName, String birthNumber, String address)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthNumber = birthNumber;
        this.address = address;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
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
    
    public RestOfficeDto getOffice() {
        return office;
    }

    public void setOffice(RestOfficeDto office) {
        this.office = office;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final RestUserDto other = (RestUserDto) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }
}
