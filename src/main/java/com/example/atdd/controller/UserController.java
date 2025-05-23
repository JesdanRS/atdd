package com.example.atdd.controller;

import com.example.atdd.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    private Map<String, User> users = new HashMap<>();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String confirm,
                           Model model) {
        if (users.containsKey(username)) {
            model.addAttribute("message", "El usuario ya existe.");
            return "register";
        }
        if (!password.equals(confirm)) {
            model.addAttribute("message", "Las contraseñas no coinciden.");
            return "register";
        }
        users.put(username, new User(username, email, encoder.encode(password)));
        model.addAttribute("message", "¡Registro exitoso! Ahora puedes iniciar sesión.");
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = users.get(username);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            model.addAttribute("message", "Usuario o contraseña incorrectos.");
            return "login";
        }
        session.setAttribute("username", username);
        return "redirect:/todo";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}