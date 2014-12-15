/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Karolina Burska
 */
@Controller
@RequestMapping("/user")
public class UserClientController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "user-list";
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String handleRequest() {
        return "user-form";
    }
    
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String deleteUser() {
        return "redirect:/user";
    }
    
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable Long id, Model model) {
        return "user-edit-form";
    }
}
