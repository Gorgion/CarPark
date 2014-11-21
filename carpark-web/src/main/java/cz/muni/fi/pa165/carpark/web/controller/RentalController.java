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

    @Autowired
    private CarService carService;

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

        List<RentalDto> rentals = rentalService.getAllByUser(user);

        model.addAttribute("rentals", rentals);

        return "rental-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String createRentalStepZero(Model model)
    {
        model.addAttribute("rentalDate", new RentalDate());
        model.addAttribute("phase", 0);

        return "rental-form";
    }

    @RequestMapping(value = "/add", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String createRentalStepOne(@PathVariable Long userId, @Valid @ModelAttribute RentalDate rentalDate, BindingResult result, Model model)
    {
        if (result.hasErrors())
        {
//            model.addAttribute("phase", 0);
            return "rental-form";
        }
        Collection<CarDto> cars = carService.getFreeCars(rentalDate.from, rentalDate.to);

        model.addAttribute("cars", cars);
        model.addAttribute("phase", 1);

        return "rental-form";
    }

    @RequestMapping(value = "/add/1", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String createRentalStepTwo(@PathVariable Long userId, @Valid @ModelAttribute RentalForm rentalForm, BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors())
        {
            return "rental-form";
        }

        try
        {
            UserDto user = userService.get(userId);
            RentalDto rental = new RentalDto(rentalForm.getRentalDate().getFrom(), rentalForm.getRentalDate().getTo(), RentalDto.State.NEW, rentalForm.getCar(), user);

            rentalService.create(rental);
        } catch (CarAlreadyReserved e)
        {
            result.reject("error.rental.carAlreadyReserved");

            Collection<CarDto> cars = carService.getFreeCars(rentalForm.getRentalDate().getFrom(), rentalForm.getRentalDate().getTo());
            model.addAttribute("cars", cars);

            return "rental-form";
        }

        redirectAttributes.addFlashAttribute("msg", "msg.rental.created");

        return "redirect:/auth/user/" + userId + "/rental";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editRental(@PathVariable Long id, Model model)
    {
        RentalDto rental = rentalService.get(id);

        RentalState rentalState = new RentalState();
        rentalState.setState(rental.getRentalState());

        model.addAttribute("rentalState", rentalState);
        model.addAttribute("checked", rental.getRentalState());

        return "rental-state-form";
    }

    @RequestMapping(value = "/{id}/edit", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String rentalEdited(@PathVariable Long userId, @PathVariable Long id, @Valid @ModelAttribute RentalState rentalState, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if (result.hasErrors())
        {
            return "rental-state-form";
        }

        RentalDto originalRental = rentalService.get(id);

        originalRental.setRentalState(rentalState.getState());

        rentalService.edit(originalRental);

        redirectAttributes.addFlashAttribute("msg", "msg.rental.edited");

        return "redirect:/auth/user/" + userId + "/rental";
    }

//    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
//    public String deleteRental(@PathVariable Long id, Model model)
//    {
//        RentalDto rental = rentalService.get(id);
//        
//        model.addAttribute("rental", rental);
//        
//        return "rental-form";
//    }
    
    @RequestMapping(value = "/{id}/delete", method =
    {
        RequestMethod.POST, RequestMethod.DELETE
    })
    public String rentalDeleted(@PathVariable Long userId, @PathVariable Long id, /*@Valid @ModelAttribute RentalDto rental, BindingResult result,*/ RedirectAttributes redirectAttributes)
    {
        RentalDto rental = rentalService.get(id);

        if (rental == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.rental.deleted");
        }

        rentalService.delete(rental);

        redirectAttributes.addFlashAttribute("msg", "msg.rental.deleted");

        return "redirect:/auth/user/" + userId + "/rental";
    }

    @AfterDate(fromFieldName = "from", toFieldName = "to")
    public static class RentalDate
    {

        @NotNull
        @Future
        private Date from;

        @NotNull
        @Future
        private Date to;

        public RentalDate()
        {
        }

        public Date getFrom()
        {
            return from;
        }

        public void setFrom(Date from)
        {
            this.from = from;
        }

        public Date getTo()
        {
            return to;
        }

        public void setTo(Date to)
        {
            this.to = to;
        }

    }

    public static class RentalForm
    {

        @NotNull
        private RentalDate rentalDate;

        @NotNull
        private CarDto car;

        public RentalDate getRentalDate()
        {
            return rentalDate;
        }

        public void setRentalDate(RentalDate rentalDate)
        {
            this.rentalDate = rentalDate;
        }

        public CarDto getCar()
        {
            return car;
        }

        public void setCar(CarDto car)
        {
            this.car = car;
        }
    }

    public static class RentalState
    {

        private RentalDto.State state;

        public RentalDto.State getState()
        {
            return state;
        }

        public void setState(RentalDto.State state)
        {
            this.state = state;
        }
    }
}
