package com.example.SpringBootLibrary.controllers;

import com.example.SpringBootLibrary.models.Person;
import com.example.SpringBootLibrary.service.PeopleService;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String getAllPeople(Model model){
        model.addAttribute("people", peopleService.getAllPerson());
        return "/people/allPeople";
    }
    @GetMapping("/{id}")
    public String findPeopleById(Model model,@PathVariable("id") int id){
        Person person = peopleService.findPersonById(id);
        model.addAttribute("person", person);
        model.addAttribute("books", peopleService.findBookByPersonId(id));

        return "/people/index";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping()
    public String createPerson(@ModelAttribute("person")@Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return"/people/new";
        }
        peopleService.createPerson(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String editPerson(Model model,@PathVariable("id") int id ){
        model.addAttribute("person", peopleService.findPersonById(id));
        return "people/edit";
    }
    @PatchMapping ("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "/people/edit";
        }
        peopleService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        peopleService.deletePerson(id);
        return "redirect:/people";
    }

}
