/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;
import javax.transaction.Transactional;

/**
 * Service for user credentials.
 *
 * @author Tomas Svoboda
 */
public interface UserCredentialsService
{

    /**
     * Create user credentials.
     *
     * @param credentials user credentials.
     */
    void create(UserCredentialsDto credentials);

    /**
     * Remove user credentials.
     *
     * @param credentials user credentials
     */
    void delete(UserCredentialsDto credentials);

    /**
     * Retrieve user credentials for given username.
     *
     * @param username username we want to search user credentials for
     * @return user credentials
     */
    UserCredentialsDto getByUsername(String username);

    /**
     * Update user credentials object.
     *
     * @param credentials user credentials
     */
    void update(UserCredentialsDto credentials);

}
