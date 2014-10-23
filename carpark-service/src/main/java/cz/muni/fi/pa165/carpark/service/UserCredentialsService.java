/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.UserCredentials;
import javax.transaction.Transactional;

/**
 * Service for user credentials.
 *
 * @author Tomas Svoboda
 */
public interface UserCredentialsService
{

    @Transactional
    void create(UserCredentials credentials);

    @Transactional
    void delete(UserCredentials credentials);

    @Transactional
    UserCredentials getByUsername(String username);

    @Transactional
    void update(UserCredentials credentials);

}
