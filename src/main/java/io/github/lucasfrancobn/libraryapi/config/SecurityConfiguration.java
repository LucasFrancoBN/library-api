package io.github.lucasfrancobn.libraryapi.config;

import io.github.lucasfrancobn.libraryapi.security.CustomUserDetailsService;
import io.github.lucasfrancobn.libraryapi.security.LoginSocialSuccessHandler;
import io.github.lucasfrancobn.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler loginSocialSuccessHandler) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // Adiciona a página de login customizada
                .formLogin(configurer -> configurer.loginPage("/login").permitAll())
                // Adiciona o http basic como padrão
                .httpBasic(Customizer.withDefaults())
                // Estamos falando que todas requisições devem ser autenticadas.
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/login").permitAll();
                    authorizeRequests.requestMatchers(HttpMethod.POST, "/api/v1/usuarios").permitAll();

                    authorizeRequests.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> {
                    oauth2
                            // Permite o login social a partir da URL específicada
                            .loginPage("/login")
                            // Quando ele se autenticar com sucesso, ele vai chamar a classe.
                            .successHandler(loginSocialSuccessHandler);
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService) {
        return new CustomUserDetailsService(usuarioService);
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
