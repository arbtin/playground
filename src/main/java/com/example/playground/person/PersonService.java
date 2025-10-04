package com.example.playground.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDto getPersonDtoById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return new PersonDto(person.getId(), person.getName(), person.getCategory());
    }

    public List<PersonDto> getAllPerson() {
        return personRepository.findAll().stream()
                .map(person -> new PersonDto(person.getId(), person.getName(), person.getCategory()))
                .toList();
    }
}
