/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.exception.CarAlreadyExists;
import cz.muni.fi.pa165.carpark.exception.CarIsRented;
import cz.muni.fi.pa165.carpark.service.CarService;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jiri Dockal
 */
@Controller
@RequestMapping("/auth/car")
//@SessionAttributes("carForm")
public class CarController {
    @Autowired
    private CarService carService;
    
    @Autowired
    private OfficeService officeService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model)
    {           
        List<CarDto> cars = new ArrayList<>(carService.getAllCars());
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
        model.addAttribute("brands",CarDto.mBrand.values());
        model.addAttribute("types",CarDto.mType.values());
        model.addAttribute("engines",CarDto.mEngine.values());
        model.addAttribute("offices",officeService.getAllOffices());
        return "car-edit-form";
    }
    
    @RequestMapping(value = "/{id}/edit", method = {RequestMethod.POST,RequestMethod.PUT})
    public String editCar(@PathVariable Long id,@Valid@ModelAttribute("carForm") CarForm carForm, BindingResult result,Model model,RedirectAttributes redirectAttributes) 
    {
        if (result.hasErrors())
        {
            model.addAttribute("brands",CarDto.mBrand.values());
            model.addAttribute("types",CarDto.mType.values());
            model.addAttribute("engines",CarDto.mEngine.values());
            model.addAttribute("offices",officeService.getAllOffices());
            model.addAttribute("errMsg", "error.car.wrongform");
            return "car-edit-form";
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
        catch(CarAlreadyExists ex)
        {
            model.addAttribute("carForm",new CarForm());
            model.addAttribute("brands",CarDto.mBrand.values());
            model.addAttribute("types",CarDto.mType.values());
            model.addAttribute("engines",CarDto.mEngine.values());
            model.addAttribute("offices",officeService.getAllOffices());
            model.addAttribute("errMsg","error.car.alreadyexists");
            return "car-edit-form";
        }
        
        OfficeDto office = officeService.getOffice(carForm.getIdOffice());
        List<CarDto> cars = new ArrayList<>(office.getCars());
        cars.add(car);
        office.setCars(cars);
        officeService.editOffice(office);
        
        for(OfficeDto o : officeService.getAllOffices())
        {
            if(!o.equals(office))
            {
                if(o.getCars().contains(car))
                {
                    List<CarDto> c = new ArrayList<>(o.getCars());
                    c.remove(car);
                    o.setCars(c);
                    officeService.editOffice(o);
                }
            }
        }
                       
        redirectAttributes.addFlashAttribute("msg", "msg.car.edited");
        
        return "redirect:/auth/car";
    }
    
    @RequestMapping(value = "/add", method = {RequestMethod.GET})
    public String createNewCar(@ModelAttribute("carForm") CarForm carForm, Model model) {
        model.addAttribute("carForm",new CarForm());
        model.addAttribute("brands",CarDto.mBrand.values());
        model.addAttribute("types",CarDto.mType.values());
        model.addAttribute("engines",CarDto.mEngine.values());
        model.addAttribute("offices",officeService.getAllOffices());
        return "car-form";
    }
    
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public String addNewCar(@Valid @ModelAttribute("carForm") CarForm carForm, BindingResult result,Model model,RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
        {     
            model.addAttribute("brands",CarDto.mBrand.values());
            model.addAttribute("types",CarDto.mType.values());
            model.addAttribute("engines",CarDto.mEngine.values());
            model.addAttribute("offices",officeService.getAllOffices());
            model.addAttribute("errorMsg","error.car.wrongform");
            return "car-form";//"redirect:/auth/car/add";
        }
        
        CarDto car = new CarDto(carForm.getBrand(),carForm.getType(),carForm.getEngine()
                ,carForm.getLicencePlate(),carForm.getVIN(),false);
        
        try
        {
        Long carId = carService.AddCar(car);
        car.setID(carId);
        }
        catch(CarAlreadyExists ex)
        {
            model.addAttribute("carForm",new CarForm());
            model.addAttribute("brands",CarDto.mBrand.values());
            model.addAttribute("types",CarDto.mType.values());
            model.addAttribute("engines",CarDto.mEngine.values());
            model.addAttribute("offices",officeService.getAllOffices());
            model.addAttribute("errMsg","error.car.alreadyexists");
            return "car-form";
        }
        OfficeDto office = officeService.getOffice(carForm.getIdOffice());
        
        List<CarDto> cars = new ArrayList<>(office.getCars());
        cars.add(car);
        office.setCars(cars);
        officeService.editOffice(office);
        
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
        catch(CarIsRented ex)
        {
            redirectAttributes.addFlashAttribute("errMsg", "error.car.rentednotdeleted");
           return "redirect:/auth/car";
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
        
        @NotNull
        private Long idOffice;

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

        public Long getIdOffice() {
            return idOffice;
        }

        public void setIdOffice(Long idOffice) {
            this.idOffice = idOffice;
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
