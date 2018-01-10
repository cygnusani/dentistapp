package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.Date;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @RequestMapping(path = "/")
    public String index() {
        return "index";
    }

    @GetMapping("/form")
    public String showRegisterForm(DentistVisitDTO dentistVisitDTO, Model model) {
        // new date: today
        dentistVisitDTO.setVisitTime(new Date());
        // fetch all dentist names for dropdown field
        model.addAttribute("dentists", dentistVisitService.findAllDentists());
        // return view
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("dentists", dentistVisitService.findAllDentists());
            return "form";
        }
        // save/merge visit info
        dentistVisitService.addVisit(dentistVisitDTO.getId(), dentistVisitDTO.getDentistName(), dentistVisitDTO.getVisitTime());
        // add search to model
        model.addAttribute("key", "");
        // fetch all registered visits
        model.addAttribute("regs", dentistVisitService.findAll());

        return "redirect:/all";
    }

    @GetMapping("/all")
    public String showAllRegistrations(Model model) {
        model.addAttribute("key", "");
        model.addAttribute("regs", dentistVisitService.findAll());
        return "all";
    }

    @GetMapping(path = "/all/edit/{id}")
    public String editRegistration(@PathVariable Long id, @ModelAttribute DentistVisitDTO dentistVisitDTO, Model model) {
        model.addAttribute("dentists", dentistVisitService.findAllDentists());
        // fetch visit time info by id
        DentistVisitEntity e = dentistVisitService.findById(id);
        // save id
        model.addAttribute("id", e.getId());
        // update DTO info
        dentistVisitDTO.setDentistName(e.getDentistName());
        dentistVisitDTO.setVisitTime(e.getVisitTime());
        // return view
        return "form";
    }

    @GetMapping("/all/delete/{id}")
    public String deleteRegistration(@PathVariable Long id, Model model) {
        // delete visit info by id
        dentistVisitService.delete(id);
        model.addAttribute("key", "");
        model.addAttribute("regs", dentistVisitService.findAll());
        return "all";
    }

    @GetMapping("/all/search/{key}")
    public String search(@PathVariable String key, Model model) {
        model.addAttribute("key", "");
        model.addAttribute("regs", dentistVisitService.search(key));
        return "all";
    }
}
