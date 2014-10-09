/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity representing a rental of a car for the user at certain period of time.
 *
 * @author Tomas Svoboda
 */
@Entity
public class Rental implements Serializable
{
    @Id    
    @GeneratedValue
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fromDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date toDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State rentalState;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Car car;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

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

    public State getRentalState()
    {
        return rentalState;
    }

    public void setRentalState(State rentalState)
    {
        this.rentalState = rentalState;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Rental other = (Rental) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Rental{" + "id=" + id + ", fromDate=" + fromDate + ", toDate=" + toDate + ", rentalState=" + rentalState + ", car=" + car + ", user=" + user + '}';
    }        
    
    public static enum State
    {
        NEW,
        APPROVED,
        PENDING,
        FINISHED
    }
}
