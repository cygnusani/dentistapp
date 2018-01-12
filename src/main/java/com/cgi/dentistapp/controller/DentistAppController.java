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
import java.util.List;

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
        // fetch all dentist names for dropdown field
        model.addAttribute("dentists", dentistVisitService.findAllDentists());
        // fetch all visit times
        List<String> times = dentistVisitService.findAllTimes();
        model.addAttribute("times", times);
        // new date: today
        dentistVisitDTO.setTime(times.get(0));
        dentistVisitDTO.setVisitTime(new Date());
        model.addAttribute("dentistVisitDTO", dentistVisitDTO);
        // return view
        return "form";
    }

    @PostMapping("/")
    public String postRegisterForm(@Valid DentistVisitDTO dentistVisitDTO, BindingResult bindingResult, Model model) {
        boolean hasErrors = false;
        // check for binding errors
        /*
        if (bindingResult.hasErrors()) {
            hasErrors = true;
        }
        */

        Date temp = dentistVisitService.formatDate(dentistVisitDTO.getVisitTime(), dentistVisitDTO.getTime());
        if (temp.before(new Date())) {
            bindingResult.rejectValue("visitTime", "error.visitTime.past");
            hasErrors = true;
        }
        // check if selected date is in the past
        /*
        if (dentistVisitService.visitTimeInPast(dentistVisitDTO.getVisitTime())) {
            bindingResult.rejectValue("visitTime", "error.visitTime.past");
            hasErrors = true;
        }
        */
        // check if the date is available
        if (!dentistVisitService.visitTimeAvailable(dentistVisitDTO.getDentistName(), temp)) { //dentistVisitDTO.getVisitTime(), dentistVisitDTO.getTime()
            // https://stackoverflow.com/questions/12107503/adding-error-message-to-spring-3-databinder-for-custom-object-fields
            // create error message
            bindingResult.rejectValue("visitTime", "error.visitTime.taken");
            hasErrors = true;
            //model.addAttribute("dentists", dentistVisitService.findAllDentists());
            //return "form";
        }
        // if has errors ...
        if (hasErrors) {
            // fill dentists list
            model.addAttribute("dentists", dentistVisitService.findAllDentists());
            // fetch all visit times
            model.addAttribute("times", dentistVisitService.findAllTimes());
            // return to form
            return "form";
        }
        // if there are no errors ...
        // save/merge visit info
        dentistVisitService.addVisit(
                dentistVisitDTO.getId(),
                dentistVisitDTO.getDentistName(),
                temp);
        // add search to model
        model.addAttribute("key", "");
        // fetch all registered visits
        model.addAttribute("regs", dentistVisitService.findAll());
        // return to all visits view
        return "redirect:/all";
    }

    @GetMapping("/all")
    public String showAllRegistrations(Model model) {
        model.addAttribute("key", "");
        model.addAttribute("regs", dentistVisitService.findAll());
        return "all";
    }

    @GetMapping(path = "/all/edit/{id}")
    public String editRegistration(@PathVariable Long id, DentistVisitDTO dentistVisitDTO, Model model) { //, @ModelAttribute DentistVisitDTO dentistVisitDTO
        // fetch all visit times
        model.addAttribute("times", dentistVisitService.findAllTimes());
        model.addAttribute("dentists", dentistVisitService.findAllDentists());
        // fetch visit time info by id
        DentistVisitEntity e = dentistVisitService.findById(id);
        // save id
        //model.addAttribute("id", e.getId());
        // update DTO info
        dentistVisitDTO.setId(e.getId());
        dentistVisitDTO.setDentistName(e.getDentistName());
        dentistVisitDTO.setVisitTime(e.getVisitTime());
        dentistVisitDTO.setTime(String.format("%d:%d", e.getVisitTime().getHours(), e.getVisitTime().getMinutes()));

        model.addAttribute("dentistVisitDTO", dentistVisitDTO);
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

    @GetMapping("/all/search")
    public String search(@RequestParam String key, Model model) {
        model.addAttribute("key", "");
        model.addAttribute("regs", dentistVisitService.search(key));
        return "all";
    }
}
