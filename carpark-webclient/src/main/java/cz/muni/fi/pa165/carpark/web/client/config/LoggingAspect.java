/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.client.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Logging aspect for several modules.
 *
 * @author Tomas Svoboda
 */
@Component
@Aspect
public class LoggingAspect
{

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(cz.muni.fi.pa165.carpark.web.client..*)")
    public void inWebLayer()
    {
    }

    @Before("inWebLayer()")
    public void logBeforeExecution(JoinPoint joinPoint)
    {
        LOGGER.debug("BEFORE: " + joinPoint.toLongString());
    }
    
    @AfterReturning(pointcut = "inWebLayer()", returning = "retValue")
    public void logAfterExecution(JoinPoint joinPoint)
    {
        LOGGER.debug("AFTER: " + joinPoint.toLongString());
    }
    
    @AfterThrowing(pointcut = "inWebLayer()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error)
    {
        LOGGER.error("AFTER: " + joinPoint.toLongString() + ", THROWN: with msg [" + error.getMessage() + "], cause: " + error);
    }
}
