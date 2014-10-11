/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Rental;
import cz.muni.fi.pa165.carpark.entity.User;
import cz.muni.fi.pa165.carpark.exception.DataAccessException;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * Create, update, retrieve and delete operations on Rental entity.
 *
 * @author Tomas Svoboda
 */
public class RentalDaoImpl implements RentalDao
{
    private EntityManagerFactory emf;

    @Override
    public void setEmf(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    @Override
    public void create(Rental rental)
    {
        validateRental(rental);

        if (rental.getId() != null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is already created.");
        }

        try
        {
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();

            try
            {
                et.begin();

                em.persist(rental);

                et.commit();
            } catch (RollbackException e)
            {
                if (et != null && et.isActive())
                {
                    et.rollback();
                }

                throw new DataAccessException("Error while persisting rental entity.", e);
            } finally
            {
                if (em.isOpen())
                {
                    em.close();
                }
            }
        } catch (PersistenceException e)
        {
            throw new DataAccessException("Error while persisting rental entity.", e);
        }
    }

    @Override
    public List<Rental> getAll()
    {
        try
        {
            EntityManager em = emf.createEntityManager();

            try
            {
                Query query = em.createQuery("FROM Rental");
                List<Rental> rentals = query.getResultList();

                return Collections.unmodifiableList(rentals);

            } finally
            {
                if (em != null && em.isOpen())
                {
                    em.close();
                }
            }
        } catch (PersistenceException e)
        {
            throw new DataAccessException("Error while retrieving all rental entities.", e);
        }
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

        try
        {
            EntityManager em = emf.createEntityManager();

            try
            {

                Query query = em.createQuery("FROM Rental WHERE user=:user");
                query.setParameter("user", user);
                List<Rental> rentals = query.getResultList();

                return Collections.unmodifiableList(rentals);

            } finally
            {
                if (em != null && em.isOpen())
                {
                    em.close();
                }
            }
        } catch (PersistenceException e)
        {
            throw new DataAccessException("Error while retrieving some rental entities.", e);
        }
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

        try
        {
            EntityManager em = emf.createEntityManager();

            try
            {

                Rental rental = em.find(Rental.class, id);

                return rental;
            } finally
            {
                if (em != null && em.isOpen())
                {
                    em.close();
                }
            }
        } catch (PersistenceException e)
        {
            throw new DataAccessException("Error while retrieving rental entity.", e);
        }
    }

    @Override
    public void edit(Rental rental)
    {
        validateRental(rental);

        if (rental.getId() == null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is not created.");
        }

        try
        {
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();

            try
            {
                et.begin();

                em.merge(rental);

                et.commit();
            } catch (RollbackException e)
            {
                if (et != null && et.isActive())
                {
                    et.rollback();
                }

                throw new DataAccessException("Error while editing rental entity.", e);
            } finally
            {
                if (em.isOpen())
                {
                    em.close();
                }
            }
        } catch (PersistenceException e)
        {
            throw new DataAccessException("Error while editing rental entity.", e);
        }
    }

    @Override
    public void delete(Rental rental)
    {
        validateRental(rental);

        if (rental.getId() == null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is not created.");
        }

        try
        {
            EntityManager em = emf.createEntityManager();
            EntityTransaction et = em.getTransaction();

            try
            {
                et.begin();

                Rental rentalToDelete = em.find(Rental.class, rental.getId());
                em.remove(rentalToDelete);

                et.commit();
            } catch (RollbackException e)
            {
                if (et != null && et.isActive())
                {
                    et.rollback();
                }

                throw new DataAccessException("Error while removing rental entity.", e);
            } finally
            {
                if (em.isOpen())
                {
                    em.close();
                }
            }
        } catch (PersistenceException e)
        {
            throw new DataAccessException("Error while removing rental entity.", e);
        }
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
