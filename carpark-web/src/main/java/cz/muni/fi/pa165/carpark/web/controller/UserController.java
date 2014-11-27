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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    
        @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addedUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale) {
               System.out.println("add!");
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                //log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                //log.debug("FieldError: {}", fe);
            }
            //return book.getId()==null?"book/list":"book/edit";
        }
 

          System.out.println(userForm);
          UserDto user = new UserDto();
           user.setAddress(userForm.getAddress());
           user.setBirthNumber(userForm.getBirthNumber());
           user.setFirstName(userForm.getFirstName());
           user.setLastName(userForm.getLastName());
           userService.add(user);
           System.out.println("add!+"+ user.toString());

            /*
            redirectAttributes.addFlashAttribute(
                    "message",
                    messageSource.getMessage("book.add.message", new Object[]{book.getTitle(), book.getAuthor(), book.getId()}, locale)
            );
            */
        
        redirectAttributes.addFlashAttribute("msg", messageSource.getMessage("msg.user.created", null, locale));
        return "redirect:" + uriBuilder.path("/auth/user").build();
    }
    
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model)
    {
                System.out.println("edit!");
        UserDto user = userService.get(id);
        System.out.println("id:"+ id);
        System.out.println("user:"+ user.getId());
        UserForm userForm = new UserForm();
        userForm.setId(user.getId());
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setAddress(user.getAddress());
        userForm.setBirthNumber(user.getBirthNumber());
        
        model.addAttribute("userForm", userForm);
        model.addAttribute("action", "edit");
        
        return "user-form";
    }
        
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editedUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, UriComponentsBuilder uriBuilder, Locale locale) {
               System.out.println("FUNGUJ!");
        if (bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                //log.debug("ObjectError: {}", ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                //log.debug("FieldError: {}", fe);
            }
            //return book.getId()==null?"book/list":"book/edit";
        }
 

          System.out.println(userForm);
           UserDto user = userService.get(userForm.getId());
             System.out.println("FUNGUJ2!+"+ user.toString());
           user.setAddress(userForm.getAddress());
           user.setBirthNumber(userForm.getBirthNumber());
           user.setFirstName(userForm.getFirstName());
           user.setLastName(userForm.getLastName());
           System.out.println("prask!");
           userService.edit(user);
           System.out.println("fuk!");

        
        redirectAttributes.addFlashAttribute("msg", messageSource.getMessage("msg.user.edited", null, locale));
        return "redirect:" + uriBuilder.path("/auth/user").build();
    }
    
    
    @RequestMapping(value = "/{id}/delete", method = { RequestMethod.POST, RequestMethod.DELETE })
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes, Locale locale, UriComponentsBuilder uriBuilder) {
        UserDto user = userService.get(id);
        if (user == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.user.deleted");
        }else{
             userService.delete(user);
            redirectAttributes.addFlashAttribute("msg", "msg.user.deleted");    
        }

        return "redirect:" + uriBuilder.path("/auth/user").build();
    }    
    
    
    
    public static class UserForm
    {
        @NotNull
        private Long id;

        @NotNull
        private String firstName;

        @NotNull
        private String lastName;
        
        @NotNull
        private String birthNumber;
        
        private String address;
        
        @Override
        public String toString() {
            return "UserForm{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthNumber=" + birthNumber + ", address=" + address + '}';
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
