package com.example.playground.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    Category factory;
    Category wearhouse;

    List<Category> company;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        factory = new Category("Factory");
        wearhouse = new Category("Wearhouse");
        company = new ArrayList<>(List.of(factory, wearhouse));

        factory.setId(2L);
    }

    @Test
    void shouldSaveCategory() {
        when(categoryRepository.save(factory)).thenReturn(factory);
        Category actualRequest = categoryService.saveCategory(factory);
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertThat(actualRequest).isEqualTo(factory);
    }

    @Test
    void shouldGetAllCategorys() {
        when(categoryRepository.findAll()).thenReturn(company);
        List<Category> actualRequest = categoryService.findAllCategorys();
        verify(categoryRepository, times(1)).findAll();
        assertThat(actualRequest).isEqualTo(company);
    }

    @Test
    void shouldGetPilotById() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(factory));
        Category actualRequest = categoryService.findCategoryById(2L);
        verify(categoryRepository, times(1)).findById(2L);
        assertThat(actualRequest).isEqualTo(factory);
    }

}