/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.dao;

import cz.muni.fi.pa165.carpark.config.DaoTestConfig;
import javax.inject.Inject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test of dao exception aspect.
 *
 * @author Tomas Svoboda
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoTestConfig.class)
public class DaoExceptionAspectTest
{

    @Inject
    private DaoExceptionMockImpl daoExceptionAspectImpl;

    @Test(expected = DataAccessException.class)
    public void testThrowException()
    {
        daoExceptionAspectImpl.throwIllegalArgumentException();
    }

    @Test
    public void testWithoutException()
    {
        daoExceptionAspectImpl.returnString();
    }
}
