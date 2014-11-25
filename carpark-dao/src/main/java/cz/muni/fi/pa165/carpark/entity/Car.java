/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;

//import javax.persistence.Entity;
/**
 * Entity of car with variable properties
 *
 * @author Jiri Dockal
 */
@Entity
public class Car implements Serializable
{
    @Id
    @GeneratedValue
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private mBrand brand;
    
    @Enumerated(EnumType.STRING)
    private mType type;

    @Enumerated(EnumType.STRING)
    private mEngine engine;
    
    @Column(nullable = false, unique = true)
    private String licencePlate;
    
    @Column(nullable = false, unique = true)
    private String VIN;
    
    @Column(nullable = false)
    private boolean rented;
    
    public mBrand getBrand()
    {
        return brand;
    }

    public void setBrand(mBrand brand)
    {
        this.brand = brand;
    }
    
    public String getLicencePlate()
    {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate)
    {
        this.licencePlate = licencePlate;
    }

    public mType getType()
    {
        return type;
    }

    public void setType(mType type)
    {
        this.type = type;
    }

    public mEngine getEngine()
    {
        return engine;
    }

    public void setEngine(mEngine engine)
    {
        this.engine = engine;
    }

    public Long getID()
    {
        return id;
    }

    public void setID(Long id)
    {
        this.id = id;
    }

    public String getVIN()
    {
        return this.VIN;
    }

    public void setVIN(String VIN)
    {
        this.VIN = VIN;
    }

    public Boolean getRented()
    {
        return rented;
    }

    public void setRented(Boolean rented)
    {
        this.rented = rented;
    }

    @Override
    public int hashCode()
    {
        int hash = 27;
        hash = 7 * hash + Objects.hashCode(this.id);
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
        final Car other = (Car) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Car: id=" + id + ", brand:" + brand + ", type:" + type + ", engine:" + engine + ", VIN:" + VIN + ", isRented:" + rented + "\n";
    }

    public static enum mEngine
    {

        PETROL,
        DIESEL
    }

    public static enum mType
    {

        COMBI,
        SEDAN,
        HATCHBACK
    }

    public static enum mBrand
    {

        SKODA_OCTAVIA,
        SKODA_FABIA,
        SKODA_SUPERB,
        FORD_FOCUS,
        FORD_MONDEO
    }
}
