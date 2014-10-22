/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.RentalDao;
import cz.muni.fi.pa165.carpark.dto.Rental;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * CRUD service for rental entity.
 *
 * @author Tomas Svoboda
 */
@Named
public class RentalServiceImpl implements RentalService
{
    @Inject
    private RentalDao rentalDao;

    public void setRentalDao(RentalDao rentalDao)
    {
        this.rentalDao = rentalDao;
    }

    @Async
    @Transactional
    @Override
    public Future<Void> create(Rental rental)
    {
        try
        {
            cz.muni.fi.pa165.carpark.entity.Rental entity = Converter.getEntity(rental);
            rentalDao.create(entity);
            rental.setId(entity.getId());
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during creating rental entity.", e) {};
        }

        return new AsyncResult<>(null);
    }

    @Async
    @Transactional
    @Override
    public Future<Void> edit(Rental rental)
    {
        try
        {
            rentalDao.edit(Converter.getEntity(rental));
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during editing rental entity.", e) {};
        }

        return new AsyncResult<>(null);
    }

    @Async
    @Transactional
    @Override
    public Future<Void> delete(Rental rental)
    {
        try
        {
            rentalDao.delete(Converter.getEntity(rental));
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during removing rental entity.", e) {};
        }
        return new AsyncResult<>(null);
    }

    @Async
    @Transactional
    @Override
    public Future<Rental> get(Long id)
    {
        Rental rental;

        try
        {
            rental = Converter.getTransferObject(rentalDao.get(id));
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entity.", e) {};
        }

        return new AsyncResult<>(rental);
    }

    @Async
    @Transactional
    @Override
    public Future<List<Rental>> getAll()
    {
        List<Rental> rentals = new ArrayList<>();

        try
        {
            for (cz.muni.fi.pa165.carpark.entity.Rental rental : rentalDao.getAll())
            {
                rentals.add(Converter.getTransferObject(rental));
            }
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e) {};
        }

        return new AsyncResult<>(rentals);
    }

    @Async
    @Transactional
    @Override
    public Future<List<Rental>> getAllByUser(UserDto user)
    {
        List<Rental> rentals = new ArrayList<>();

        try
        {
            for (cz.muni.fi.pa165.carpark.entity.Rental rental : rentalDao.getAllByUser((user).createEntity()))
            {
                rentals.add(Converter.getTransferObject(rental));
            }
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e) {};
        }

        return new AsyncResult<>(rentals);
    }
}
