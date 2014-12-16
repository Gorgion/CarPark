/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;

import cz.muni.fi.pa165.carpark.dto.UserRoleDto;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * UserForm dto.
 *
 * @author Tomas Svoboda
 */
public class UserForm
{
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String birthNumber;

    private String address;
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    @NotNull
    private UserRoleDto.RoleType role;

    @NotNull
    private Long idOffice;

    public UserRoleDto.RoleType getRole()
    {
        return role;
    }

    public void setRole(UserRoleDto.RoleType role)
    {
        this.role = role;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Long getIdOffice()
    {
        return idOffice;
    }

    public void setIdOffice(Long idOffice)
    {
        this.idOffice = idOffice;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getBirthNumber()
    {
        return birthNumber;
    }

    public void setBirthNumber(String birthNumber)
    {
        this.birthNumber = birthNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
