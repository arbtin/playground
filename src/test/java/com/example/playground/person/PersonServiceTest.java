package com.example.playground.person;

import com.example.playground.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    PersonRepository personRepository;

    @InjectMocks
    PersonService personService;

    Person bob = new Person("Bob");
    List<Person> staff;

    Category employees;

    @BeforeEach
    void setUp() {
        bob.setId(1L);
        employees = new Category("employees");
        employees.setId(1L);
    }

    @Test
    void shouldSavePerson() {
        // Arrange
        when(personRepository.save(bob)).thenReturn(bob);
        // Act
        Person actualPerson  = personService.savePerson(bob);
        // Assert
        verify (personRepository, times (1)).save(any(Person.class));
        assertThat(actualPerson).isEqualTo(bob);
    }

    @Test
    void shouldFindAllPersons() {
        // Arrange
        when(personRepository.findAll()).thenReturn(staff);
        // Act
        List<Person> listAllPersons = personService.findAllPersons();
        // Assert
        verify(personRepository, times(1)).findAll();
        assertThat(listAllPersons).isEqualTo(staff);
    }

    @Test
    void shouldFindAircraftById() {
        // Arrange
        when(personRepository.findById(1L)).thenReturn(Optional.of(bob));
        // Act
        Person foundSinglePerson = personService.findPersonById(1L);
        // Assert
        verify (personRepository, times(1)).findById(1L);
        assertThat(foundSinglePerson).isEqualTo(bob);

    }
}