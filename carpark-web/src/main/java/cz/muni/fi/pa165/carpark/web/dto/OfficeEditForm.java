/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Office edit form dto
 *
 * @author Tomas Svoboda
 */
public class OfficeEditForm
{

    @NotBlank
    private String address;
    
    @NotBlank
    private Long managerId;

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public Long getManagerId()
    {
        return managerId;
    }

    public void setManagerId(Long managerId)
    {
        this.managerId = managerId;
    }
}
