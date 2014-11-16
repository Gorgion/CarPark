/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.UserCredentials;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * UserCredentials dao implementation.
 *
 * @author Tomas Svoboda
 */
@Named
public class UserCredentialsDaoImpl implements UserCredentialsDao
{
    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Override
    public void create(UserCredentials uc)
    {
        validateUserCredentials(uc);

        if (uc.getUserId() != null)
        {
            throw new IllegalArgumentException("user id is not null");
        }

        em.persist(uc);
    }

    @Override
    public void update(UserCredentials uc)
    {
        validateUserCredentials(uc);

        if (uc.getUserId() == null)
        {
            throw new IllegalArgumentException("user id is null");
        }

        em.merge(uc);
    }

    @Override
    public void delete(UserCredentials uc)
    {
        validateUserCredentials(uc);

        if (uc.getUserId() == null)
        {
            throw new IllegalArgumentException("user id is null");
        }

        em.remove(em.find(UserCredentials.class, uc.getUserId()));
    }

    @Override
    public UserCredentials getByUsername(String username)
    {
        if (username == null)
        {
            throw new IllegalArgumentException("username is null");
        }

        Query query = em.createQuery("SELECT uc FROM UserCredentials uc WHERE uc.username = :username", UserCredentials.class).setParameter("username", username);

        UserCredentials credentials = null;
        try
        {
            credentials = (UserCredentials) query.getSingleResult();
        } catch (NoResultException e)
        {

        }

        return credentials;
    }

    private void validateUserCredentials(UserCredentials uc)
    {
        if (uc == null)
        {
            throw new IllegalArgumentException("user credentials are null");
        }

        if (uc.getUsername() == null || uc.getUsername().trim().isEmpty())
        {
            throw new IllegalArgumentException("username is null or empty");
        }

        if (uc.getUser() == null)
        {
            throw new IllegalArgumentException("user is null");
        }

        if (uc.getPassword() == null || uc.getPassword().trim().isEmpty())
        {
            throw new IllegalArgumentException("password is null or empty");
        }

        if (uc.getRoles() == null)
        {
            throw new IllegalArgumentException("roles are null");
        }
    }
}
