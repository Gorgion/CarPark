package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.OfficeDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.OfficeService;
import cz.muni.fi.pa165.carpark.service.RentalService;
import cz.muni.fi.pa165.carpark.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
public class UserController
{

    @Autowired
    private UserService userService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private RentalService rentalService;


    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model)
    {
        List<UserDto> users = userService.getAll();

        model.addAttribute("users", users);
        model.addAttribute("userForm", new UserForm());
//        lastOfficeId = null;

        return "user-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addRequest(Model model)
    {
        model.addAttribute("userForm", new UserForm());
        model.addAttribute("offices", officeService.getAllOffices());
        return "user-form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addedUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors())
        {

            model.addAttribute("offices", officeService.getAllOffices());
            return "user-form";
        } else
        {
            UserDto user = getUserDto(userForm);
            System.out.println("\nADD>>");
            Long id = userService.add(user);
            System.out.println("\nok");
            user.setId(id);

            OfficeDto office = officeService.getOffice(userForm.getIdOffice());
            
            System.out.println("\n\n>>>\n" + office);
            
            if (office != null)
            {
                List<UserDto> users = new ArrayList<>(office.getEmployees());
                System.out.println("\n1\n" + users);
                users.add(user);
                System.out.println("\n1\n" + users);
                office.setEmployees(users);

                officeService.editOffice(office);
//                officeService.addEmployeeToOffice(office, user);
            }

            redirectAttributes.addFlashAttribute("msg", "msg.user.created");
            return "redirect:/auth/user";
        }
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model)
    {
        UserDto user = userService.get(id);
        UserForm userForm = getUserForm(user);

        model.addAttribute("userForm", userForm);
        model.addAttribute("action", "edit");
        model.addAttribute("offices", officeService.getAllOffices());

//        lastOfficeId = null;
        for (OfficeDto o : officeService.getAllOffices())
        {
            if (o.getEmployees().contains(user))
            {
                model.addAttribute("selectedOfficeId", o.getID());
                break;
            }
        }
//        model.addAttribute("selectedOfficeId", lastOfficeId);

        return "user-form";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editedUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if (bindingResult.hasErrors())
        {
            model.addAttribute("action", "edit");
            model.addAttribute("offices", officeService.getAllOffices());
            return "user-form";
        } else
        {
            UserDto user = userService.get(userForm.getId());

            if (user == null)
            {
                redirectAttributes.addFlashAttribute("error", "error.user.edited");
                return "redirect:/auth/user";
            }
            user.setAddress(userForm.getAddress());
            user.setBirthNumber(userForm.getBirthNumber());
            user.setFirstName(userForm.getFirstName());
            user.setLastName(userForm.getLastName());
            
            userService.edit(user);

            for (OfficeDto o : officeService.getAllOffices())
            {System.out.println("\n\n#3\n" + o);
                if (o.getEmployees().contains(user))
                {System.out.println("\n\n#4");
                    //List<UserDto> c = new ArrayList<>(o.getEmployees());
                    //c.remove(user);
                    //o.setEmployees(c);
                    //officeService.editOffice(o);
                    officeService.deleteEmployeeFromOffice(o, user);
                }
            }
            OfficeDto newOffice = officeService.getOffice(userForm.getIdOffice());
            if (newOffice != null)
            {
                //List<UserDto> users = new ArrayList<>(newOffice.getEmployees());

                //users.add(user);
                //newOffice.setEmployees(users);
                //System.out.println("\n\n#8\n" + newOffice);
                //officeService.editOffice(newOffice);
                //System.out.println("\n\n#9");
                officeService.addEmployeeToOffice(newOffice, user);
            }
        }
        System.out.println("\n\n#10");
        redirectAttributes.addFlashAttribute(
                "msg", "msg.user.edited");
System.out.println("\n\n#11");
        return "redirect:/auth/user";
//        }

    }

    @RequestMapping(value = "/{id}/delete", method =
    {
        RequestMethod.POST, RequestMethod.DELETE
    })
    public String delete(@PathVariable long id, RedirectAttributes redirectAttributes)
    {
        UserDto user;
        try
        {
            user = userService.get(id);
            if (user == null)
            {
                redirectAttributes.addFlashAttribute("error", "error.user.deleted");
                return "redirect:/auth/user";
            }
            for(OfficeDto o : officeService.getAllOffices())
            {
                if(o.getEmployees().contains(user))
                {
                    officeService.deleteEmployeeFromOffice(o, user);
                }
            }
            userService.delete(user);
        } catch (IllegalArgumentException | DataAccessException ex)
        {
            redirectAttributes.addFlashAttribute("error", "error.user.deleted" + ex);
            return "redirect:/auth/user";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.user.deleted");
        return "redirect:/auth/user";
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

    private UserForm getUserForm(UserDto user)
    {
        UserForm userForm = new UserForm();

        userForm.setId(user.getId());
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setAddress(user.getAddress());
        userForm.setBirthNumber(user.getBirthNumber());

        return userForm;

    }

    public static class UserForm
    {

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
