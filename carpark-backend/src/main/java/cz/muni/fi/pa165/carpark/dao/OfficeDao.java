/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.entity.Office;
import java.util.Collection;

/**
 *
 * @author Karolina Burska
 */
public interface OfficeDao {    
    public void addOffice(Object office);

	public Office getOffice(Long id);

	public void editOffice(Office office);

	public void deleteOffice(Office office);

	public Collection getAllOffices();

	public Collection getOfficeCars(Office office);

	public Collection getEmployees();

}
