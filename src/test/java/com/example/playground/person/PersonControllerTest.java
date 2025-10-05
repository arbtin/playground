package com.example.playground.person;

import com.example.playground.category.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    PersonService personService;

    Person bob = new Person("Bob");
    Person nancy = new Person("Nancy");

    Category newCategory;

    Person updatedBob = new Person("Fred");
    Person updatedNancy = new Person("Mary");
    List<Person> staff;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        bob.setId(1L);
        nancy.setId(2L);

        staff = new ArrayList<>(List.of(bob, nancy));

        newCategory = new Category("New Staff");
        newCategory.setId(1L);
        bob.setCategory(newCategory);
        nancy.setCategory(newCategory);

        updatedBob.setCategory(newCategory);

        Mockito.when(personService.updatePersonById(anyLong(), any(Person.class))).thenReturn(updatedBob);
        Mockito.when(personService.partialUpdatePerson(anyLong(), any(Person.class))).thenReturn(updatedNancy);
    }

    @Test
    void shouldCreatePerson() throws Exception {
        Mockito.when(personService.savePerson(any(Person.class))).thenReturn(nancy);
        String nancyJson = objectMapper.writeValueAsString(nancy);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nancyJson))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Nancy"))
                .andExpect(jsonPath("$.category.name").value("New Staff"));
        Mockito.verify(personService).savePerson(any(Person.class));
    }

    @Test
    void shouldFindAllPersons() throws Exception {
        Mockito.when(personService.findAllPersons()).thenReturn(staff);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        Mockito.verify(personService).findAllPersons();
    }

    @Test
    void shouldFindPersonById() throws Exception {
        Mockito.when(personService.findPersonById(Mockito.anyLong())).thenReturn(bob);
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/person/2"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.name").value("Factory"));
        Mockito.verify(personService).findPersonById(2L);
    }

    @Test
    void shouldUpdatePersonById() throws Exception {
        Person updatedPerson = new Person();
        updatedPerson.setId(1L);
        updatedPerson.setName("Fred");
        updatedPerson.setCategory(newCategory);
        String updateContent = objectMapper.writeValueAsString(updatedPerson);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fred"))
                .andExpect(jsonPath("$.category.name").value("New Staff"));
    }

    @Test
    void shouldUpdatePersonName() throws Exception {
        Person existingPerson = new Person();
        existingPerson.setId(2L);
        existingPerson.setName("Mary");

        Person updatedPerson = new Person();
        updatedPerson.setCategory(newCategory);

        mockMvc.perform(MockMvcRequestBuilders.patch("/v1/api/person/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Mary\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mary"));
    }

    @Test
    void shouldDeletePerson() throws Exception {
        doNothing().when(personService).deletePerson(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/person/3"))
                .andExpect(status().isNoContent());
    }
}