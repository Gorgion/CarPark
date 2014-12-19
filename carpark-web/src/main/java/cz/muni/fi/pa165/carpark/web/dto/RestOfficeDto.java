/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.dto;

import com.fasterxml.jackson.annotation.JsonView;
import cz.muni.fi.pa165.carpark.web.rest.JsonViews;
import java.util.List;

/**
 * Redundant web dto due to stop infinite cycle in json in office-user maping.
 *
 * @author Tomas Svoboda
 */
public class RestOfficeDto
{

    @JsonView(value = {JsonViews.Offices.class, JsonViews.Users.class})
    private Long id;
    @JsonView(value = {JsonViews.Offices.class, JsonViews.Users.class})
    private String address;
    @JsonView(value = JsonViews.Offices.class)
    private RestUserDto manager;
    @JsonView(value = JsonViews.Offices.class)
    private List<RestUserDto> employees;

    public RestOfficeDto()
    {
    }

    public RestOfficeDto(Long id, String address)
    {
        this.id = id;
        this.address = address;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public RestUserDto getManager()
    {
        return manager;
    }

    public void setManager(RestUserDto manager)
    {
        this.manager = manager;
    }

    public List<RestUserDto> getEmployees()
    {
        return employees;
    }

    public void setEmployees(List<RestUserDto> employees)
    {
        this.employees = employees;
    }

}
