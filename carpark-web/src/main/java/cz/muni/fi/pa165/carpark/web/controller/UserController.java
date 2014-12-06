package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.RentalService;
import cz.muni.fi.pa165.carpark.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    private OfficeService officeService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<UserDto> users = userService.getAll();

        model.addAttribute("users", users);
        model.addAttribute("userForm", new UserForm());

        return "user-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addRequest(Model model) {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("offices", officeService.getAllOffices());
        return "user-form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addedUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("offices", officeService.getAllOffices());  // TODO TRY-CATCH WHEN NO OFFICES
            return "user-form";
        } else {
            UserDto user = getUserDto(userForm);
            userService.add(user);
            
            redirectAttributes.addFlashAttribute("msg", "msg.user.created");
            return "redirect:/auth/user";
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        UserDto user = userService.get(id);
        UserForm userForm = getUserForm(user);

        model.addAttribute("userForm", userForm);
        model.addAttribute("action", "edit");
        model.addAttribute("offices", officeService.getAllOffices());
        model.addAttribute("selectedOfficeId",userForm.getIdOffice());
        
        return "user-form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editedUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices()); // TODO TRY-CATCH WHEN NO OFFICES
            return "user-form";
        } else {
            UserDto user = userService.get(userForm.getId());

            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "error.user.edited");
                return "redirect:/auth/user";
            }
            user.setAddress(userForm.getAddress());
            user.setBirthNumber(userForm.getBirthNumber());
            user.setFirstName(userForm.getFirstName());
            user.setLastName(userForm.getLastName());
            user.setOfficeDto(officeService.getOffice(userForm.getIdOffice())); // TODO TRY-CATCH WHEN NO OFFICE
            
            userService.edit(user);
        }
        redirectAttributes.addFlashAttribute("msg", "msg.user.edited");
        return "redirect:/auth/user";

    }

    @RequestMapping(value = "/{id}/delete", method
            = {
                RequestMethod.POST, RequestMethod.DELETE
            })
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
        UserDto user;
        try {
            user = userService.get(id);
            if (user == null) {
                redirectAttributes.addFlashAttribute("error", "error.user.deleted");
                return "redirect:/auth/user";
            }
            userService.delete(user);
        } catch (IllegalArgumentException | DataAccessException ex) {
            redirectAttributes.addFlashAttribute("error", "error.user.deleted" + ex);
            return "redirect:/auth/user";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.deleted");
        return "redirect:/auth/user";
    }

    private UserDto getUserDto(UserForm userForm) {
        UserDto user = new UserDto();

        user.setId(userForm.getId());
        user.setAddress(userForm.getAddress());
        user.setBirthNumber(userForm.getBirthNumber());
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setOfficeDto(officeService.getOffice(userForm.getIdOffice()));  // TODO TRY-CATCH WHEN NO OFFICES

        return user;
    }

    private UserForm getUserForm(UserDto user) {
        UserForm userForm = new UserForm();

        userForm.setId(user.getId());
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setAddress(user.getAddress());
        userForm.setBirthNumber(user.getBirthNumber());
        userForm.setIdOffice(user.getOfficeDto().getID());  // TODO TRY-CATCH WHEN NO OFFICES
        
        return userForm;

    }

    public static class UserForm {

        private Long id;

        @NotEmpty
        private String firstName;

        @NotEmpty
        private String lastName;

        @NotEmpty
        private String birthNumber;

        private String address;

        @NotNull
        private Long idOffice;

        public Long getIdOffice() {
            return idOffice;
        }

        public void setIdOffice(Long idOffice) {
            this.idOffice = idOffice;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getBirthNumber() {
            return birthNumber;
        }

        public void setBirthNumber(String birthNumber) {
            this.birthNumber = birthNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    }
}
