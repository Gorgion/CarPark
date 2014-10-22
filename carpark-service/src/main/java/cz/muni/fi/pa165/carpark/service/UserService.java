/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dto.UserDto;
import java.util.List;

/**
 *
 * @author Tomáš Vašíček
 */
public interface UserService {

    /**
     * Persist new user
     *
     * @param user user for persitance
     */
    void add(UserDto user);

    /**
     * Get user by given id.
     *
     * @param id id of searched user
     * @return
     */
    UserDto get(Long id);

    /**
     * Edit given user.
     *
     * @param user user for editing
     */
    void edit(UserDto user);

    /**
     * Delete given user
     *
     * @param user user for deletion
     */
    void delete(UserDto user);

    /**
     * Get all users
     *
     * @return List<UserDto> of users
     */
    List<UserDto> getAll();

    /**
     * Get all users with rent
     *
     * @return List<UserDto>of users
     */
    List<UserDto> getAllWithRent();

    /**
     * Get all users without rent
     *
     * @return List<UserDto> of users
     */
    List<UserDto> getAllWithoutRent();

}
