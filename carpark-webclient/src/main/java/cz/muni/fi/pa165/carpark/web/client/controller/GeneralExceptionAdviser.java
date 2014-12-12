/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.client.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * General exception handler.
 *
 * @author Tomas Svoboda
 */
@ControllerAdvice
public class GeneralExceptionAdviser
{    

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Global exception occured")
    @ExceptionHandler(Exception.class)
    public void generalExceptionHandler(Exception e)
    {
    }    
}
