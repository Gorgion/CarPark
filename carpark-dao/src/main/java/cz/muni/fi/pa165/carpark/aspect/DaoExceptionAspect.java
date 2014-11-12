/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * Aspect for handling exception within dao methods.
 *
 * @author Tomas Svoboda
 */
@Aspect
@Component
public class DaoExceptionAspect
{

    @AfterThrowing(pointcut = "within(cz.muni.fi.pa165.carpark.dao.*)", throwing = "ex")
    public void afterThrowingExecution(JoinPoint jp, Exception ex)
    {
        throw new DataAccessException("Some error occured in dao method.", ex)
        {
        };
    }
}
