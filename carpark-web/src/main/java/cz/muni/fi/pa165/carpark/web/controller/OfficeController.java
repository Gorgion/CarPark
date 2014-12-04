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
import java.util.ArrayList;
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

    public OfficeForm getNewForm() {
        return new OfficeForm();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<OfficeDto> offices = new ArrayList<>(officeService.getAllOffices());
        model.addAttribute("offices", offices);

        return "office-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String handleRequest(Model model) {
        model.addAttribute("officeForm", new OfficeForm());
        return "office-form";
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.PUT})
    public String processSubmit(@Valid @ModelAttribute("officeForm") OfficeForm officeForm, final BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("error", "error.office.created");
            return "office-form";
        }

        for (OfficeDto o : officeService.getAllOffices()) {
            if (o.getAddress().equals(officeForm.getAddress())) {
                attributes.addFlashAttribute("error", "error.office.created");
                return "office-form";
            }
        }

        String address = officeForm.getAddress();
        UserDto manager = null;
        List<UserDto> employees = Collections.EMPTY_LIST;
        List<CarDto> cars = Collections.EMPTY_LIST;

        OfficeDto office = new OfficeDto(address, manager, employees, cars);
        officeService.addOffice(office);
        attributes.addFlashAttribute("msg", "msg.office.created");

        return "redirect:/auth/office";
    }

    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String officeDeletion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        OfficeDto office = officeService.getOffice(id);

        if (office == null) {
            redirectAttributes.addFlashAttribute("error", "error.office.deleted");
        }

        officeService.deleteOffice(office);

        redirectAttributes.addFlashAttribute("msg", "msg.office.deleted");

        return "redirect:/auth/office";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editOffice(@PathVariable Long id, Model model) {
        OfficeDto office = officeService.getOffice(id);
        OfficeEditForm officeEditForm = new OfficeEditForm();

        if (office.getEmployees().isEmpty()) {
            model.addAttribute("error", "error.office.noemployees");
        }
        officeEditForm.setAddress(office.getAddress());

        if (office.getManager() != null) {
            officeEditForm.setManagerId(office.getManager().getId());
        }

        model.addAttribute("officeEditForm", officeEditForm);
        model.addAttribute("managerId", officeService.getEmployees(office));

        return "office-edit-form";
    }

    @RequestMapping(value = "/{id}/edit", method = {RequestMethod.POST, RequestMethod.PUT})
    public String officeEdition(@PathVariable Long id, @Valid @ModelAttribute OfficeEditForm officeEditForm, final BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        OfficeDto office = officeService.getOffice(id);

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "error.office.edit");
            model.addAttribute("officeEditForm", officeEditForm);
            model.addAttribute("managerId", officeService.getEmployees(office));
            return "office-edit-form";
        }
        if (officeEditForm.getManagerId() == null) {
            redirectAttributes.addFlashAttribute("error", "error.office.edit");
            model.addAttribute("officeEditForm", officeEditForm);
            model.addAttribute("managerId", officeService.getEmployees(office));
            return "office-edit-form";
        }

        System.out.println("no-error?");
        System.out.println("1." + officeEditForm.getAddress());
        System.out.println("2." + officeEditForm.getManagerId());

        office.setAddress(officeEditForm.getAddress());

        office.setManager(userService.get(officeEditForm.getManagerId()));

        System.out.println("mmm " + office.getManager());
        System.out.println("mmm " + office.getAddress());
        System.out.println("mmm " + office.getEmployees());
        System.out.println("mmm " + office.getCars());

        officeService.editOffice(office);

        redirectAttributes.addFlashAttribute("msg", "msg.office.edit");
        return "redirect:/auth/office";
    }

    public static class OfficeForm {

        @NotNull
        @NotBlank
        private String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class OfficeEditForm {

        @NotNull
        @NotBlank
        @Valid
        private String address;
        @NotNull
        private Long managerId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Long getManagerId() {
            return managerId;
        }

        public void setManagerId(Long managerId) {
            this.managerId = managerId;
        }
    }
}
