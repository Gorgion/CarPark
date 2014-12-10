/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * UserForm dto.
 *
 * @author Tomas Svoboda
 */
public class UserForm
{

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String birthNumber;

    private String address;

    @NotNull
    private Long idOffice;

    public Long getIdOffice()
    {
        return idOffice;
    }

    public void setIdOffice(Long idOffice)
    {
        this.idOffice = idOffice;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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
