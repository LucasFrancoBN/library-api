package io.github.lucasfrancobn.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String paginaLogin() {
        // Esse nome precisa ser o mesmo nome da página que registramos no addViewController
        return "login";
    }
}
