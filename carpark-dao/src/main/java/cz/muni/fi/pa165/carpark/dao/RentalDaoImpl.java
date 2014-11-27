/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 * Create, update, retrieve and delete operations on Rental entity.
 *
 * @author Tomas Svoboda
 */
@Repository
public class RentalDaoImpl implements RentalDao
{
    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public void create(Rental rental)
    {
        validateRental(rental);

        if (rental.getId() != null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is already created.");
        }

        entityManager.persist(rental);

    }

    @Override
    public List<Rental> getAll()
    {

        Query query = entityManager.createQuery("SELECT r FROM Rental r",Rental.class);
        List<Rental> rentals = query.getResultList();

        return Collections.unmodifiableList(rentals);
    }

    @Override
    public List<Rental> getAllByUser(User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException("User is null.");
        }

        if (user.getId() == null)
        {
            throw new IllegalArgumentException("User id is null.");
        }

        Query query = entityManager.createQuery("SELECT r FROM Rental r WHERE r.user=:user ORDER BY r.fromDate DESC", Rental.class);
        query.setParameter("user", user);
        List<Rental> rentals = query.getResultList();

        return Collections.unmodifiableList(rentals);
    }

    @Override
    public Rental get(Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("ID is NULL");
        }
        if (id <= 0)
        {
            throw new IllegalArgumentException("ID is less than or eq to 0");
        }

        Rental rental = entityManager.find(Rental.class, id);

        return rental;

    }

    @Override
    public void edit(Rental rental)
    {
        validateRental(rental);

        if (rental.getId() == null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is not created.");
        }

        entityManager.merge(rental);
    }

    @Override
    public void delete(Rental rental)
    {
        validateRental(rental);

        if (rental.getId() == null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is not created.");
        }

        Rental rentalToDelete = entityManager.find(Rental.class, rental.getId());
        entityManager.remove(rentalToDelete);
    }

    private static void validateRental(Rental rental)
    {
        if (rental == null)
        {
            throw new IllegalArgumentException("Rental is null");
        }

        if (rental.getFromDate() == null)
        {
            throw new IllegalArgumentException("Rental from date is null");
        }

        if (rental.getToDate() == null)
        {
            throw new IllegalArgumentException("Rental to date is null");
        }

        if (rental.getCar() == null)
        {
            throw new IllegalArgumentException("Rented car is null");
        }

        if (rental.getUser() == null)
        {
            throw new IllegalArgumentException("Rental user is null");
        }

        if (rental.getRentalState() == null)
        {
            throw new IllegalArgumentException("Rental state is null");
        }

        if (rental.getFromDate().after(rental.getToDate()))
        {
            throw new IllegalArgumentException("From date is after to date in entity '" + rental + "'");
        }
    }
}
