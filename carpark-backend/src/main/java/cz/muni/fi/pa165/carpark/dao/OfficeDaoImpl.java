/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Office;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Lorain
 */
public class OfficeDaoImpl {
    	
        private EntityManagerFactory emf;
        
        public void setEMF(EntityManagerFactory emf)
        {
            this.emf = emf;
        }
    
        public Office getOffice(Long id){
            if(id == null) {
            throw new IllegalArgumentException("ID is NULL");
        }
        if(id < 0) {
            throw new IllegalArgumentException("ID is less than 0");
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Office office = em.find(Office.class, id);

        em.getTransaction().commit();
        em.close();
        
        return office;
        }
        
	public void editOffice(Office office){
            throw new UnsupportedOperationException("Not supported yet."); 
        }

	public void deleteOffice(Object office){
            throw new UnsupportedOperationException("Not supported yet."); 
        }

	public Collection getAllOffices(){
            throw new UnsupportedOperationException("Not supported yet."); 
        }

	public Collection getOfficeCars(Object office){
            throw new UnsupportedOperationException("Not supported yet."); 
        }

	public Collection getEmployees(){
            throw new UnsupportedOperationException("Not supported yet."); 
        }
}
