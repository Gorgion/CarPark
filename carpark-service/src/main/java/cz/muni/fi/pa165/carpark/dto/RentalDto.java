/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.Date;
import java.util.Objects;

/**
 * DTO for rental entity.
 *
 * @author Tomas Svoboda
 */
public class RentalDto
{
    private Long id;

    private Date fromDate;

    private Date toDate;

    private cz.muni.fi.pa165.carpark.entity.Rental.State rentalState;

    private Car car;

    private User user;

    public RentalDto()
    {
    }

    public RentalDto(Date fromDate, Date toDate, cz.muni.fi.pa165.carpark.entity.Rental.State rentalState, Car car, User user)
    {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.rentalState = rentalState;
        this.car = car;
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Car getCar()
    {
        return car;
    }

    public void setCar(Car car)
    {
        this.car = car;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }

    public cz.muni.fi.pa165.carpark.entity.Rental.State getRentalState()
    {
        return rentalState;
    }

    public void setRentalState(cz.muni.fi.pa165.carpark.entity.Rental.State rentalState)
    {
        this.rentalState = rentalState;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final RentalDto other = (RentalDto) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "RentalDTO{" + "id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", rentalState=" + rentalState + ", car=" + car + ", user=" + user + '}';
    }

}
