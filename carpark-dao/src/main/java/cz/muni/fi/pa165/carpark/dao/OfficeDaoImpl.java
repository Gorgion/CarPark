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
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
//import javax.transaction.Transactional;
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
            throw new IllegalArgumentException("Office is null.");
        }
        if (office.getID() != null) {
            throw new IllegalArgumentException("Office is already added.");
        }

        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(office);
            em.getTransaction().commit();
        }
        catch(PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        finally {
            em.close();
        }
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

        Office office = null;
        
        try {
            office = (Office)em.createQuery("SELECT o FROM Office o WHERE o.id =:ide").setParameter("ide", id).getSingleResult();
        }
        catch(PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        finally {
            em.close();
        }
        
        return office;
    }

    @Override
    public void editOffice(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }

        EntityManager em = emf.createEntityManager();

        try {
        em.getTransaction().begin();
        em.merge(office);
        em.getTransaction().commit();
        }
        catch(PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        em.close();
    }

    @Override
    public void deleteOffice(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            
            em.remove(em.find(Office.class, office.getID()));
            
            em.getTransaction().commit();
        }
        catch(PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        finally {
            em.close();
        }
    }

    @Override
    public List<Office> getAllOffices() {
        EntityManager em = emf.createEntityManager();

        List<Office> offices  = new ArrayList<>();
        
        try {
            offices = (List<Office>)em.createQuery("FROM Office").getResultList();
        }
        catch(PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        finally {
            em.close();
        }

        return Collections.unmodifiableList(offices);
    }

    @Override
    public List<Car> getOfficeCars(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        EntityManager em = emf.createEntityManager();
        
        List<Car> cars = new ArrayList<>();
        
        try {
            cars = em.createQuery("SELECT o.cars FROM Office o WHERE o.iD = :officeid")
                    .setParameter("officeid", office.getID())
                    .getResultList();
        }
        catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }  
        }
        finally {
            em.close();
        }
        
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

        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();

            car = em.merge(car);
        
            List<Car> actualCars = getOffice(office.getID()).getCars();
            actualCars.add(car);
            office.setCars(actualCars);

            em.merge(office);
            em.getTransaction().commit();
            
        }
        catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }  
        }
        finally {
            em.close();
        }
    }

    @Override
    public void deleteCarFromOffice(Office office, Car car) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        if (car == null) {
            throw new IllegalArgumentException("Car is null.");
        }

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
        
            List<Car> actualCars = getOffice(office.getID()).getCars();
            actualCars.remove(car);
            office.setCars(actualCars);

            em.merge(office);
            em.getTransaction().commit();  
        }
        catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        finally {
            em.close();
        }
    }

    @Override
    public List<User> getEmployees(Office office) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }

        EntityManager em = emf.createEntityManager();
        
        List<User> users = new ArrayList<>();
        
        try {
            users = em.createQuery("SELECT o.employees FROM Office o WHERE o.id =:officeId")
                    .setParameter("officeId", office.getID())
                    .getResultList();

        }
        catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }  
        }
        finally {
            em.close();
        }
        
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

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            user = em.merge(user);
        
            List<User> actualEmployees = getOffice(office.getID()).getEmployees();
            actualEmployees.add(user);
            office.setEmployees(actualEmployees);

            em.merge(office);
            em.getTransaction().commit(); 
        }
        catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }  
        }
        finally {
            em.close();
        }
    }

    @Override
    public void deleteEmployeeFromOffice(Office office, User user) {
        if (office == null) {
            throw new IllegalArgumentException("Office is null.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User is null.");
        }

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
        
            List<User> employees = getOffice(office.getID()).getEmployees();
            employees.remove(user);
            office.setEmployees(employees);

            em.merge(office);
            em.getTransaction().commit();  
        }
        catch (PersistenceException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        finally {
            em.close();
        }
    }
}
