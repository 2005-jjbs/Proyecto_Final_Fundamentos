package com.ingesoft.redsocial.servicios;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarios;


    // CU01 - Registrar nuevo usuario (nuevo flujo por correo)
    public void registrarNuevoUsuario(
        String correo,
        String nombre,
        String tipoVinculacion,
        String password
    ) throws Exception {

        // 1. Sistema valida que el correo sea institucional (ejemplo @javeriana.edu.co)
        if (correo == null || !correo.endsWith("@javeriana.edu.co")) {
            throw new Exception("Debe usar su correo institucional @javeriana.edu.co");
        }

        // 2. Sistema verifica que no exista otro usuario con ese correo
        if (usuarios.existsByCorreo(correo)) {
            throw new Exception("Ya existe otra cuenta con ese correo institucional");
        }

        // 5. Sistema valida que el password tenga más de 8 caracteres, incluya un número y una mayúscula
        if (password == null || password.length() < 8
            || !Pattern.compile(".*\\d.*").matcher(password).matches()
            || !Pattern.compile(".*[A-Z].*").matcher(password).matches()) {
            throw new Exception("La contraseña debe tener mínimo 8 caracteres, incluir un número y una letra mayúscula");
        }

        // 6. Sistema crea el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setNombre(nombre);
        usuario.setTipoVinculacion(tipoVinculacion);
        usuario.setPassword(password);
        usuario.setFechaRegistro(java.time.LocalDateTime.now());
        usuario.setVerificado(false);

        usuarios.save(usuario);

    }

    // --- Sobrecarga para compatibilidad con tests antiguos: registrar por login ---
    public void registrarNuevoUsuario(
        String login,
        String nombre,
        String password
    ) throws Exception {

        // 2. Sistema verifica que no exista otro usuario con ese login
        if (usuarios.existsById(login)) {
            throw new Exception("Ya existe otro usuario con ese login");
        }

        // 5. Sistema valida que el password tenga más de 5 letras (comportamiento previo)
        if (password == null || password.length() <= 5) {
            throw new Exception("La contraseña no cumple con la política de la red social");
        }

        // 6. Sistema crea el nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setLogin(login);
        usuario.setNombre(nombre);
        usuario.setPassword(password);
        usuarios.save(usuario);
    }


    // CU02 - Iniciar sesión (nuevo flujo por correo)
    public void iniciarSesionPorCorreo (
        String correo,
        String password
    ) throws Exception {

        // 2. Sistema verifica que exista un usuario con ese correo
        Optional<Usuario> buscado = usuarios.findByCorreo(correo);
        if (buscado.isEmpty()) {
            throw new Exception("No existe un usuario con ese correo");
        }

        // 4. Sistema verifica que el password coincida con el password de ese usuario
        Usuario usuario = buscado.get();
        if (!password.equals(usuario.getPassword())) {
            throw new Exception("No coincide el password");
        }

        // 5. Sistema establece el usuario actual (pendiente sesión)

    }

    // Versión original sin el tercer parámetro (firma exacta esperada por tests)
    public void iniciarSesion (
        String login,
        String password
    ) throws Exception {
        // 2. Sistema verifica que exista un usuario con ese login
        if (!usuarios.existsById(login)) {
            throw new Exception("No existe un usuario con ese login");
        }

        // 4. Sistema verifica que el password coincida con el password de ese usuario
        Usuario usuario = usuarios.findById(login).orElseThrow(() -> new Exception("No existe un usuario con ese login"));
        if (!password.equals(usuario.getPassword())) {
            throw new Exception("No coincide el password");
        }

        // 5. Sistema establece el usuario actual
    }


    // CU03 - Buscar persona
    public List<Usuario> buscarPersona (
        String nombre
    ) throws Exception {
        
        // 2. Sistema busca usuarios con ese nombre
        List<Usuario> personas = usuarios.findByNombreContains(nombre);

        if (personas == null || personas.isEmpty()) {
            throw new Exception("No se encuentran personas con ese nombre");
        }

        // 3. Sistema muestra el login y nombre de los usuarios 
        return personas;
    }

}
