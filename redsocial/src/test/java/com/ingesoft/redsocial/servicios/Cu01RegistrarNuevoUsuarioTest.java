package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@SpringBootTest
@Transactional
class Cu01RegistrarNuevoUsuarioTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarios;

    @Test
    void registraUsuarioConExito() throws Exception {

        usuarios.deleteAll();

        String login = "integ_user";
        String nombre = "Integracion Usuario";
        String password = "password123";

        usuarioService.registrarNuevoUsuario(login, nombre, password);

        assertTrue(usuarios.existsById(login));
        Usuario u = usuarios.findById(login).orElseThrow();
        assertEquals(nombre, u.getNombre());
        assertEquals(password, u.getPassword());
    }

    @Test
    void fallaSiLoginYaExisteEnBD() throws Exception {
        usuarios.deleteAll();

        String login = "existing_user";
        String nombre = "Existente";
        String password = "password987";

        Usuario previo = new Usuario();
        previo.setLogin(login);
        previo.setNombre("Previo");
        previo.setPassword("xpto12345");
        usuarios.save(previo);

        Exception ex = assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, password)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("ya existe") || ex.getMessage().toLowerCase().contains("existe"));
    }

    @Test
    void fallaSiPasswordInvalidoEnBD() throws Exception {
        usuarios.deleteAll();

        String login = "bd_short_pwd";
        String nombre = "Pwd Corta";

        // password nulo
        Exception e1 = assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, null)
        );
        assertTrue(e1.getMessage().toLowerCase().contains("contraseña") || e1.getMessage().toLowerCase().contains("password") || e1.getMessage().toLowerCase().contains("contrase"));

        // password demasiado corto
        Exception e2 = assertThrows(Exception.class, () ->
            usuarioService.registrarNuevoUsuario(login, nombre, "12345")
        );
        assertTrue(e2.getMessage().toLowerCase().contains("contraseña") || e2.getMessage().toLowerCase().contains("password") || e2.getMessage().toLowerCase().contains("contrase"));
    }

}
