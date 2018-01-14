package com.cgi.dentistapp.controller;

import com.cgi.dentistapp.dao.entity.DentistVisitEntity;
import com.cgi.dentistapp.dto.DentistVisitDTO;
import com.cgi.dentistapp.service.DentistVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class DentistAppController extends WebMvcConfigurerAdapter {

    @Autowired
    private DentistVisitService dentistVisitService;

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
        if (bindingResult.hasErrors()) {
            hasErrors = true;
        }
        Date temp = dentistVisitService.formatDate(dentistVisitDTO.getVisitTime(), dentistVisitDTO.getTime());
        if (temp.before(new Date())) {
            bindingResult.rejectValue("visitTime", "error.visitTime.past");
            hasErrors = true;
        }
        // check if the date is available
        if (!dentistVisitService.visitTimeAvailable(dentistVisitDTO.getId(), dentistVisitDTO.getDentistName(), temp)) {
            // create error message
            bindingResult.rejectValue("visitTime", "error.visitTime.taken");
            hasErrors = true;
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
        return "all";
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
        // update DTO info
        dentistVisitDTO.setId(e.getId());
        dentistVisitDTO.setDentistName(e.getDentistName());
        dentistVisitDTO.setVisitTime(e.getVisitTime());
        dentistVisitDTO.setTime(dentistVisitService.formatTime(e.getVisitTime()));
        // add dentistVisitDTO to model
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
