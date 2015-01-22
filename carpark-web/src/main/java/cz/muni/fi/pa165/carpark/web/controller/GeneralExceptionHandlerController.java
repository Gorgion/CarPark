/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Exception handler controller.
 *
 * @author Tomas Svoboda
 */
@Controller
public class GeneralExceptionHandlerController
{
    
    @RequestMapping("/500")
    public String handle500error()
    {        
        return "500";
    }
    
    @RequestMapping("/400")
    public String handle400error()
    {     
        return "400";
    }
    
    
    @RequestMapping("/404")
    public String handle404error()
    {        
        return "404";
    }
    
    @RequestMapping("/403")
    public String handle403error()
    {        
        return "403";
    }
}
