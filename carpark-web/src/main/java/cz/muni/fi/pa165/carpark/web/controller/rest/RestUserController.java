package cz.muni.fi.pa165.carpark.web.controller.rest;

import com.fasterxml.jackson.annotation.JsonView;
import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.dto.UserRoleDto;
import cz.muni.fi.pa165.carpark.exception.UserAlreadyExists;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserCredentialsService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.servicefacade.UserAccountServiceFacade;
import cz.muni.fi.pa165.carpark.web.dto.RestOfficeDto;
import cz.muni.fi.pa165.carpark.web.dto.RestUserDto;
import cz.muni.fi.pa165.carpark.web.dto.UserAddForm;
import cz.muni.fi.pa165.carpark.web.dto.UserEditForm;
import cz.muni.fi.pa165.carpark.web.rest.JsonViews;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/rest/users")
public class RestUserController
{

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountServiceFacade userAccountServiceFacade;

    @Autowired
    private UserCredentialsService credentialsService;
    
    @Autowired
    private OfficeService officeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @JsonView(JsonViews.Users.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<RestUserDto>> list()
    {
        List<RestUserDto> users = new ArrayList<>();
        for (UserDto userDto : userService.getAll())
        {
            RestUserDto restUser = new RestUserDto(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getBirthNumber(), userDto.getAddress());
            RestOfficeDto restOffice = new RestOfficeDto(userDto.getOfficeDto().getId(), userDto.getOfficeDto().getAddress());

            restUser.setOffice(restOffice);

            users.add(restUser);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @JsonView(JsonViews.Users.class)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<RestUserDto> getEntity(@PathVariable Long id)
    {
        UserDto userDto = userService.get(id);
        RestUserDto restUser = new RestUserDto(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getBirthNumber(), userDto.getAddress());
        RestOfficeDto restOffice = new RestOfficeDto(userDto.getOfficeDto().getId(), userDto.getOfficeDto().getAddress());

        restUser.setOffice(restOffice);

        return new ResponseEntity<>(restUser, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<UserAddForm> addedUser(@Valid @RequestBody UserAddForm userForm)
    {
        UserDto user = getUserDto(userForm);

        if (user.getOfficeDto() == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserRoleDto role = new UserRoleDto();
        role.setRoleName(userForm.getRole().toString());

        UserCredentialsDto credentialsDto = new UserCredentialsDto(userForm.getUsername(), passwordEncoder.encode(userForm.getPassword()), Boolean.TRUE, user, role);

        try
        {
            userAccountServiceFacade.registerUser(credentialsDto);
        } catch (UserAlreadyExists ex)
        {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<UserEditForm> editedUser(@PathVariable Long id, @Valid @RequestBody UserEditForm userForm)
    {
        UserDto user = userService.get(id);

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
        
        OfficeDto prevOffice = user.getOfficeDto();
        if(prevOffice != null)
        {
            prevOffice.setManager(null);
            officeService.editOffice(prevOffice);
        }

        user.setOfficeDto(office);

        userService.edit(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long id)
    {
        try
        {
            UserCredentialsDto credentials = credentialsService.get(id);
            
            if (credentials == null)
            {         
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            userAccountServiceFacade.removeUserAccount(credentials);            
        } catch (IllegalArgumentException | DataAccessException ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private UserDto getUserDto(UserAddForm userForm)
    {
        UserDto user = new UserDto();

        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setOfficeDto(officeService.getOffice(userForm.getIdOffice()));

        return user;
    }
}
