/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * CRUD operations on User entity.
 * 
 * @author Tomáš Vašíček
 */
public class UserDaoImpl implements UserDao {
    
    //@PersistenceContext
    //private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;
    
    @Override
    public void setEmf(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    @Override
    public void add(User user) {
        if (user == null) {
            throw new NullPointerException("User can not be NULL");
        }
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
        finally{
            entityManager.close();
        }        
    }

    @Override
    public User get(Long id) {
        if (id == null){
            throw new NullPointerException("ID can not be NULL");
        }
        if(id < 0) {
            throw new IllegalArgumentException("ID is less than 0");
        }
        
        User user = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();     
        try{
            user = entityManager.find(User.class, id);
        }
        catch (PersistenceException ex){
            ex.printStackTrace();
        }
        finally{
            entityManager.close();
        } 
        
//        if (user == null) {
//            throw new IllegalArgumentException("User is not found");
//        }
        return user;
    }

    @Override
    public void edit(User user) {
        if (user == null) {
            throw new NullPointerException("User can not be NULL");
        }
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        }
        catch(PersistenceException ex){
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            ex.printStackTrace();
        }
        finally{
            entityManager.close();
        } 
    }

    @Override
    public void delete(User user) {
        if (user == null) {
            throw new NullPointerException("User can not be NULL");
        }
        
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try{
            User userToDelete = get(user.getId());
            //User userToDelete = entityManager.find(User.class, user.getId());
            
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(userToDelete));
            entityManager.getTransaction().commit();
        }
        catch(PersistenceException ex){
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
        finally{
            entityManager.close();
        } 
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        Query query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);;
        List<User> users = query.getResultList();
        entityManager.close();
        
        return users;
    }

    @Override
    public List<User> getAllWithRent() {    
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        //TODO edit naive version
        Query query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id IN (SELECT DISTINCT r.user FROM Rental r)", User.class);
                //"SELECT u FROM User u WHERE u.id IN (SELECT r.user, r.rentalState from Rental r WHERE r.rentalState := state", User.class);
        //query.setParameter("state", State.APPROVED);
        List<User> users = query.getResultList();
        entityManager.close();
        
        return users;
    }

    @Override
    public List<User> getAllWithoutRent() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        
        //TODO edit naive version
        Query query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id NOT IN (SELECT DISTINCT r.user FROM Rental r)", User.class);
                //"SELECT u FROM User u WHERE u.id NOT IN (SELECT r.user, r.rentalState from Rental r WHERE r.rentalState := state", User.class);
        //query.setParameter("state", State.APPROVED);
        List<User> users = query.getResultList();
        entityManager.close();
        
        return users;
    }
    
}
