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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Create, retrieve and delete operations on Rental entity.
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
        if(rental.getId() != null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is already created.");
        }
        
        validateRental(rental);
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(rental);

        em.getTransaction().commit();
        em.close();        
    }
    
    @Override
    public List<Rental> getAll()
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("FROM Rental");
        List<Rental> rentals = query.getResultList();

        em.getTransaction().commit();
        em.close();        
        
        return Collections.unmodifiableList(rentals);
    }
    
    @Override
    public List<Rental> getAllByUser(User user)
    {
        if(user.getId() == null)
        {
            throw new IllegalArgumentException("User id is null.");
        }
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createQuery("FROM Rental WHERE user=:user");
        query.setParameter("user", user);
        List<Rental> rentals = query.getResultList();

        em.getTransaction().commit();
        em.close();        
        
        return Collections.unmodifiableList(rentals);
    }
    
    @Override
    public Rental get(Long id)
    {
        if(id == null) {
            throw new IllegalArgumentException("ID is NULL");
        }
        if(id < 0) {
            throw new IllegalArgumentException("ID is less than 0");
        }
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Rental rental = em.find(Rental.class, id);

        em.getTransaction().commit();
        em.close();
        
        return rental;
    }
    
    @Override
    public void delete(Rental rental)
    {
        if(rental.getId() == null)
        {
            throw new IllegalArgumentException("Rental '" + rental + "' is not created.");
        }
        
        validateRental(rental);
        
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Rental rentalToDelete = em.find(Rental.class, rental.getId());
        em.remove(rentalToDelete);

        em.getTransaction().commit();
        em.close();        
    }
    
    private static void validateRental(Rental rental)
    {
        if(rental.getFromDate().after(rental.getToDate()))
        {
            throw new IllegalArgumentException("From date is after to date in entity '" + rental + "'");
        }
    }
}
