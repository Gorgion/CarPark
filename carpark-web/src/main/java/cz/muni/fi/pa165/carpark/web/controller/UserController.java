package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.exception.CarAlreadyReserved;
import cz.muni.fi.pa165.carpark.service.CarService;
import cz.muni.fi.pa165.carpark.service.RentalService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.validation.AfterDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import org.aspectj.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/auth/user/{userId}/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @ModelAttribute("userId")
    public Long getUserId(@PathVariable Long userId)
    {
        return userId;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(@PathVariable(value = "userId") Long userId, Model model)
    {
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "user-list";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public UserForm getNewForm() {
        return new UserForm();
    }
    
    @RequestMapping(value = "/{id}/delete", method =
    {
        RequestMethod.POST, RequestMethod.DELETE
    })
    public String userDeleted(@PathVariable Long userId, @PathVariable Long id, /*@Valid @ModelAttribute RentalDto rental, BindingResult result,*/ RedirectAttributes redirectAttributes)
    {
        UserDto user = userService.get(id);
        if (user == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.user.deleted");
        }
        userService.delete(user);
        redirectAttributes.addFlashAttribute("msg", "msg.user.deleted");
        
        return "redirect:/auth/user/" + userId + "/user";
    }
    
    public static class UserForm
    {
        @NotNull
        private String firstName;
       
        @NotNull
        private String lastName;
        
        @NotNull
        private String birthNumber;
        
        private String address;
        
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
