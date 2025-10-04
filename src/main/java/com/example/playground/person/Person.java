package com.example.playground.person;

import jakarta.persistence.*;
import com.example.playground.category.Category;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    public Person() {
//    }
//
//    public Person(String name, Category category) {
//        this.name = name;
//        this.category = category;
//    }

    public Category getCategory() {
        return this.category;
    }


    public Long getId() {
        return id;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    // Getters and setters
}
