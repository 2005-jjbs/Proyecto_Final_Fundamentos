package com.ingesoft.redsocial;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.repositorios.ProductoRepository;

import jakarta.transaction.Transactional;

@Component
public class InicializadorDatosEjemplo implements CommandLineRunner {

    UsuarioRepository usuarioRepository;
    ProductoRepository productoRepository;

    InicializadorDatosEjemplo(UsuarioRepository usuarioRepository, ProductoRepository productoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
   
        // Carga datos iniciales: usuarios
        Usuario u1 = new Usuario();
        u1.setLogin("user1");
        u1.setNombre("Usuario 1");
        u1.setPassword("Password1");
        u1.setCorreo("user1@javeriana.edu.co");
        u1.setTipoVinculacion("Estudiante");
        u1.setFechaRegistro(java.time.LocalDateTime.now());
        u1.setVerificado(true);
        usuarioRepository.save(u1);

        Usuario u2 = new Usuario();
        u2.setLogin("user2");
        u2.setNombre("Usuario 2");
        u2.setPassword("Password2");
        u2.setCorreo("user2@javeriana.edu.co");
        u2.setTipoVinculacion("Profesor");
        u2.setFechaRegistro(java.time.LocalDateTime.now());
        u2.setVerificado(true);
        usuarioRepository.save(u2);

        Usuario u3 = new Usuario();
        u3.setLogin("user3");
        u3.setNombre("Usuario 3");
        u3.setPassword("Password3");
        u3.setCorreo("user3@javeriana.edu.co");
        u3.setTipoVinculacion("Egresado");
        u3.setFechaRegistro(java.time.LocalDateTime.now());
        u3.setVerificado(true);
        usuarioRepository.save(u3);

        // Productos de ejemplo
        Producto p1 = new Producto();
        p1.setTitulo("Libreta Moleskine");
        p1.setDescripcion("Libreta negra tamaño A5 en buen estado.");
        p1.setPrecio(25000.0);
        p1.setCategoria("Papelería");
        p1.setEstado("Usado");
        p1.setUbicacion("Bogotá");
        p1.setFechaPublicacion(java.time.LocalDateTime.now());
        p1.setActivo(true);
        p1.setPropietario(u1);
        productoRepository.save(p1);

        Producto p2 = new Producto();
        p2.setTitulo("Calculadora científica");
        p2.setDescripcion("Calculadora en perfecto estado, modelo científico.");
        p2.setPrecio(80000.0);
        p2.setCategoria("Electrónicos");
        p2.setEstado("Nuevo");
        p2.setUbicacion("Bogotá");
        p2.setFechaPublicacion(java.time.LocalDateTime.now());
        p2.setActivo(true);
        p2.setPropietario(u2);
        productoRepository.save(p2);

    }

}
