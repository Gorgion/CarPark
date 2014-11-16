/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.service.CarService;
import cz.muni.fi.pa165.carpark.service.RentalService;
import cz.muni.fi.pa165.carpark.service.RentalServiceImpl;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Tomas Svoboda
 */
@Controller
public class GeneralController
{
    @Autowired
    private CarService rs;
    
    @RequestMapping(value = {"/","/index","/home"})
    public String homePage()
    {        
        return "index";
    }
    
    @RequestMapping(value = {"/test"})
    public String homePagetest()
    {
//        CarDto rental = new CarDto(Car.mBrand.SKODA, Car.mType.COMBI, Car.mColor.BLACK, Car.mEngine.PETROL, Car.mModel.FABIA, "spz", "vin", true);
        
//        rs.AddCar(rental);
        rs.getAllCars();
        
        return "index";
    }
}
