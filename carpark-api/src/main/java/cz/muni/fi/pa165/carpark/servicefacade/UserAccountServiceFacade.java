/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.servicefacade;

import cz.muni.fi.pa165.carpark.dto.UserCredentialsDto;

/**
 * User account service facade.
 *
 * @author Tomas Svoboda
 */
public interface UserAccountServiceFacade
{

    /**
     * Register new user.
     *
     * @param credentials credentials of the user.
     */
    void registerUser(UserCredentialsDto credentials);

    /**
     * Remove user account.
     *
     * @param credentials credentials of the user.
     */
    void removeUserAccount(UserCredentialsDto credentials);
    
}
