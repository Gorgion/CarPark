/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller.rest;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;

import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserService;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tomas Svoboda
 */
@RestController
@RequestMapping("/auth/rest/offices")
public class RestOfficeController
{

    @Autowired
    private OfficeService officeService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<OfficeDto>> list()
    {
        List<OfficeDto> offices = new ArrayList<>(officeService.getAllOffices());

        return new ResponseEntity<>(offices, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<OfficeForm> processSubmit(@Valid @RequestBody OfficeForm officeForm)
    {
        for (OfficeDto o : officeService.getAllOffices())
        {
            if (o.getAddress().equals(officeForm.getAddress()))
            {
                return new ResponseEntity<>(officeForm, HttpStatus.CONFLICT);
            }
        }

        String address = officeForm.getAddress();
        UserDto manager = null;
        List<UserDto> employees = Collections.EMPTY_LIST;
        List<CarDto> cars = Collections.EMPTY_LIST;

        OfficeDto office = new OfficeDto(address, manager, employees, cars);
        officeService.addOffice(office);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> officeDeletion(@PathVariable Long id)
    {
        OfficeDto office = officeService.getOffice(id);

        if (office == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        officeService.deleteOffice(office);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<OfficeEditForm> officeEdition(@PathVariable Long id, @Valid @RequestBody OfficeEditForm officeEditForm)
    {
        OfficeDto office = officeService.getOffice(id);

        if (officeEditForm.getManagerId() == null)
        {
            return new ResponseEntity<>(officeEditForm, HttpStatus.BAD_REQUEST);
        }

        office.setAddress(officeEditForm.getAddress());

        office.setManager(userService.get(officeEditForm.getManagerId()));

        officeService.editOffice(office);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static class OfficeForm
    {

        @NotBlank
        private String address;

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }
    }

    public static class OfficeEditForm
    {

        @NotBlank
        private String address;
        @NotNull
        private Long managerId;

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

        public Long getManagerId()
        {
            return managerId;
        }

        public void setManagerId(Long managerId)
        {
            this.managerId = managerId;
        }
    }
}
