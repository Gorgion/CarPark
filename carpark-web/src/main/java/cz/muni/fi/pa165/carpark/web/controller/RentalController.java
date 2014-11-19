package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.service.RentalService;
import cz.muni.fi.pa165.carpark.service.UserService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.aspectj.util.LangUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Rental controller.
 *
 * @author Tomas Svoboda
 */
@Controller
@RequestMapping("/auth/user/{userId}/rental")
public class RentalController
{
    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private UserService userService;
    
    @ModelAttribute("states")
    public RentalDto.State[] rentalStates()
    {                
        return RentalDto.State.values();
    }
    
    @ModelAttribute("userId")
    public Long getUserId(@PathVariable Long userId)
    {
        return userId;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(@PathVariable(value = "userId") Long userId, Model model)
    {
        UserDto user = userService.get(userId);                
        
        List<RentalDto> rentals =  rentalService.getAllByUser(user);
        
        model.addAttribute("rentals", rentals);
        
        return "rental-list";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createRental(@PathVariable(value = "userId") Long userId, Model model)
    {
        UserDto user = userService.get(userId);                
        
        RentalDto rental = new RentalDto();
        rental.setUser(user);
        
        model.addAttribute("rental", rental);
        model.addAttribute("action", "add");
        
        return "rental-form";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String rentalCreated(@PathVariable(value = "userId") Long userId, @Valid @ModelAttribute RentalDto rental, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors())
        {
            return "rental-form";
        }
        
        UserDto user = userService.get(userId);                        
        
        rental.setUser(user);
        
        rentalService.create(rental);
        
        redirectAttributes.addFlashAttribute("msg", "msg.rental.created");
        
        return "redirect:/list";
    }
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editRental(@PathVariable Long id, Model model)
    {
        RentalDto rental = rentalService.get(id);
        
        model.addAttribute("rental", rental);
        model.addAttribute("action", "edit");
        
        return "rental-form";
    }
    
    @RequestMapping(value = "/{id}/edit", method = {RequestMethod.POST, RequestMethod.PUT})
    public String rentalEdited(@PathVariable Long id, @Valid @ModelAttribute RentalDto rental, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors())
        {
            return "rental-form";
        }
        
        RentalDto originalRental = rentalService.get(id);                
        
        if(originalRental == null)
        {
//            result.addError("");
            
            return "rental-form";
        }
        
        rental.setUser(originalRental.getUser());
        
        rentalService.edit(rental);
        
        redirectAttributes.addFlashAttribute("msg", "msg.rental.edited");
        
        return "rental-form";
    }
    
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String deleteRental(@PathVariable Long id, Model model)
    {
        RentalDto rental = rentalService.get(id);
        
        model.addAttribute("rental", rental);
        
        return "rental-form";
    }
    
    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.POST, RequestMethod.DELETE})
    public String rentalDeleted(@PathVariable Long id, /*@Valid @ModelAttribute RentalDto rental, BindingResult result,*/ RedirectAttributes redirectAttributes)
    {
        RentalDto rental = rentalService.get(id);
        
        if(rental == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.rental.deleted");
        }
        
        rentalService.delete(rental);
        
        redirectAttributes.addFlashAttribute("msg", "msg.rental.deleted");
        
        return "rental-form";
    }
}
