/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.service;

import cz.muni.fi.pa165.carpark.dao.RentalDao;
import cz.muni.fi.pa165.carpark.dto.RentalDto;
import cz.muni.fi.pa165.carpark.dto.UserDto;
import cz.muni.fi.pa165.carpark.util.Converter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.springframework.dao.DataAccessException;

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

    @Transactional
    @Override
    public void create(RentalDto rental)
    {
        try
        {
            cz.muni.fi.pa165.carpark.entity.Rental entity = Converter.getEntity(rental);
            rentalDao.create(entity);
            rental.setId(entity.getId());
        } catch (IllegalArgumentException e)
        {
            throw new DataAccessException("Some error occured during creating rental entity.", e)
            {
            };
        } catch (PersistenceException e)
        {
            throw new DataAccessException("Some error occured during creating rental entity.", e)
            {
            };
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during creating rental entity.", e)
            {
            };
        }
    }

    @Transactional
    @Override
    public void edit(RentalDto rental)
    {
        try
        {
            rentalDao.edit(Converter.getEntity(rental));
        } catch (IllegalArgumentException | PersistenceException e)
        {
            throw new DataAccessException("Some error occured during editing rental entity.", e)
            {
            };
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during editing rental entity.", e)
            {
            };
        }
    }

    @Transactional
    @Override
    public void delete(RentalDto rental)
    {
        try
        {
            rentalDao.delete(Converter.getEntity(rental));
        } catch (IllegalArgumentException | PersistenceException e)
        {
            throw new DataAccessException("Some error occured during removing rental entity.", e)
            {
            };
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during removing rental entity.", e)
            {
            };
        }
    }

    @Transactional
    @Override
    public RentalDto get(Long id)
    {
        RentalDto rental;

        try
        {
            rental = Converter.getTransferObject(rentalDao.get(id));
        } catch (IllegalArgumentException | PersistenceException e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entity.", e)
            {
            };
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entity.", e)
            {
            };
        }
        return rental;
    }

    @Transactional
    @Override
    public List<RentalDto> getAll()
    {
        List<RentalDto> rentals = new ArrayList<>();

        try
        {
            for (cz.muni.fi.pa165.carpark.entity.Rental rental : rentalDao.getAll())
            {
                rentals.add(Converter.getTransferObject(rental));
            }
        } catch (IllegalArgumentException | PersistenceException e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e)
            {
            };
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e)
            {
            };
        }

        return rentals;
    }

    @Transactional
    @Override
    public List<RentalDto> getAllByUser(UserDto user)
    {
        List<RentalDto> rentals = new ArrayList<>();

        try
        {
            for (cz.muni.fi.pa165.carpark.entity.Rental rental : rentalDao.getAllByUser((user).createEntity()))
            {
                rentals.add(Converter.getTransferObject(rental));
            }
        } catch (IllegalArgumentException | PersistenceException e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e)
            {
            };
        } catch (Exception e)
        {
            throw new DataAccessException("Some error occured during retrieving rental entities.", e)
            {
            };
        }

        return rentals;
    }
}
