/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.UserCredentialsDao;
import cz.muni.fi.pa165.carpark.dto.UserCredentials;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 *
 * @author Tomas Svoboda
 */
@Named
public class UserCredentialsServiceImpl implements UserCredentialsService
{
    @Inject
    private UserCredentialsDao credentialsDao;

    @Transactional
    @Async
    @Override
    public Future<Void> create(UserCredentials credentials)
    {
        try
        {
            cz.muni.fi.pa165.carpark.entity.UserCredentials entity = Converter.getEntity(credentials);
            credentialsDao.create(entity);
            credentials.setUserId(entity.getUserId());
        } catch (Exception e)
        {
            throw new DataAccessException("Error occured during creating new user credentials entity.", e)
            {
            };
        }

        return new AsyncResult<>(null);
    }

    @Transactional
    @Async
    @Override
    public Future<Void> delete(UserCredentials credentials)
    {
        try
        {
            credentialsDao.delete(Converter.getEntity(credentials));
        } catch (Exception e)
        {
            throw new DataAccessException("Error occured during removing user credentials entity.", e)
            {
            };
        }

        return new AsyncResult<>(null);
    }

    @Transactional
    @Async
    @Override
    public Future<Void> update(UserCredentials credentials)
    {
        try
        {
            credentialsDao.update(Converter.getEntity(credentials));
        } catch (Exception e)
        {
            throw new DataAccessException("Error occured during updating user credentials entity.", e)
            {
            };
        }

        return new AsyncResult<>(null);
    }

    @Transactional
    @Async
    @Override
    public Future<UserCredentials> getByUsername(String username)
    {
        cz.muni.fi.pa165.carpark.entity.UserCredentials credentialsEntity;
        try
        {
            credentialsEntity = credentialsDao.getByUsername(username);
        } catch (Exception e)
        {
            throw new DataAccessException("Error occured during retrieving user credentials entity.", e)
            {
            };
        }

        UserCredentials credentials = Converter.getTransferObject(credentialsEntity);

        return new AsyncResult<>(credentials);
    }
}
