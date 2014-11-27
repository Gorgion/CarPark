/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Tomas Svoboda
 */
@ControllerAdvice
public class GeneralExceptionAdviser
{
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {DataAccessException.class})
    public ModelAndView serverErrorHandler(DataAccessException e)
    {
        ModelAndView model = new ModelAndView("error");
        
        model.addObject("error.code", "500");
        model.addObject("error.msg", "error.500.body");
        
        return model;
    }
     
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView generalExceptionHandler(Exception e)
    {
        ModelAndView model = new ModelAndView("error");
        
        model.addObject("error.code", "500");
        model.addObject("error.msg", "error.500.body");
        
        return model;
    }
}
