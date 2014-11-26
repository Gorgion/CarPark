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
import java.util.Locale;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import org.aspectj.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import org.springframework.web.util.UriComponentsBuilder;

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
    private MessageSource messageSource;

    //@ModelAttribute("user-form")
    //@RequestMapping(value = "/add", method = RequestMethod.GET)
    public UserForm getNewForm() {
        return new UserForm();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model)
    { 
//        UserDto userDto = new UserDto();
//        userDto.setFirstName("Name");
//        userDto.setLastName("Surname");
//        userDto.setAddress("Address");
//        userDto.setBirthNumber("958456/8524");
//        userService.add(userDto);
        List<UserDto> users = userService.getAll();
        model.addAttribute("users", users);
        model.addAttribute("userForm", new UserForm());
        return "user-list";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addRequest(Model model)
    {
        //System.out.println("On Submit");
        model.addAttribute("userForm", new UserForm());
        return "user-form";
    }
    
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editRental(@PathVariable Long id, Model model)
    {
        UserDto user = userService.get(id);
        
        UserForm userForm = new UserForm();
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setAddress(user.getAddress());
        userForm.setBirthNumber(user.getBirthNumber());
        
        model.addAttribute("userForm", userForm);
        model.addAttribute("action", "edit");
        
        return "user-form";
    }
        
    
    @RequestMapping(value = "/{id}/delete", method = { RequestMethod.POST, RequestMethod.DELETE })
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder) {
        UserDto user = userService.get(id);
        userService.delete(user);
        redirectAttributes.addFlashAttribute(
                "message",
                messageSource.getMessage("user.delete.message", new Object[]{ user.getId(), user.getFirstName(), user.getLastName(), user.getBirthNumber(), user.getAddress()}, locale)
        );
        return "redirect:" + uriBuilder.path("/auth/user").build();
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
