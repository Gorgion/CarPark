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
import org.springframework.format.annotation.DateTimeFormat;
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

//    @Autowired
//    private ConversionService conversionService;
//    @InitBinder
//    protected void initBinder(ServletRequestDataBinder binder) {
//        binder.setConversionService(conversionService);
//    }    
    
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
    public String createRentalStepOne(@PathVariable Long userId, @Valid @ModelAttribute /*RentalDate rentalDate*/ RentalForm rentalForm, final BindingResult result, Model model)
    {
        if (result.hasErrors())
        {
            model.addAttribute("phase", 0);
            return "rental-form";
        }
        Collection<CarDto> cars = carService./*getAllCars();//*/getFreeCars(rentalForm.getFrom(), rentalForm.getTo());//rentalDate.from, rentalDate.to);
        System.out.println("\n\n\nFREE------------------" + cars + "\n----------");
        System.out.println("\n\n\nRented------------------" + carService.getRentedCars() + "\n----------");
        System.out.println("\n\n\nAll------------------" + carService.getAllCars() + "\n----------");
        model.addAttribute("cars", cars);
        model.addAttribute("phase", 1);
        
//        RentalForm rf = new RentalForm();
//        rf.setRentalDate(rentalDate);
//        rf.setFrom(rentalDate.getFrom());
//        rf.setTo(rentalDate.getTo());
        
//        model.addAttribute("rentalDate", rentalDate);
        model.addAttribute("rentalForm", rentalForm);

        return "rental-form";
    }

    @RequestMapping(value = "/add/1", method =
    {
        RequestMethod.POST, RequestMethod.PUT
    })
    public String createRentalStepTwo(@PathVariable Long userId, /*@Valid*/ @ModelAttribute RentalForm rentalForm, final BindingResult result, Model model, RedirectAttributes redirectAttributes)
    {
        
        System.out.println("\n#1");
        if (result.hasErrors())
        {
         System.out.println("\n#2-------\n" + result.getAllErrors());   
//         RentalDate rentalDate = new RentalDate();
//         rentalDate.setFrom(rentalForm.from);
//         rentalDate.setTo(rentalForm.getTo());
//         model.addAttribute("rentalDate", rentalDate);
            model.addAttribute("phase", 1);
            return "rental-form";
        }
//System.out.println("\n#3\n"+rentalForm.from  + "\n" + rentalForm.to+ "\n"+rentalForm.carId+"\n--------");
        CarDto car = carService.getCar(rentalForm.getCarId());
        try
        {
            UserDto user = userService.get(userId);
//            RentalDto rental = new RentalDto(rentalForm.getRentalDate().getFrom(), rentalForm.getRentalDate().getTo(), RentalDto.State.NEW, rentalForm.getCar(), user);
            RentalDto rental = new RentalDto(rentalForm.getFrom(), rentalForm.getTo(), RentalDto.State.NEW, /*rentalForm.getCar()*/car, user);

//            System.out.println("\n#4");
            rentalService.create(rental);
//            System.out.println("\n#5");
        } catch (CarAlreadyReserved e)
        {
//            System.out.println("\n#6");
//            result.reject("error.rental.carAlreadyReserved");
            model.addAttribute("error.rental.carAlreadyReserved");
//System.out.println("\nERR-------\n" + result.getAllErrors());   

//         RentalDate rentalDate = new RentalDate();
//         rentalDate.setFrom(rentalForm.from);
//         rentalDate.setTo(rentalForm.getTo());
//         model.addAttribute("rentalDate", rentalDate);
         
            model.addAttribute("phase", 1);
//            Collection<CarDto> cars = carService.getFreeCars(rentalForm.getRentalDate().getFrom(), rentalForm.getRentalDate().getTo());
            Collection<CarDto> cars = carService.getAllCars();//FreeCars(rentalForm.getFrom(), rentalForm.getTo());
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

//    @AfterDate(fromFieldName = "from", toFieldName = "to")
//    public static class RentalDate
//    {
//
//        @NotNull
//        @Future
//        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//        private Date from;
//
//        @NotNull
//        @Future
//        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//        private Date to;
//
//        public RentalDate()
//        {
//        }
//
//        public Date getFrom()
//        {
//            return from;
//        }
//
//        public void setFrom(Date from)
//        {
//            this.from = from;
//        }
//
//        public Date getTo()
//        {
//            return to;
//        }
//
//        public void setTo(Date to)
//        {
//            this.to = to;
//        }
//
//    }

    @AfterDate(fromFieldName = "from", toFieldName = "to")
    public static class RentalForm
    {

//        @NotNull
//        private RentalDate rentalDate;

//        @NotNull
        private Long carId;

        public Long getCarId()
        {
            return carId;
        }

        public void setCarId(Long carId)
        {
            this.carId = carId;
        }
        
        @NotNull
        @Future
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date from;

        @NotNull
        @Future
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private Date to;
        
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
        
//        @NotNull
//        private CarDto car;
//
//        public RentalDate getRentalDate()
//        {
//            return rentalDate;
//        }
//
//        public void setRentalDate(RentalDate rentalDate)
//        {
//            this.rentalDate = rentalDate;
//        }
//
//        public CarDto getCar()
//        {
//            return car;
//        }
//
//        public void setCar(CarDto car)
//        {
//            this.car = car;
//        }
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
