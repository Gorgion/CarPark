/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Office form dto
 *
 * @author Tomas Svoboda
 */
public class OfficeForm
{

    @NotBlank
    private String address;

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
