/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 * Office entity with operations add, get, edit, delete. It also finds all
 * offices, office cars and office employees.
 *
 * @author Karolina Burska
 */

@Repository
public class OfficeDaoImpl implements OfficeDao {

    @PersistenceContext//(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    @Override
    public void addOffice(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        if (office.getID() != null) {
            throw new IllegalArgumentException("Office is already added.");
        }

        em.persist(office);
    }

    @Override
    public Office getOffice(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID is less than 0.");
        }

        Office office = em.find(Office.class, id);

        return office;
    }

    @Override
    public void editOffice(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }

        em.merge(office);
    }

    @Override
    public void deleteOffice(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }

        Office toDelete = em.find(Office.class, office.getID());
        em.remove(toDelete);
    }

    @Override
    public List<Office> getAllOffices() {
        Query query = em.createQuery("SELECT o FROM Office o",Office.class);
        List<Office> offices = query.getResultList();

        return Collections.unmodifiableList(offices);
    }

    @Override
    public List<Car> getOfficeCars(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        
        List<Car> cars = em.createQuery("SELECT o.cars FROM Office o WHERE o.iD = :officeid")
                    .setParameter("officeid", office.getID())
                    .getResultList();
        
        
        return Collections.unmodifiableList(cars);
    }

    @Override
    public void addCarToOffice(Office office, Car car) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        if (car == null) {
            throw new IllegalArgumentException("Car is null.");
        }

        car = em.find(Car.class, car.getID());//merge(car);

        List<Car> actualCars = getOffice(office.getID()).getCars();
        if(actualCars == null)
            actualCars = new ArrayList<Car>();
        actualCars.add(car);
        office.setCars(actualCars);

        em.merge(office);
    }

    @Override
    public void deleteCarFromOffice(Office office, Car car) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        if (car == null) {
            throw new IllegalArgumentException("Car is null.");
        }

        List<Car> actualCars = getOffice(office.getID()).getCars();
        actualCars.remove(car);
        office.setCars(actualCars);

        em.merge(office);
    }

    @Override
    public List<User> getEmployees(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }

        List<User> users = em.createQuery("SELECT o.employees FROM Office o WHERE o.id =:officeId")
                    .setParameter("officeId", office.getID())
                    .getResultList();

        return Collections.unmodifiableList(users);
    }

    @Override
    public void addEmployeeToOffice(Office office, User user) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        user = em.find(User.class,user.getId());

        List<User> actualEmployees = getOffice(office.getID()).getEmployees();
        if (actualEmployees == null)
            actualEmployees = new ArrayList<User>();
        
        
        actualEmployees.add(user);
        office.setEmployees(actualEmployees);

        em.merge(office);
    }

    @Override
    public void deleteEmployeeFromOffice(Office office, User user) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        List<User> employees = getOffice(office.getID()).getEmployees();
        employees.remove(user);
        office.setEmployees(employees);

        em.merge(office);
    }
}
