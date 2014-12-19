/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.carpark.web.client.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jiri Dockal
 */
@Controller
@RequestMapping("/office")
public class OfficeClientController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "office-list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String handleRequest() {
        return "office-form";
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public String officeDeletion() {
        return "redirect:/office";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editOffice(@PathVariable Long id, Model model) {
        return "office-edit-form";
    }

}
