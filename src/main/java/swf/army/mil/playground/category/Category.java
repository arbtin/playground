package swf.army.mil.playground.category;

import jakarta.persistence.*;
import swf.army.mil.playground.person.Person;

import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Person> people;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}