/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 * CRUD operations on User entity.
 *
 * @author Tomáš Vašíček
 */
@Repository
public class UserDaoImpl implements UserDao
{

    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Override
    public Long add(User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException("User can not be NULL");
        }
        
        entityManager.persist(user);   
        entityManager.flush();
        return user.getId();
    }

    @Override
    public User get(Long id)
    {
        if (id == null)
        {
            throw new IllegalArgumentException("ID can not be NULL");
        }
        if (id < 0)
        {
            throw new IllegalArgumentException("ID is less than 0");
        }

        User user = entityManager.find(User.class, id);

        return user;
    }

    @Override
    public void edit(User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException("User can not be NULL");
        }

        entityManager.merge(user);
    }

    @Override
    public void delete(User user)
    {
        if (user == null)
        {
            throw new IllegalArgumentException("User can not be NULL");
        }

        User userToDelete = entityManager.find(User.class, user.getId());

        entityManager.remove(userToDelete);
    }

    @Override
    public List<User> getAll()
    {

        Query query = entityManager.createQuery(
                "SELECT u FROM User u", User.class);
        List<User> users = query.getResultList();

        return users;
    }

    @Override
    public List<User> getAllWithRent()
    {
        Query query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id IN (SELECT DISTINCT r.user FROM Rental r)", User.class);
        List<User> users = query.getResultList();

        return users;
    }

    @Override
    public List<User> getAllWithoutRent()
    {
        Query query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.id NOT IN (SELECT DISTINCT r.user FROM Rental r)", User.class);
        List<User> users = query.getResultList();

        return users;
    }

}
