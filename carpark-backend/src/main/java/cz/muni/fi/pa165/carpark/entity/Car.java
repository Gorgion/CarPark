/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.entity;

import java.util.Objects;

//import javax.persistence.Entity;
/**
 * Entity of car
 *
 * @author Jiri Dockal
 */
//@Entity
public class Car
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
    private Office office;
    private Rental rent;

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

    public void setID(Long iD)
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

    public Rental getRent()
    {
        return rent;
    }

    public void setRent(Rental rent)
    {
        this.rent = rent;
    }

    public Office getOffice()
    {
        return office;
    }

    public void setOffice(Office office)
    {
        this.office = office;
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

    public static enum mEngine
    {

        PETROL,
        DIESEL,
        ELECTRIC
    }

    public static enum mType
    {

        COMBI,
        SEDAN,
        HATCHBACK,
        CABRIOLET
    }

    public static enum mBrand
    {

        SKODA,
        DAEWOO,
        FORD,
        CHEVROLET,
        TESLA
    }

    public static enum mColor
    {

        BLACK,
        WHITE,
        RED,
        GREEN,
        BLUE
    }

    public static enum mModel
    {

        OCTAVIA,
        FABIA,
        MATIZ,
        FOCUS,
        MONDEO,
        MODEL_S,
        CAMARO
    }
}
