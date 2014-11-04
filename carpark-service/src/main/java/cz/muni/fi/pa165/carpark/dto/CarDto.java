/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

import cz.muni.fi.pa165.carpark.entity.Car.mBrand;
import cz.muni.fi.pa165.carpark.entity.Car.mColor;
import cz.muni.fi.pa165.carpark.entity.Car.mEngine;
import cz.muni.fi.pa165.carpark.entity.Car.mModel;
import cz.muni.fi.pa165.carpark.entity.Car.mType;
import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for car entity
 *
 * @author Jiri Dockal
 */
public class CarDto implements Serializable
{
    private Long id;
    private mBrand brand;
    private mType type;
    private mColor color;
    private mEngine engine;
    private mModel model;
    private String licencePlate;
    private String VIN;
    private boolean rented;
    
    public CarDto(mBrand brand, mType type, mColor color, mEngine engine, 
            mModel model, String licencePlate, String VIN, boolean rented)
    {
        this.brand = brand;
        this.type = type;
        this.color = color;
        this.engine = engine;
        this.model = model;
        this.licencePlate = licencePlate;
        this.VIN = VIN;
        this.rented = rented;
    }
    
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

    public mColor getColor()
    {
        return color;
    }

    public void setColor(mColor color)
    {
        this.color = color;
    }

    public mEngine getEngine()
    {
        return engine;
    }

    public void setEngine(mEngine engine)
    {
        this.engine = engine;
    }

    public mModel getModel()
    {
        return model;
    }

    public void setModel(mModel model)
    {
        this.model = model;
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
        final CarDto other = (CarDto) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Car: id=" + id + ", brand:" + brand + ", model:" + model + ", type:" + type + ", engine:" + engine + ", color:" + color + ", VIN:" + VIN + ", isRented:" + rented + "\n";
    }
}

