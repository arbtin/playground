package com.example.playground.person;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

//    @PostMapping()
//    public ResponseEntity<AircraftDto> createAircraft(@RequestBody AircraftDto aircraftDto) {
//        return ResponseEntity.ok( aircraftService.saveAircaft(aircraftDto));
//    }

    @GetMapping()
    public List<PersonDto> getAllPerson() {
        return personService.getAllPerson();
    }

    @GetMapping("/{personId}")
    public PersonDto getPersonById(@PathVariable Long personId) {
        return personService.getPersonDtoById(personId);
    }
}
