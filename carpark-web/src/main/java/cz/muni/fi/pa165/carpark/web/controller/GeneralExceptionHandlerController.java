/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Tomas Svoboda
 */
@Controller
public class GeneralExceptionHandlerController
{
    @RequestMapping("/500")
    public String handle500error(Model model)
    {
//        model.addAttribute("", model);
        return "";
    }
    
    @RequestMapping("/400")
    public String handle400error(Model model)
    {
//        model.addAttribute("", model);
        return "";
    }
    
    @RequestMapping("/404")
    public String handle404error(Model model)
    {
//        model.addAttribute("", model);
        return "500";
    }
}
