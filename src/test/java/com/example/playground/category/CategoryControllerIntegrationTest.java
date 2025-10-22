package com.example.playground.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIntegrationTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    CategoryRepository categoryRepository;

    Category company = new Category("Company");

    @Test
    void shouldCreateCategory() throws Exception {
        String categoryJson = mapper.writeValueAsString(company);
        mvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.name").value("Company"));
    }

    @Test
    void shouldGetAllCategorys() throws Exception {
        categoryRepository.save(company);
        //categoryRepository.save();

        mvc.perform(get("/api/category"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$[0].name").value("Company"));
    }

    @Test
    void shouldGetPilotById() throws Exception {
        Category savedCompany = categoryRepository.save(company);

        mvc.perform(MockMvcRequestBuilders.get("/api/category/" + savedCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company"));

    }

}