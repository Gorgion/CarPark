/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for general purposes.
 *
 * @author Tomas Svoboda
 */
@Controller
public class GeneralController
{    
    @RequestMapping(value = "/")
    public String homePage()
    {        
        return "index";
    }        
}
