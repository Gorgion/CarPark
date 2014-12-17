/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.UserCredentials;

/**
 * UserCredentials dao implementation.
 *
 * @author Tomas Svoboda
 */
public interface UserCredentialsDao
{

    /**
     * Create user credentials.
     *
     * @param uc user credentials
     */
    void create(UserCredentials uc);

    /**
     * remove user credentials.
     *
     * @param uc user credentials
     */
    void delete(UserCredentials uc);

    /**
     * Retrieve user credentials.
     *
     * @param username user credentials for given username.
     * @return found user credentials
     */
    UserCredentials getByUsername(String username);
    
    /**
     * Retrieve user credentials.
     *
     * @param id user credentials for given id.
     * @return found user credentials
     */
    UserCredentials get(Long id);

    /**
     * Update user credentials.
     *
     * @param uc user credentials.
     */
    void update(UserCredentials uc);
    
}
