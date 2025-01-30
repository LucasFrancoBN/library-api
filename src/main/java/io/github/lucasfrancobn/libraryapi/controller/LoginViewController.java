package io.github.lucasfrancobn.libraryapi.controller;

import io.github.lucasfrancobn.libraryapi.security.CustomAuthentication;
import io.github.lucasfrancobn.libraryapi.security.CustomUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {
    @GetMapping("/login")
    public String paginaLogin() {
        // Esse nome precisa ser o mesmo nome da página que registramos no addViewController
        return "login";
    }

    @GetMapping
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if(authentication.getPrincipal() instanceof CustomAuthentication) {
            System.out.println(authentication.getPrincipal());
        }

        return "Olá " + authentication.getName();
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String authorized(@RequestParam String code) {
        return "Seu authorization code é: " + code;
    }
}
