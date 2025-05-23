package com.example.atdd.controller;

import com.example.atdd.model.Todo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class TodoController {
    private Map<String, List<Todo>> todos = new HashMap<>();

    @GetMapping("/todo")
    public String showTodos(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";
        List<Todo> userTodos = todos.getOrDefault(username, new ArrayList<>());
        model.addAttribute("todos", userTodos);
        model.addAttribute("username", username);
        return "todo";
    }

    @PostMapping("/todo")
    public String addTodo(@RequestParam String todo, HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) return "redirect:/login";
        todos.putIfAbsent(username, new ArrayList<>());
        if (!todo.isBlank()) {
            todos.get(username).add(new Todo(todo));
        }
        return "redirect:/todo";
    }
}