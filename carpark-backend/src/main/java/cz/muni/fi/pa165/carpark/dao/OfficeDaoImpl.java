/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Office;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Office entity with operations add, get, edit, delete.
 * It also finds all offices, office cars and office employees.
 *
 * @author Karolina Burska
 */
public class OfficeDaoImpl implements OfficeDao {
    	
        private EntityManagerFactory emf;
        
        @Override
        public void setEMF(EntityManagerFactory emf){
            this.emf = emf;
        }
    
        @Override
        public void addOffice(Office office){
            if(office == null) {
                throw new NullPointerException("Office is null.");
            }
            if(office.getID() != null) {
                throw new IllegalArgumentException("Office is already added.");
            }

            EntityManager em = emf.createEntityManager();
            
            em.getTransaction().begin();
            em.persist(office);
            em.getTransaction().commit();
            em.close();        
        }
        
        @Override
        public Office getOffice(Long id){
            if(id == null) {
            throw new IllegalArgumentException("ID is null.");
            }
            if(id < 0) {
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
	public void editOffice(Office office){
            if(office == null) {
                throw new NullPointerException("Office is null.");
            }
 
        }

        @Override
	public void deleteOffice(Office office){
            if(office == null) {
                throw new NullPointerException("Office is null.");
            }
            
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            em.remove(em.find(Office.class, office.getID()));
            
            em.getTransaction().commit();
            em.close(); 
        }

        @Override
	public Collection getAllOffices(){
            EntityManager em = emf.createEntityManager();
        
            Query query = em.createQuery("SELECT * FROM Office");
            List<Office> offices = query.getResultList();
        
            em.getTransaction().commit();
            em.close();
        
        return Collections.unmodifiableCollection(offices);
        }

        @Override
	public Collection getOfficeCars(Office office){
            throw new UnsupportedOperationException("Not supported yet."); 
        }

        @Override
	public Collection getEmployees(Office office){
            throw new UnsupportedOperationException("Not supported yet."); 
        }
}
