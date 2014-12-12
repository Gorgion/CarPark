/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;

import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.web.dto.OfficeEditForm;
import cz.muni.fi.pa165.carpark.web.dto.OfficeForm;
import cz.muni.fi.pa165.carpark.web.dto.RestOfficeDto;
import cz.muni.fi.pa165.carpark.web.dto.RestUserDto;
import cz.muni.fi.pa165.carpark.web.rest.JsonViews;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for office service.
 *
 * @author Tomas Svoboda
 */
@RestController
@RequestMapping("/rest/offices")
public class RestOfficeController
{

    @Autowired
    private OfficeService officeService;

    @Autowired
    private UserService userService;

    @JsonView(JsonViews.Offices.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<RestOfficeDto>> list() throws JsonProcessingException
    {
        List<RestOfficeDto> offices = new ArrayList<>();

        for (OfficeDto office : officeService.getAllOffices())
        {
            RestOfficeDto restOffice = new RestOfficeDto(office.getId(), office.getAddress());
            List<RestUserDto> restUsers = new ArrayList<>();

            if (office.getManager() != null)
            {
                RestUserDto manager = new RestUserDto();

                manager.setId(office.getManager().getId());
                manager.setAddress(office.getManager().getAddress());
                manager.setBirthNumber(office.getManager().getBirthNumber());
                manager.setFirstName(office.getManager().getFirstName());
                manager.setLastName(office.getManager().getLastName());
                manager.setOffice(restOffice);

                restOffice.setManager(manager);
            }

            for (UserDto emp : office.getEmployees())
            {
                RestUserDto rud = new RestUserDto();

                rud.setId(emp.getId());
                rud.setAddress(emp.getAddress());
                rud.setBirthNumber(emp.getBirthNumber());
                rud.setFirstName(emp.getFirstName());
                rud.setLastName(emp.getLastName());
                rud.setOffice(restOffice);

                restUsers.add(rud);
            }

            restOffice.setEmployees(restUsers);

            offices.add(restOffice);
        }

        return new ResponseEntity<>(offices, HttpStatus.OK);
    }

    @JsonView(JsonViews.Offices.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<RestOfficeDto> getEntity(@PathVariable Long id) throws JsonProcessingException
    {
        OfficeDto office = officeService.getOffice(id);
        RestOfficeDto restOffice = new RestOfficeDto(office.getId(), office.getAddress());
        List<RestUserDto> restUsers = new ArrayList<>();

        if (office.getManager() != null)
        {
            RestUserDto manager = new RestUserDto();

            manager.setId(office.getManager().getId());
            manager.setAddress(office.getManager().getAddress());
            manager.setBirthNumber(office.getManager().getBirthNumber());
            manager.setFirstName(office.getManager().getFirstName());
            manager.setLastName(office.getManager().getLastName());
            manager.setOffice(restOffice);

            restOffice.setManager(manager);
        }

        for (UserDto emp : office.getEmployees())
        {
            RestUserDto rud = new RestUserDto();

            rud.setId(emp.getId());
            rud.setAddress(emp.getAddress());
            rud.setBirthNumber(emp.getBirthNumber());
            rud.setFirstName(emp.getFirstName());
            rud.setLastName(emp.getLastName());
            rud.setOffice(restOffice);

            restUsers.add(rud);
        }

        restOffice.setEmployees(restUsers);

        return new ResponseEntity<>(restOffice, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<OfficeForm> createOffice(@Valid @RequestBody OfficeForm officeForm)
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
}
