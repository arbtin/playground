package com.example.playground.todo;

import org.springframework.stereotype.Service;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> fetchTodos(){
        return todoRepository.findAll();
    }

    public Todo createTodo(Todo newTodo) {
        return todoRepository.save(newTodo);
    }

    public Long deleteTodo(Long id) {
        todoRepository.deleteById(id);
        return id;
    }

    public Todo editTodo(Long id, Todo updatedTodo) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isEmpty()) {
            throw new IllegalArgumentException("Todo of id " + id + " not found.");
        }

        Todo todo = optionalTodo.get();
        todo.setText(updatedTodo.getText());
        todo.setStatus(updatedTodo.getStatus());
        return todoRepository.save(todo);
    }

    public Todo findByStatusOrId(String status, Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        Optional<Todo> todo = Optional.empty();
        if (status != null && !status.isBlank()) {
            todo = todoRepository.findByStatusOrId(status, id)
                    .or(() -> todoRepository.findById(id));
        }
        return todo.orElseThrow(() -> new ResourceNotFoundException("Todo was not found for status: " + status + " or Id: " + id));
    }
}