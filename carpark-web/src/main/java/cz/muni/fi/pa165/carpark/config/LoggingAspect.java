/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author Tomas Svoboda
 */
@Component
@Aspect
public class LoggingAspect
{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(cz.muni.fi.pa165.carpark.dao..*)")
    public void inDaoLayer()
    {
    }

    @Pointcut("within(cz.muni.fi.pa165.carpark.service..*)")
    public void inServiceLayer()
    {
    }

    @Pointcut("within(cz.muni.fi.pa165.carpark.web..*)")
    public void inWebLayer()
    {
    }

    @Before("inWebLayer() || inServiceLayer() || inDaoLayer()")
    public void logBeforeExecution(JoinPoint joinPoint)
    {
        LOGGER.debug("BEFORE: " + joinPoint.toLongString());//logMsg.toString());
    }
    
    @AfterReturning(pointcut = "inWebLayer() || inServiceLayer() || inDaoLayer()", returning = "retValue")
    public void logAfterExecution(JoinPoint joinPoint)
    {
        LOGGER.debug("AFTER: " + joinPoint.toLongString());
    }
    
    @AfterThrowing(pointcut = "inWebLayer() || inServiceLayer() || inDaoLayer()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error)
    {
        LOGGER.error("AFTER: " + joinPoint.toLongString() + ", THROWN: " + error);
    }
}
