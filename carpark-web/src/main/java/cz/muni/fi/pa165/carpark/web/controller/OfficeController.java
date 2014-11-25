/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;

import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.validation.AfterDate;
import java.util.ArrayList;
import java.util.Collection;

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
        //model.addAttribute("employees", userService.getAll());
        model.addAttribute("officeForm", new OfficeForm());
        return "office-list";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String handleRequest(Model model)
    {
        //System.out.println("On Submit");
        model.addAttribute("officeForm", new OfficeForm());
        model.addAttribute("employees", userService.getAll());
        return "office-form";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processSubmit(@Valid @ModelAttribute("officeForm") OfficeForm officeForm, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors())
        {
            return "office-form";
        }
        attributes.addFlashAttribute("msg","msg.office.succesful"); 
        model.addAttribute("employees", userService.getAll());
        model.addAttribute("officeForm", new OfficeForm());
        return "redirect:/auth/office";
    }
    
    public static class OfficeForm
    {
        @NotBlank
        private String address;
        //@NotNull
        private UserDto manager;
        //private List<UserDto> employees;
        
        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public UserDto getManager() {
            return manager;
        }

        public void setLastName(UserDto manager) {
            this.manager = manager;
        }
/*
        public List<UserDto> getEmployees() {
            return employees;
        }

        public void setEmployees(List<UserDto> employees) {
            this.employees = employees;
        }*/
    }
}
