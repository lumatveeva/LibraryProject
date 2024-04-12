package com.example.SpringBootLibrary.util;


import com.example.SpringBootLibrary.models.Person;
import com.example.SpringBootLibrary.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//public class PersonValidator implements Validator {
//    @Autowired
//    private PeopleService peopleService;
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return Person.class.equals(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        Person person = (Person) target;
//        if(peopleService.getPersonByName(person.getName()).isPresent()){
//            errors.rejectValue("name", "", "Человек с таким именем уже существует");
//        }
//    }
//}
