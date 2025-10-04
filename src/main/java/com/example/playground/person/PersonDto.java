package com.example.playground.person;

import com.example.playground.category.Category;

public record PersonDto(Long id, String name, Category category) {}