/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.UserCredentialsDao;
import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;
import cz.muni.fi.pa165.carpark.util.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author Tomas Svoboda
 */
@Named
public class UserCredentialsServiceImpl implements UserCredentialsService
{

    @Inject
    private UserCredentialsDao credentialsDao;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void create(UserCredentialsDto credentials)
    {
        cz.muni.fi.pa165.carpark.entity.UserCredentials entity = Converter.getEntity(credentials);
        credentialsDao.create(entity);
        credentials.setUserId(entity.getUserId());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_BUILT_IN_ADMIN'")
    @Transactional
    @Override
    public void delete(UserCredentialsDto credentials)
    {
        credentialsDao.delete(Converter.getEntity(credentials));
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public void update(UserCredentialsDto credentials)
    {
        credentialsDao.update(Converter.getEntity(credentials));
    }

    @Transactional
    @Override
    public UserCredentialsDto getByUsername(String username)
    {
        cz.muni.fi.pa165.carpark.entity.UserCredentials credentialsEntity;

        credentialsEntity = credentialsDao.getByUsername(username);

        UserCredentialsDto credentials = Converter.getTransferObject(credentialsEntity);

        return credentials;
    }
    
    @PreAuthorize("isAuthenticated()")
    @Transactional
    @Override
    public UserCredentialsDto get(Long id)
    {
        cz.muni.fi.pa165.carpark.entity.UserCredentials credentialsEntity;

        credentialsEntity = credentialsDao.get(id);

        UserCredentialsDto credentials = Converter.getTransferObject(credentialsEntity);

        return credentials;
    }
}
