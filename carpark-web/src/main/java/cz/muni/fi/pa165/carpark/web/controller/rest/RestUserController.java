package cz.muni.fi.pa165.carpark.web.controller.rest;

import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.web.dto.UserForm;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        if (user.getOfficeDto() == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long id = userService.add(user);
        user.setId(id);

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

        OfficeDto office = officeService.getOffice(userForm.getIdOffice());
        if (office == null)
        {
            return new ResponseEntity<>(userForm, HttpStatus.BAD_REQUEST);
        }

        user.setOfficeDto(office);

        userService.edit(user);

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

        OfficeDto office = officeService.getOffice(userForm.getIdOffice());
        user.setOfficeDto(office);

        return user;
    }
}
