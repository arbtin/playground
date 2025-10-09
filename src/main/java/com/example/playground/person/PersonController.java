package com.example.playground.person;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping()
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.ok( personService.savePerson(person));
    }

    @GetMapping()
    public List<Person> getAllPerson() {
        return personService.findAllPersons();
    }

//    @GetMapping
//    public List<Person> getPersons() { return personService.findAllPersons();
//    }

    @GetMapping("/{personId}")
    public Person findPersonById(@PathVariable Long personId) {
        return personService.findPersonById(personId);
    }

//    @GetMapping("/{id}")
//    public Person getPersonById(@PathVariable Long id) { return personService.findPersonById(id);
//    }


    @PutMapping("/{id}")
    public Person updatePersonById(@PathVariable Long id, @RequestBody Person person) {
        return personService.updatePersonById(id, person);
    }

    @PatchMapping("/{id}")
    public Person partialUpdatePerson(@PathVariable Long id, @RequestBody Person person) {
        return personService.partialUpdatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}