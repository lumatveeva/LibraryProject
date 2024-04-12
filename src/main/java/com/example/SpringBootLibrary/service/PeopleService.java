package com.example.SpringBootLibrary.service;

import com.example.SpringBootLibrary.models.Book;
import com.example.SpringBootLibrary.models.Person;
import com.example.SpringBootLibrary.repository.PeopleRepository;
import org.hibernate.Hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    public List<Person> getAllPerson(){
        return peopleRepository.findAll();
    }
    public Person findPersonById(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        return person;
    }
    public Optional<Person> getPersonByName(String name){
        return peopleRepository.findByName(name);
    }
    public List<Book> findBookByPersonId(int id){
        Optional<Person> owner = peopleRepository.findById(id);

        if(owner.isPresent()){
            Hibernate.initialize(owner.get().getBooks());
            owner.get().getBooks().forEach( book ->{
                    long diffInMilles = Math.abs( book.getTakenAt().getTime() - new Date().getTime());
                    if ( diffInMilles > 864000000)
                        book.setLate(true);
            });
            return owner.get().getBooks();
        } else{
            return Collections.emptyList();
        }

    }
    @Transactional
    public void deletePerson(int id){
        peopleRepository.deleteById(id);    }

    @Transactional
    public void createPerson(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

}
