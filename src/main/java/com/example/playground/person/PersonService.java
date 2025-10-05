package com.example.playground.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

//    public List<PersonDto> getAllPerson() {
//        return personRepository.findAll().stream()
//                .map(person -> new PersonDto(person.getId(), person.getName(), person.getCategory()))
//                .toList();
//    }

    public List<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public PersonDto getPersonDtoById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        return new PersonDto(person.getId(), person.getName(), person.getCategory());
    }

    public Person findPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElse(null);
    }

    public Person updatePersonById(Long id, Person person) {
        if (!personRepository.existsById(id)) {
            // should return status code not found
            System.out.println("Person not found with id: " + id);
        }
        person.setId(id);
        return personRepository.save(person);
    }

    public Person partialUpdatePerson(Long id, Person person) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson == null) {
            // Send back not found status code
            System.out.println("Person not found with id " + id);
        }

        if (person.getName() != null) {
            existingPerson.setName(person.getName());
        }
        if (person.getCategory() != null) {
            existingPerson.setCategory(person.getCategory());
        }

        return personRepository.save(existingPerson);
    }

    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            System.out.println("Person not found with id " + id);
        }
        // Send back Delete status success
        personRepository.deleteById(id);
    }

}
