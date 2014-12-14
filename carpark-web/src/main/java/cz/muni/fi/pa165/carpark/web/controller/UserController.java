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
import cz.muni.fi.pa165.carpark.web.dto.UserForm;
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
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserCredentialsService credentialsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccountServiceFacade userAccountServiceFacade;

    @Autowired
    private OfficeService officeService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<UserDto> users = userService.getAll();

        model.addAttribute("users", users);
        model.addAttribute("userForm", new UserForm());

        return "user-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUserRequest(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("userForm", new UserForm());

        List<OfficeDto> offices = officeService.getAllOffices();
        if (offices.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "error.user.nooffices");
            return "redirect:/auth/user";
        }

        model.addAttribute("offices", offices);
        return "user-form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offices", officeService.getAllOffices());  // TODO TRY-CATCH WHEN NO OFFICES - OK by CAR
            return "user-form";
        } else {
            UserDto user = getUserDto(userForm);

            if (credentialsService.getByUsername(userForm.getUsername()) != null) {
                model.addAttribute("offices", officeService.getAllOffices());  // TODO TRY-CATCH WHEN NO OFFICES
                //bindingResult.reject("username", "user.usernameAlreadyExist");
                model.addAttribute("error", "user.usernameAlreadyExist");

                return "user-form";
            }

            Set<UserRoleDto> roles = new HashSet<>();

            UserCredentialsDto credentialsDto = new UserCredentialsDto(userForm.getUsername(), passwordEncoder.encode(userForm.getPassword()), Boolean.TRUE, user, roles);

            //TEMP
            UserRoleDto role1 = new UserRoleDto();
            role1.setRoleName(UserRoleDto.RoleType.ADMIN.name());
            role1.setUserCredentials(credentialsDto);

            roles.add(role1);
            try {
                userAccountServiceFacade.registerUser(credentialsDto);
            } catch (UserAlreadyExists ex) {
                model.addAttribute("offices", officeService.getAllOffices());
                model.addAttribute("error", "error.user.useralreadyexists");
                return "user-form";
            }

            redirectAttributes.addFlashAttribute("msg", "msg.user.created");
            return "redirect:/auth/user";
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editUserRequest(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        UserDto user = userService.get(id);
        UserForm userForm = getUserForm(user);

        model.addAttribute("userForm", userForm);
        model.addAttribute("action", "edit");

        if (officeService.getAllOffices().isEmpty() || user.getOfficeDto() == null) {
            redirectAttributes.addFlashAttribute("error", "error.user.nooffices");
            return "redirect:/auth/user";
        }

        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("selectedOfficeId", userForm.getIdOffice());

        return "user-form";
    }

    @RequestMapping(value = "/{id}/edit", method = {RequestMethod.POST, RequestMethod.PUT})
    public String editUser(@PathVariable Long id, @Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); // TODO TRY-CATCH WHEN NO OFFICES
            return "user-form";
        }

        UserDto user = userService.get(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "error.user.edited");
            return "redirect:/auth/user";
        }
        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());

        OfficeDto officeDto = officeService.getOffice(userForm.getIdOffice());
        if (officeDto == null) {
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); // TODO TRY-CATCH WHEN NO OFFICES
            model.addAttribute("error", "error.user.nooffices");
            return "user-form";
        }
        try {
            userService.edit(user);
        } catch (UserAlreadyExists ex) {

            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); // TODO TRY-CATCH WHEN NO OFFICES
            model.addAttribute("error", "error.user.useralreadyexists");
            return "user-form";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.edited");
        return "redirect:/auth/user";

    }

    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        UserDto user;
        try {
            user = userService.get(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "error.user.deleted");
                return "redirect:/auth/user";
            }
            userService.delete(user);
            //userAccountServiceFacade.removeUserAccount(...);
        } catch (IllegalArgumentException | DataAccessException ex) {
            redirectAttributes.addFlashAttribute("error", "error.user.deleted" + ex);
            return "redirect:/auth/user";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.deleted");
        return "redirect:/auth/user";
    }

    private UserDto getUserDto(UserForm userForm) {
        UserDto user = new UserDto();

        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setOfficeDto(officeService.getOffice(userForm.getIdOffice()));  // TODO TRY-CATCH WHEN NO OFFICES

        return user;
    }

    private UserForm getUserForm(UserDto user) {
        UserForm userForm = new UserForm();

        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setAddress(user.getAddress());
        userForm.setBirthNumber(user.getBirthNumber());
        userForm.setIdOffice(user.getOfficeDto().getId());  // TODO TRY-CATCH WHEN NO OFFICES

        return userForm;

    }

}
