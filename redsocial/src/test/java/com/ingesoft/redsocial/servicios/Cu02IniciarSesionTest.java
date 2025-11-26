package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.modelo.Usuario;

@SpringBootTest
@Transactional
public class Cu02IniciarSesionTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void iniciarSesionPorLogin_exito() throws Exception {
        usuarioRepository.deleteAll();

        Usuario u = new Usuario();
        u.setLogin("ana");
        u.setNombre("Ana");
        u.setPassword("Secret1");
        usuarioRepository.save(u);

        // no debe lanzar excepción
        usuarioService.iniciarSesion("ana", "Secret1");
    }

    @Test
    void iniciarSesionPorLogin_passwordIncorrecto_falla() {
        usuarioRepository.deleteAll();

        Usuario u = new Usuario();
        u.setLogin("carlos");
        u.setNombre("Carlos");
        u.setPassword("MiPass123");
        usuarioRepository.save(u);

        Exception e = assertThrows(Exception.class, () -> {
            usuarioService.iniciarSesion("carlos", "wrongpass");
        });

        assertTrue(e.getMessage().toLowerCase().contains("no coincide") || e.getMessage().toLowerCase().contains("login"));
    }

    @Test
    void iniciarSesionPorCorreo_exito() throws Exception {
        usuarioRepository.deleteAll();

        Usuario u = new Usuario();
        u.setLogin("luis");
        u.setNombre("Luis");
        u.setCorreo("luis@javeriana.edu.co");
        u.setPassword("Clave123");
        usuarioRepository.save(u);

        // no debe lanzar excepción
        usuarioService.iniciarSesionPorCorreo("luis@javeriana.edu.co", "Clave123");
    }

    @Test
    void iniciarSesionPorCorreo_correoNoExiste_falla() {
        usuarioRepository.deleteAll();

        Exception e = assertThrows(Exception.class, () -> {
            usuarioService.iniciarSesionPorCorreo("noexiste@javeriana.edu.co", "12345");
        });

        assertTrue(e.getMessage().toLowerCase().contains("no existe") || e.getMessage().toLowerCase().contains("correo"));
    }

}

