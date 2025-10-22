package com.example.playground.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    Category wearhouse = new Category("Wearhouse");
    Category factory = new Category("Factory");

    List<Category> company = new ArrayList<>(List.of(wearhouse, factory));

    @BeforeEach
    void setUp() {
        wearhouse.setId(1L);
        factory.setId(2L);

        Mockito.when(categoryService.saveCategory(Mockito.any(Category.class))).thenReturn(wearhouse);
        Mockito.when(categoryService.findAllCategorys()).thenReturn(company);
        Mockito.when(categoryService.findCategoryById(Mockito.anyLong())).thenReturn(factory);
    }

    @Test
    void shouldCreateCategory() throws Exception {
        String wearhouseJson = objectMapper.writeValueAsString(wearhouse);
        mvc.perform(MockMvcRequestBuilders.post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(wearhouseJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Wearhouse"));
        Mockito.verify(categoryService).saveCategory(any(Category.class));
    }

    @Test
    void shouldSaveNewCategory() throws Exception {
        String factoryJson = objectMapper.writeValueAsString(factory);
        mvc.perform(MockMvcRequestBuilders.post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(factoryJson));

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);

        Mockito.verify(categoryService).saveCategory(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(factory);
    }

    @Test
    void shouldFindAllCategorys() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        Mockito.verify(categoryService).findAllCategorys();
    }

    @Test
    void shouldFindCategoryById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/category/2"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Factory"));
        Mockito.verify(categoryService).findCategoryById(2L);
    }

}