package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.dto.UserRoleDto;
import cz.muni.fi.pa165.carpark.exception.UserAlreadyExists;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.UserCredentialsService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.servicefacade.UserAccountServiceFacade;
import cz.muni.fi.pa165.carpark.web.dto.CredentialsForm;
import cz.muni.fi.pa165.carpark.web.dto.CredentialsPasswordForm;
import cz.muni.fi.pa165.carpark.web.dto.UserAddForm;
import cz.muni.fi.pa165.carpark.web.dto.UserEditForm;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * User controller.
 *
 * @author Tomas Vasicek
 */
@Controller
@RequestMapping("/auth/user")
public class UserController
{

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCredentialsService credentialsService;

    @Autowired
    private UserAccountServiceFacade userAccountServiceFacade;

    @Autowired
    private OfficeService officeService;

    @ModelAttribute(value = "userRoles")
    public UserRoleDto.RoleType[] userRoles()
    {
        return UserRoleDto.RoleType.values();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model)
    {
        List<UserDto> users = userService.getAll();

        model.addAttribute("users", users);
        model.addAttribute("userForm", new UserAddForm());

        return "user-list";
    }

    @RequestMapping(value = "/{id}/profile", method = RequestMethod.GET)
    public String getAccount(@PathVariable Long id, Model model)
    {

        UserDto user = userService.get(id);
        UserEditForm userForm = getUserEditForm(user);

        model.addAttribute("userForm", userForm);
        model.addAttribute("passwordForm", new CredentialsPasswordForm());

        return "user-profile-form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUserRequest(Model model, RedirectAttributes redirectAttributes)
    {
        model.addAttribute("userForm", new UserAddForm());

        List<OfficeDto> offices = officeService.getAllOffices();
        if (offices.isEmpty())
        {
            redirectAttributes.addFlashAttribute("error", "error.user.nooffices");
            return "redirect:/auth/user";
        }

        model.addAttribute("offices", offices);
        return "user-form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute(value = "userForm") UserAddForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("offices", officeService.getAllOffices());  
            return "user-form";
        } else
        {
            UserDto user = getUserDto(userForm);

            UserRoleDto role = new UserRoleDto();
            role.setRoleName(userForm.getRole().toString());

            UserCredentialsDto credentialsDto = new UserCredentialsDto(userForm.getUsername(), passwordEncoder.encode(userForm.getPassword()), Boolean.TRUE, user, role);

            try
            {
                userAccountServiceFacade.registerUser(credentialsDto);
            } catch (UserAlreadyExists ex)
            {
                model.addAttribute("offices", officeService.getAllOffices());
                model.addAttribute("error", "error.user.useralreadyexists");
                return "user-form";
            }

            redirectAttributes.addFlashAttribute("msg", "msg.user.created");
            return "redirect:/auth/user";
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editUserRequest(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes)
    {
        UserDto user = userService.get(id);
        UserEditForm userForm = getUserEditForm(user);

        UserCredentialsDto credentialsDto = credentialsService.get(id);
        CredentialsForm credentialsForm = new CredentialsForm();
        credentialsForm.setUsername(credentialsDto.getUsername());
        credentialsForm.setRole(getRoleType(credentialsDto));

        model.addAttribute("userForm", userForm);
        model.addAttribute("credentialsForm", credentialsForm);
        model.addAttribute("passwordForm", new CredentialsPasswordForm());

        model.addAttribute("action", "edit");

        if (officeService.getAllOffices().isEmpty() || user.getOfficeDto() == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.user.nooffices");
            return "redirect:/auth/user";
        }

        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("selectedOfficeId", userForm.getIdOffice());

        return "user-form";
    }

    @RequestMapping(value = "/{id}/edit", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String editUser(@PathVariable Long id, @Valid @ModelAttribute(value = "userForm") UserEditForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors())
        {
            System.out.println("\n#1");
            model.addAttribute("action", "edit");
System.out.println("\n#2");
            UserCredentialsDto credentialsDto = credentialsService.get(id);
            System.out.println("\n#3");
            CredentialsForm credentialsForm = new CredentialsForm();
            System.out.println("\n#4");
            credentialsForm.setUsername(credentialsDto.getUsername());
            System.out.println("\n#5");
            credentialsForm.setRole(getRoleType(credentialsDto));
System.out.println("\n#6");
            model.addAttribute("credentialsForm", credentialsForm);
            System.out.println("\n#7");
            model.addAttribute("passwordForm", new CredentialsPasswordForm());
            System.out.println("\n#8");
            model.addAttribute("offices", officeService.getAllOffices()); 
            System.out.println("\n#9");
            return "user-form";
        }

        UserDto user = userService.get(id);
        if (user == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.user.edited");
            return "redirect:/auth/user";
        }
        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());

        OfficeDto officeDto = officeService.getOffice(userForm.getIdOffice());
        if (officeDto == null)
        {
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            model.addAttribute("error", "error.user.nooffices");

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setUsername(credentialsDto.getUsername());
            credentialsForm.setRole(getRoleType(credentialsDto));

            model.addAttribute("credentialsForm", credentialsForm);
            model.addAttribute("passwordForm", new CredentialsPasswordForm());

            return "user-form";
        }

        userService.edit(user);

        redirectAttributes.addFlashAttribute("msg", "msg.user.edited");
        return "redirect:/auth/user";

    }

    @RequestMapping(value = "/{id}/profile/edit", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String editUserProfile(@PathVariable Long id, @Valid @ModelAttribute(value = "userForm") UserEditForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors())
        {
            UserCredentialsDto credentialsDto = credentialsService.get(id);
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setUsername(credentialsDto.getUsername());
            credentialsForm.setRole(getRoleType(credentialsDto));

            model.addAttribute("credentialsForm", credentialsForm);
            model.addAttribute("passwordForm", new CredentialsPasswordForm());
            model.addAttribute("offices", officeService.getAllOffices()); 
            return "user-profile-form";
        }

        UserDto user = userService.get(id);
        if (user == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.user.edited");
            return "redirect:/auth/user";
        }
        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());

        OfficeDto officeDto = officeService.getOffice(userForm.getIdOffice());
        if (officeDto == null)
        {
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            model.addAttribute("error", "error.user.nooffices");

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setUsername(credentialsDto.getUsername());
            credentialsForm.setRole(getRoleType(credentialsDto));

            model.addAttribute("credentialsForm", credentialsForm);
            model.addAttribute("passwordForm", new CredentialsPasswordForm());

            return "user-profile-form";
        }

        userService.edit(user);

        redirectAttributes.addFlashAttribute("msg", "msg.user.edited");
        return "redirect:/auth/user";

    }

    
    @RequestMapping(value = "/{id}/credentials/edit", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String editUserCredentials(@PathVariable Long id, @Valid @ModelAttribute(value = "credentialsForm") CredentialsForm credentialsForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if (bindingResult.hasErrors())
        {
            UserDto user = userService.get(id);
            UserEditForm userEditForm = getUserEditForm(user);
            model.addAttribute("userForm", userEditForm);

            model.addAttribute("passwordForm", new CredentialsPasswordForm());
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            return "user-form";
        }

        try
        {
            UserRoleDto role = new UserRoleDto();
            role.setRoleName(credentialsForm.getRole().toString());

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            credentialsDto.setRole(role);

            UserRoleDto role1 = new UserRoleDto();
            role1.setRoleName(credentialsForm.getRole().toString());

            credentialsService.update(credentialsDto);

        } catch (UserAlreadyExists ex)
        {
            UserDto user = userService.get(id);
            UserEditForm userEditForm = getUserEditForm(user);
            model.addAttribute("userForm", userEditForm);

            model.addAttribute("passwordForm", new CredentialsPasswordForm());
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            model.addAttribute("error", "error.user.useralreadyexists");
            return "user-form";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.edited");
        return "redirect:/auth/user";

    }

    @RequestMapping(value = "/{id}/credentials/password/edit", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String editUserPassword(@PathVariable Long id, @Valid @ModelAttribute(value = "passwordForm") CredentialsPasswordForm passwordForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if (bindingResult.hasErrors())
        {
            UserDto user = userService.get(id);
            UserEditForm userEditForm = getUserEditForm(user);
            model.addAttribute("userForm", userEditForm);

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setUsername(credentialsDto.getUsername());
            credentialsForm.setRole(getRoleType(credentialsDto));

            model.addAttribute("credentialsForm", credentialsForm);

            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            return "user-form";
        }

        try
        {

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            credentialsDto.setPassword(passwordEncoder.encode(passwordForm.getPassword()));

            credentialsService.update(credentialsDto);

        } catch (UserAlreadyExists ex)
        {
            UserDto user = userService.get(id);
            UserEditForm userEditForm = getUserEditForm(user);
            model.addAttribute("userForm", userEditForm);

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setUsername(credentialsDto.getUsername());
            credentialsForm.setRole(getRoleType(credentialsDto));

            model.addAttribute("credentialsForm", credentialsForm);
            model.addAttribute("passwordForm", new CredentialsPasswordForm());
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            model.addAttribute("error", "error.user.useralreadyexists");
            return "user-form";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.edited");
        return "redirect:/auth/user";

    }
    
    @RequestMapping(value = "/{id}/profile/password/edit", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String editProfileUserPassword(@PathVariable Long id, @Valid @ModelAttribute(value = "passwordForm") CredentialsPasswordForm passwordForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if (bindingResult.hasErrors())
        {
            UserDto user = userService.get(id);
            UserEditForm userEditForm = getUserEditForm(user);
            model.addAttribute("userForm", userEditForm);

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setUsername(credentialsDto.getUsername());
            credentialsForm.setRole(getRoleType(credentialsDto));

            model.addAttribute("credentialsForm", credentialsForm);

            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            return "user-profile-form";
        }

        try
        {

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            credentialsDto.setPassword(passwordEncoder.encode(passwordForm.getPassword()));

            credentialsService.update(credentialsDto);

        } catch (UserAlreadyExists ex)
        {
            UserDto user = userService.get(id);
            UserEditForm userEditForm = getUserEditForm(user);
            model.addAttribute("userForm", userEditForm);

            UserCredentialsDto credentialsDto = credentialsService.get(id);
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setUsername(credentialsDto.getUsername());
            credentialsForm.setRole(getRoleType(credentialsDto));

            model.addAttribute("credentialsForm", credentialsForm);
            model.addAttribute("passwordForm", new CredentialsPasswordForm());
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); 
            model.addAttribute("error", "error.user.useralreadyexists");
            return "user-profile-form";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.edited");
        return "redirect:/auth/user";

    }

    @RequestMapping(value = "/{id}/delete", method =
    {
        RequestMethod.POST, RequestMethod.DELETE
    })
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes)
    {
        UserCredentialsDto userCredentials;
        try
        {
            userCredentials = credentialsService.get(id);
            if (userCredentials == null)
            {
                redirectAttributes.addFlashAttribute("error", "error.user.deleted");
                return "redirect:/auth/user";
            }

            userAccountServiceFacade.removeUserAccount(userCredentials);
        } catch (IllegalArgumentException | DataAccessException ex)
        {
            redirectAttributes.addFlashAttribute("error", "error.user.deleted" + ex);
            return "redirect:/auth/user";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.deleted");
        return "redirect:/auth/user";
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

    private UserAddForm getUserAddForm(UserDto user)
    {
        UserAddForm userForm = new UserAddForm();

        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setAddress(user.getAddress());
        userForm.setBirthNumber(user.getBirthNumber());
        userForm.setIdOffice(user.getOfficeDto().getId()); 

        return userForm;

    }

    private UserEditForm getUserEditForm(UserDto user)
    {
        UserEditForm userForm = new UserEditForm();

        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setAddress(user.getAddress());
        userForm.setBirthNumber(user.getBirthNumber());
        userForm.setIdOffice(user.getOfficeDto().getId());  

        return userForm;

    }

    private UserRoleDto.RoleType getRoleType(UserCredentialsDto credentialsDto)
    {
        switch (credentialsDto.getRole().getRoleName())
        {
            case "ROLE_ADMIN":
                return UserRoleDto.RoleType.ADMIN;
            case "ROLE_MANAGER":
                return UserRoleDto.RoleType.MANAGER;
            default:
                return UserRoleDto.RoleType.USER;
        }
    }
}
