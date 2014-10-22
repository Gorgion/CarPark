/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.UserCredentials;
import java.util.concurrent.Future;
import javax.transaction.Transactional;
import org.springframework.scheduling.annotation.Async;

/**
 * Service for user credentials.
 *
 * @author Tomas Svoboda
 */
public interface UserCredentialsService
{

    @Transactional
    @Async
    Future<Void> create(UserCredentials credentials);

    @Transactional
    @Async
    Future<Void> delete(UserCredentials credentials);

    @Transactional
    @Async
    Future<UserCredentials> getByUsername(String username);

    @Transactional
    @Async
    Future<Void> update(UserCredentials credentials);
    
}
