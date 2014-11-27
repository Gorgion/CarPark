/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.CarService;

import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.validation.AfterDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Karolina Burska
 */
@Controller
@RequestMapping("/auth/office")
public class OfficeController {
    @Autowired
    private OfficeService officeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CarService carService;

    //@ModelAttribute("office-form")
    //@RequestMapping(value = "/add", method = RequestMethod.GET)
    public OfficeForm getNewForm() {
        return new OfficeForm();
    }
    
    //@ModelAttribute("office-form")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model)
    { 
        List<OfficeDto> offices = new ArrayList<>(officeService.getAllOffices());
        model.addAttribute("offices", offices);
        //model.addAttribute("hasEmployee", offices);
        /*List<UserDto> users = new ArrayList<>(userService.getAll());
        model.addAttribute("users", users);*/
        //model.addAttribute("employees", userService.getAll());
        //model.addAttribute("officeForm", new OfficeForm());
        return "office-list";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String handleRequest(Model model)
    {
        model.addAttribute("officeForm", new OfficeForm());
        //model.addAttribute("employees", userService.getAll());
        return "office-form";
    }
    
    @RequestMapping(value = "/add", method = {RequestMethod.POST,RequestMethod.PUT})
    public String processSubmit(@ModelAttribute("officeForm") OfficeForm officeForm,final BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors())
        {
            attributes.addFlashAttribute("msg","msg.office.unsuccesful");
            return "office-form";
        }
        UserDto manager = null;
        List<UserDto> employees = Collections.EMPTY_LIST;
        List<CarDto> cars = Collections.EMPTY_LIST;
        OfficeDto office = new OfficeDto(officeForm.getAddress(), manager, employees, cars);
        officeService.addOffice(office);
        attributes.addFlashAttribute("msg","msg.office.succesful");

        //model.addAttribute("employees", userService.getAll());
        //model.addAttribute("officeForm", new OfficeForm());
        return "redirect:/auth/office";
    }
    
    @RequestMapping(value = "/{id}/delete", method ={RequestMethod.POST, RequestMethod.DELETE})
    public String officeDeletion(@PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        OfficeDto office = officeService.getOffice(id);

        if (office == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.office.deleted");
        }

        officeService.deleteOffice(office);

        redirectAttributes.addFlashAttribute("msg", "msg.office.deleted");

        return "redirect:/auth/office";
    }
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editOffice(@PathVariable Long id, Model model)
    {
        OfficeDto office = officeService.getOffice(id);

        OfficeEditForm officeEditForm = new OfficeEditForm();
        
        officeEditForm.setAddress(office.getAddress());
        officeEditForm.setManager(office.getManager());
        officeEditForm.setEmployees(office.getEmployees());
        officeEditForm.setCars(office.getCars());
        
        model.addAttribute("officeEditForm", officeEditForm);
        model.addAttribute("managers", userService.getAll());
        model.addAttribute("employees", userService.getAll());
        model.addAttribute("cars", carService.getAllCars());
        
        return "office-edit-form";
    }
    
    @RequestMapping(value = "/{id}/edit", method ={RequestMethod.POST, RequestMethod.PUT})
    public String officeEdition(@PathVariable Long id, @Valid @ModelAttribute OfficeEditForm officeEditForm,final BindingResult result,Model model, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors())
        {            
            System.out.println("\n add:"+officeEditForm.getAddress());
            System.out.println("\n man:"+officeEditForm.getManager());
            System.out.println("\n empl:"+officeEditForm.getEmployees());
            System.out.println("\n cars:"+officeEditForm.getCars());
            redirectAttributes.addFlashAttribute("msg","msg.office.edit.unsuccesful");
            return "office-edit-form";
        }

        OfficeDto office = officeService.getOffice(id);
        office.setAddress(officeEditForm.getAddress());
        office.setManager(officeEditForm.getManager());
        office.setEmployees(officeEditForm.getEmployees());
        office.setCars(officeEditForm.getCars());

        try
        {
        officeService.editOffice(office);
        }
        catch(Exception e)
        {
            redirectAttributes.addFlashAttribute("msg","msg.office.edit.unsuccesful");
            return "office-edit-form";
        }
        
        redirectAttributes.addFlashAttribute("msg", "msg.office.edited");
        return "redirect:/auth/office";
    }
    
    public static class OfficeForm
    {
        @NotBlank
        private String address;
        
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
    
    public static class OfficeEditForm
    {
        @NotBlank
        private String address;
        @NotNull
        private UserDto manager;
        
        private List<UserDto> employees;
        
        private List<CarDto> cars;
        
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public UserDto getManager() {
            return manager;
        }

        public void setManager(UserDto manager) {
            this.manager = manager;
        }

        public List<UserDto> getEmployees() {
            return employees;
        }

        public void setEmployees(List<UserDto> employees) {
            this.employees = employees;
        }
        
        public List<CarDto> getCars() {
            return cars;
        }

        public void setCars(List<CarDto> cars) {
            this.cars = cars;
        }
    }
}
