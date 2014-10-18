/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.util;

import cz.muni.fi.pa165.carpark.entity.Rental;

/**
 * Entity DTO converter.
 *
 * @author Tomas Svoboda
 */
public class Converter
{

    private Converter()
    {
    }
    
    public static Rental getEntity(cz.muni.fi.pa165.carpark.dto.Rental rental)
    {
        Rental entity = new Rental();
        
        entity.setId(rental.getId());
        entity.setFromDate(rental.getFromDate());
        entity.setToDate(rental.getToDate());
        entity.setUser(rental.getUser());
        entity.setCar(rental.getCar());
        entity.setRentalState(rental.getRentalState());
        
        return entity;
    }
    
    public static cz.muni.fi.pa165.carpark.dto.Rental getTransferObject(Rental entity)
    {
        cz.muni.fi.pa165.carpark.dto.Rental dto = new cz.muni.fi.pa165.carpark.dto.Rental();
        
        dto.setId(entity.getId());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setUser(entity.getUser());
        dto.setCar(entity.getCar());
        dto.setRentalState(entity.getRentalState());
        
        return dto;
    }
}
