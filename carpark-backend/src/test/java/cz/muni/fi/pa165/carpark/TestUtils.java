/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.carpark;

import cz.muni.fi.pa165.carpark.entity.Address;
import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.Rental.State;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.entity.User.Position;
import java.util.Date;

/**
 *
 * @author Tomáš Vašíček
 */
public class TestUtils {
    public static Rental createRental(Car car, State rentalState, User user, Date fromDate, Date toDate) {
        Rental rental = new Rental();
        
        rental.setCar(car);
        rental.setRentalState(rentalState);
        rental.setUser(user);
        rental.setFromDate(fromDate);
        rental.setFromDate(toDate);

        return rental;
    }
    
    public static User createUser(String firstName, String LastName, Address address, Position position, String birthNumber) {
        User user = new User();
        
        user.setFirstName(firstName);
        user.setLastName(LastName);
        user.setAddress(address);
        user.setPosition(position);
        user.setBirthNumber(birthNumber);

        return user;
    }
    
    public static Date dateNow(Long more) {
        return new Date(new Date().getTime() + more);
    }
}
