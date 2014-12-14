/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model, HttpServletRequest request) throws Exception
    {
        if (error != null)
        {
            model.addAttribute("error", "error.badCredentials");
        }

        if (logout != null)
        {
            model.addAttribute("msg", "msg.logout");
        }
        return "login";
    }
}
