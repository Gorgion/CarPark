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

    @Transactional
    void create(UserCredentialsDto credentials);

    @Transactional
    void delete(UserCredentialsDto credentials);

    @Transactional
    UserCredentialsDto getByUsername(String username);

    @Transactional
    void update(UserCredentialsDto credentials);

}
