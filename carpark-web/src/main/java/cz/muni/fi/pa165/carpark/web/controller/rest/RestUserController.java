package cz.muni.fi.pa165.carpark.web.controller.rest;

import cz.muni.fi.pa165.carpark.web.controller.*;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.RentalService;
import cz.muni.fi.pa165.carpark.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User rest controller.
 *
 * @author Tomas Svoboda
 */
@RestController
@RequestMapping("/auth/rest/users")
public class RestUserController
{

    @Autowired
    private UserService userService;

    @Autowired
    private OfficeService officeService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<UserDto>> list()
    {
        List<UserDto> users = userService.getAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<UserForm> addedUser(@Valid @RequestBody UserForm userForm)
    {
        UserDto user = getUserDto(userForm);
        Long id = userService.add(user);
        user.setId(id);

        OfficeDto office = officeService.getOffice(userForm.getIdOffice());

        if (office == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<UserDto> users = new ArrayList<>(office.getEmployees());
        users.add(user);
        office.setEmployees(users);

        officeService.editOffice(office);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<UserForm> editedUser(@PathVariable Long id, @Valid @RequestBody UserForm userForm)
    {
        UserDto user = userService.get(userForm.getId());

        if (user == null)
        {
            return new ResponseEntity<>(userForm, HttpStatus.BAD_REQUEST);
        }
        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());

        userService.edit(user);

        for (OfficeDto o : officeService.getAllOffices())
        {
            if (o.getEmployees().contains(user))
            {
                officeService.deleteEmployeeFromOffice(o, user);
            }
        }
        OfficeDto newOffice = officeService.getOffice(userForm.getIdOffice());
        if (newOffice != null)
        {
            officeService.addEmployeeToOffice(newOffice, user);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long id)
    {
        UserDto user;
        try
        {
            user = userService.get(id);
            if (user == null)
            {

                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            for (OfficeDto o : officeService.getAllOffices())
            {
                if (o.getEmployees().contains(user))
                {
                    officeService.deleteEmployeeFromOffice(o, user);
                }
            }
            userService.delete(user);
        } catch (IllegalArgumentException | DataAccessException ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UserDto getUserDto(UserForm userForm)
    {
        UserDto user = new UserDto();

        user.setId(userForm.getId());
        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());

        return user;
    }

    public static class UserForm
    {

        private Long id;

        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @NotBlank
        private String birthNumber;

        private String address;

        @NotNull
        private Long idOffice;

        public Long getIdOffice()
        {
            return idOffice;
        }

        public void setIdOffice(Long idOffice)
        {
            this.idOffice = idOffice;
        }

        public Long getId()
        {
            return id;
        }

        public void setId(Long id)
        {
            this.id = id;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getBirthNumber()
        {
            return birthNumber;
        }

        public void setBirthNumber(String birthNumber)
        {
            this.birthNumber = birthNumber;
        }

        public String getAddress()
        {
            return address;
        }

        public void setAddress(String address)
        {
            this.address = address;
        }

    }
}
