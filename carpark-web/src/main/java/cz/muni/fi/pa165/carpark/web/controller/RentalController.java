package cz.muni.fi.pa165.carpark.web.controller;

import cz.muni.fi.pa165.carpark.dto.CarDto;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.exception.CarAlreadyReserved;
import cz.muni.fi.pa165.carpark.service.CarService;
import cz.muni.fi.pa165.carpark.service.RentalService;
import cz.muni.fi.pa165.carpark.service.UserService;
import cz.muni.fi.pa165.carpark.validation.AfterDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
        model.addAttribute("rentalForm", new RentalForm());
        model.addAttribute("phase", 0);

        return "rental-form";
    }

    @RequestMapping(value = "/add", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String createRentalStepOne(@PathVariable Long userId, @Valid @ModelAttribute RentalForm rentalForm, final BindingResult result, Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("phase", 0);
            return "rental-form";
        }
        Collection<CarDto> cars = carService.getFreeCars(rentalForm.getFrom(), rentalForm.getTo());
        model.addAttribute("cars", cars);
        model.addAttribute("phase", 1);

        model.addAttribute("rentalForm", rentalForm);

        return "rental-form";
    }

    @RequestMapping(value = "/add/1", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String createRentalStepTwo(@PathVariable Long userId, @Valid @ModelAttribute RentalForm rentalForm, final BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {

        if (result.hasErrors())
        {
            model.addAttribute("phase", 1);
            return "rental-form";
        }

        CarDto car = carService.getCar(rentalForm.getCarId());
        try
        {
            UserDto user = userService.get(userId);

            RentalDto rental = new RentalDto(rentalForm.getFrom(), rentalForm.getTo(), RentalDto.State.NEW, /*rentalForm.getCar()*/ car, user);

            rentalService.create(rental);
        } catch (CarAlreadyReserved e)
        {
            model.addAttribute("error", "error.rental.carAlreadyReserved");

            model.addAttribute("phase", 1);

            Collection<CarDto> cars = carService.getFreeCars(rentalForm.getFrom(), rentalForm.getTo());
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

        if (rentalState.getState() == RentalDto.State.ACTIVE)
        {
            CarDto car = originalRental.getCar();
            car.setRented(true);
            carService.EditCar(car);
        }

        if (rentalState.getState() == RentalDto.State.FINISHED)
        {
            CarDto car = originalRental.getCar();
            car.setRented(false);
            carService.EditCar(car);
        }

        redirectAttributes.addFlashAttribute("msg", "msg.rental.edited");

        return "redirect:/auth/user/" + userId + "/rental";
    }

    @RequestMapping(value = "/{id}/delete", method =
    {
        RequestMethod.POST, RequestMethod.DELETE
    })
    public String rentalDeleted(@PathVariable Long userId, @PathVariable Long id, RedirectAttributes redirectAttributes)
    {
        RentalDto rental = rentalService.get(id);

        if (rental == null)
        {
            redirectAttributes.addFlashAttribute("error", "error.rental.deleted");
        }

        CarDto car = rental.getCar();

        if (car != null)
        {
            car.setRented(false);
            carService.EditCar(car);
        }
        rentalService.delete(rental);

        redirectAttributes.addFlashAttribute("msg", "msg.rental.deleted");

        return "redirect:/auth/user/" + userId + "/rental";
    }

    @AfterDate(fromFieldName = "from", toFieldName = "to")
    public static class RentalForm
    {

        private Long carId;

        @NotNull
        @Future
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date from;

        @NotNull
        @Future
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date to;

        public Long getCarId()
        {
            return carId;
        }

        public void setCarId(Long carId)
        {
            this.carId = carId;
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
