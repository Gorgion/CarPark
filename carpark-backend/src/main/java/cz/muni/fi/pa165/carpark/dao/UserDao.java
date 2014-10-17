/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.User;
import java.util.List;

/**
 * CRUD operations on User entity.
 *
 * @author Tomáš Vašíček
 */
public interface UserDao
{
    /**
     * Persist new user
     *
     * @param user user for persitance
     */
    void add(User user);

    /**
     * Get user by given id.
     *
     * @param id id of searched user
     * @return
     */
    User get(Long id);

    /**
     * Edit given user.
     *
     * @param user user for editing
     */
    void edit(User user);

    /**
     * Delete given user
     *
     * @param user user for deletion
     */
    void delete(User user);

    /**
     * Get all users
     *
     * @return List<User> of users
     */
    List<User> getAll();

    /**
     * Get all users with rent
     *
     * @return List<User>of users
     */
    List<User> getAllWithRent();

    /**
     * Get all users without rent
     *
     * @return List<User> of users
     */
    List<User> getAllWithoutRent();
}
