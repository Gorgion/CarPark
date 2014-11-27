/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.service.CarService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jiri Dockal
 */
@Controller
@RequestMapping("/auth/car")
public class CarController {
    @Autowired
    private CarService carService;
        
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model)
    {           
        List<CarDto> cars = new ArrayList(carService.getAllCars());
        model.addAttribute("cars", cars);
        return "car-list";
    }
    
    @RequestMapping(value = "/{id}/edit", method = {RequestMethod.GET})
    public String changeCar(@PathVariable Long id, Model model) {
        CarDto car;
        
        car = carService.getCar(id);
        
        CarForm carForm = new CarForm();
        
        carForm.setVIN(car.getVIN());
        carForm.setBrand(car.getBrand());
        carForm.setEngine(car.getEngine());
        carForm.setLicencePlate(car.getLicencePlate());
        carForm.setType(car.getType());
        
        model.addAttribute("carForm",carForm);
        model.addAttribute("brands",Arrays.asList(CarDto.mBrand.values()));
        model.addAttribute("types",Arrays.asList(CarDto.mType.values()));
        model.addAttribute("engines",Arrays.asList(CarDto.mEngine.values()));
        return "car-edit-form";
    }
    
    @RequestMapping(value = "/{id}/edit", method = {RequestMethod.POST,RequestMethod.PUT})
    public String editCar(@PathVariable Long id,@Valid@ModelAttribute CarForm carForm, final BindingResult result,Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
        {
            return "car-form";
        }
        
        CarDto car = carService.getCar(id);
        
        car.setBrand(carForm.getBrand());
        car.setType(carForm.getType());
        car.setEngine(carForm.getEngine());
        car.setLicencePlate(carForm.getLicencePlate());
        car.setVIN(carForm.getVIN());
        
        try
        {
        carService.EditCar(car);
        }
        catch(Exception ex)
        {
            return "car-form";
        }
        
        redirectAttributes.addFlashAttribute("msg", "msg.car.edited");
        
        return "redirect:/auth/car";
    }
    
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String createNewCar(Model model) {
        model.addAttribute("carForm",new CarForm());
        model.addAttribute("brands",Arrays.asList(CarDto.mBrand.values()));
        model.addAttribute("types",Arrays.asList(CarDto.mType.values()));
        model.addAttribute("engines",Arrays.asList(CarDto.mEngine.values()));
        return "car-form";
    }
    
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String addNewCar(@Valid@ModelAttribute CarForm carForm, final BindingResult result,Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
        {
            return "car-form";
        }
        
        CarDto car = new CarDto(carForm.getBrand(),carForm.getType(),carForm.getEngine()
                ,carForm.getLicencePlate(),carForm.getVIN(),false);
        try
        {
        carService.AddCar(car);
        }
        catch(Exception ex)
        {
            return "car-form";
        }
        
        redirectAttributes.addFlashAttribute("msg", "msg.car.created");
        
        return "redirect:/auth/car";
    }

    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String deleteCar(@PathVariable Long id,RedirectAttributes redirectAttributes) 
    {
        CarDto car;
        try
        {
            car = carService.getCar(id);
        
            if(car == null)
            {
               redirectAttributes.addFlashAttribute("errMsg", "error.car.deleted");
               return "redirect:/auth/car";
            }
        
            carService.DeleteCar(car);
        }
        catch(IllegalArgumentException | DataAccessException ex)
        {
           redirectAttributes.addFlashAttribute("errMsg", "error.car.deleted"+ex);
           return "redirect:/auth/car";
        }
        redirectAttributes.addFlashAttribute("msg", "msg.car.deleted");
        return "redirect:/auth/car";    
    }
        
    public static class CarForm
    {
        @NotNull
        private CarDto.mBrand brand;

        @NotNull
        private CarDto.mType type;

        @NotNull
        private CarDto.mEngine engine;

        @NotNull
        @NotBlank
        private String licencePlate;

        @NotBlank
        @NotNull
        private String VIN;

        public CarDto.mBrand getBrand()
        {
            return brand;
        }

        public void setBrand(CarDto.mBrand brand)
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

        public CarDto.mType getType()
        {
            return type;
        }

        public void setType(CarDto.mType type)
        {
            this.type = type;
        }

        public CarDto.mEngine getEngine()
        {
            return engine;
        }

        public void setEngine(CarDto.mEngine engine)
        {
            this.engine = engine;
        }

        public String getVIN()
        {
            return this.VIN;
        }

        public void setVIN(String VIN)
        {
            this.VIN = VIN;
        }

    }
    
    public static class CarRented
    {

        private boolean rented;

        public boolean getRented()
        {
            return rented;
        }

        public void setRented(boolean rented)
        {
            this.rented = rented;
        }
    }
    
}
