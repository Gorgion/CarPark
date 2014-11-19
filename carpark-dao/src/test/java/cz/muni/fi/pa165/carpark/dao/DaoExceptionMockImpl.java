/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import org.springframework.stereotype.Repository;

/**
 * Class used for testing dao exception aspect.
 *
 * @author Tomas Svoboda
 */
@Repository
public class DaoExceptionMockImpl
{
    public void throwIllegalArgumentException()
    {
        throw new IllegalArgumentException();
    }
    
    public String returnString()
    {
        return "success";
    }
}
