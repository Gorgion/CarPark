/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dto;

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
    private mEngine engine;
    private String licencePlate;
    private String VIN;
    private boolean rented;
    private OfficeDto officeDto;
    
    public CarDto(){}
    
    public CarDto(mBrand brand, mType type, mEngine engine, String licencePlate, String VIN, boolean rented, OfficeDto officeDto)
    {
        this.brand = brand;
        this.type = type;
        this.engine = engine;
        this.licencePlate = licencePlate;
        this.VIN = VIN;
        this.rented = rented;
        this.officeDto = officeDto;
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

    public mEngine getEngine()
    {
        return engine;
    }

    public void setEngine(mEngine engine)
    {
        this.engine = engine;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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

    public void setRented(Boolean rented)
    {
        this.rented = rented;
    }
    
        /**
     * @return the rented
     */
    public boolean isRented() {
        return rented;
    }

    /**
     * @return the officeDto
     */
    public OfficeDto getOfficeDto() {
        return officeDto;
    }

    /**
     * @param officeDto the officeDto to set
     */
    public void setOfficeDto(OfficeDto officeDto) {
        this.officeDto = officeDto;
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
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString()
    {
        return "Car: id=" + id + ", brand:" + brand + ", type:" + type + ", engine:" + engine + ", VIN:" + VIN + ", isRented:" + isRented() + "\n";
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

