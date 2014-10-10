/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Car;
import cz.muni.fi.pa165.carpark.entity.Office;
import cz.muni.fi.pa165.carpark.entity.User;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Office entity with operations add, get, edit, delete. It also finds all
 * offices, office cars and office employees.
 *
 * @author Karolina Burska
 */
public class OfficeDaoImpl implements OfficeDao {

    private EntityManagerFactory emf;

    @Override
    public void setEMF(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void addOffice(Office office) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }
        if (office.getID() != null) {
            throw new IllegalArgumentException("Office is already added.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(office);
        em.getTransaction().commit();
        em.close();
        System.out.println(office);
    }

    @Override
    public Office getOffice(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID is null.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID is less than 0.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Office office = em.find(Office.class, id);

        em.getTransaction().commit();
        em.close();

        return office;
    }

    @Override
    public void editOffice(Office office) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(office);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteOffice(Office office) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Office.class, office.getID()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<Office> getAllOffices() {
        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("FROM Office");
        List<Office> offices = query.getResultList();

        em.close();

        return Collections.unmodifiableList(offices);
    }

    @Override
    public List<Car> getOfficeCars(Office office) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }

        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT * FROM Car WHERE office=:office");

        List<Car> cars = query.getResultList();

        return Collections.unmodifiableList(cars);
    }

    @Override
    public void addCarToOffice(Office office, Car car) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }
        if (car == null) {
            throw new NullPointerException("Car is null.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<Car> actualCars = getOffice(office.getID()).getCars();
        actualCars.add(car);
        office.setCars(actualCars);

        em.merge(office);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteCarFromOffice(Office office, Car car) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }
        if (car == null) {
            throw new NullPointerException("Car is null.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<Car> actualCars = getOffice(office.getID()).getCars();
        actualCars.remove(car);
        office.setCars(actualCars);

        em.merge(office);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public List<User> getEmployees(Office office) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }

        EntityManager em = emf.createEntityManager();

        Query query = em.createQuery("SELECT * FROM User WHERE office=:office");
        List<User> users = query.getResultList();

        return Collections.unmodifiableList(users);
    }

    @Override
    public void addEmployeeToOffice(Office office, User user) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }
        if (user == null) {
            throw new NullPointerException("User is null.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<User> actualEmployees = getOffice(office.getID()).getEmployees();
        actualEmployees.add(user);
        office.setEmployees(actualEmployees);

        em.merge(office);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void deleteEmployeeFromOffice(Office office, User user) {
        if (office == null) {
            throw new NullPointerException("Office is null.");
        }
        if (user == null) {
            throw new NullPointerException("User is null.");
        }

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        List<User> actualEmployees = getOffice(office.getID()).getEmployees();
        actualEmployees.remove(user);
        office.setEmployees(actualEmployees);

        em.merge(office);
        em.getTransaction().commit();
        em.close();
    }
}
