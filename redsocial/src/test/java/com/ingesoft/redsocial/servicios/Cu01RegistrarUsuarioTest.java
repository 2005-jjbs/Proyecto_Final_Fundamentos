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
public class Cu01RegistrarUsuarioTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void registrarUsuarioConCorreoValido_exito() throws Exception {
        usuarioRepository.deleteAll();

        String correo = "juan@javeriana.edu.co";
        usuarioService.registrarNuevoUsuario(correo, "Juan", "Estudiante", "Password1");

        assertTrue(usuarioRepository.existsByCorreo(correo));
    }

    @Test
    void registrarUsuarioConCorreoNoInstitucional_falla() {
        usuarioRepository.deleteAll();

        Exception e = assertThrows(Exception.class, () -> {
            usuarioService.registrarNuevoUsuario("juan@gmail.com", "Juan", "Estudiante", "Password1");
        });

        assertTrue(e.getMessage().toLowerCase().contains("institucional") || e.getMessage().toLowerCase().contains("correo"));
    }

    @Test
    void registrarUsuarioPorLogin_exito() throws Exception {
        usuarioRepository.deleteAll();

        usuarioService.registrarNuevoUsuario("maria", "María", "Password123");
        assertTrue(usuarioRepository.existsById("maria"));
    }

    @Test
    void registrarUsuarioPorLogin_passwordCorto_falla() {
        usuarioRepository.deleteAll();

        Exception e = assertThrows(Exception.class, () -> {
            usuarioService.registrarNuevoUsuario("pedro", "Pedro", "123");
        });

        assertTrue(e.getMessage().toLowerCase().contains("contraseña") || e.getMessage().toLowerCase().contains("política"));
    }

}

