package com.ingesoft.redsocial;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.Categoria;
import com.ingesoft.redsocial.modelo.Estado;
import com.ingesoft.redsocial.modelo.Ubicacion;
import com.ingesoft.redsocial.modelo.MetodoPago;

import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.CategoriaRepository;
import com.ingesoft.redsocial.repositorios.EstadoRepository;
import com.ingesoft.redsocial.repositorios.UbicacionRepository;
import com.ingesoft.redsocial.repositorios.MetodoPagoRepository;

import jakarta.transaction.Transactional;

@Component
public class InicializadorDatosEjemplo implements CommandLineRunner {

    UsuarioRepository usuarioRepository;
    ProductoRepository productoRepository;
    CategoriaRepository categoriaRepository;
    EstadoRepository estadoRepository;
    UbicacionRepository ubicacionRepository;
    MetodoPagoRepository metodoPagoRepository;

    InicializadorDatosEjemplo(UsuarioRepository usuarioRepository,
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            EstadoRepository estadoRepository,
            UbicacionRepository ubicacionRepository,
            MetodoPagoRepository metodoPagoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.estadoRepository = estadoRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.metodoPagoRepository = metodoPagoRepository;
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

        // Crear categorías, estados, ubicaciones y métodos de pago
        Categoria catPapeleria = new Categoria();
        catPapeleria.setNombre("Papelería");
        catPapeleria.setDescripcion("Artículos de papelería");
        catPapeleria.setActivo(true);
        categoriaRepository.save(catPapeleria);

        Categoria catElectronicos = new Categoria();
        catElectronicos.setNombre("Electrónicos");
        catElectronicos.setDescripcion("Aparatos y accesorios");
        catElectronicos.setActivo(true);
        categoriaRepository.save(catElectronicos);

        Estado estadoUsado = new Estado();
        estadoUsado.setNombre("Usado");
        estadoRepository.save(estadoUsado);

        Estado estadoNuevo = new Estado();
        estadoNuevo.setNombre("Nuevo");
        estadoRepository.save(estadoNuevo);

        Ubicacion ubBogota = new Ubicacion();
        ubBogota.setUniversidad("Pontificia Universidad Javeriana - Bogotá");
        ubicacionRepository.save(ubBogota);

        MetodoPago mpTarjeta = new MetodoPago();
        mpTarjeta.setNombre("Tarjeta de crédito");
        mpTarjeta.setNumeroCuenta(1234123412341234L);
        metodoPagoRepository.save(mpTarjeta);

        MetodoPago mpTransfer = new MetodoPago();
        mpTransfer.setNombre("Transferencia bancaria");
        mpTransfer.setNumeroCuenta(123456789L);
        metodoPagoRepository.save(mpTransfer);

        // Productos de ejemplo vinculando entidades
        Producto p1 = new Producto();
        p1.setTitulo("Libreta Moleskine");
        p1.setDescripcion("Libreta negra tamaño A5 en buen estado.");
        p1.setPrecio(25000.0);
        p1.setCategoria(catPapeleria);
        p1.setEstado(estadoUsado);
        p1.setUbicacion(ubBogota);
        p1.setFechaPublicacion(java.time.LocalDateTime.now());
        p1.setActivo(true);
        p1.setPropietario(u1);
        productoRepository.save(p1);

        Producto p2 = new Producto();
        p2.setTitulo("Calculadora científica");
        p2.setDescripcion("Calculadora en perfecto estado, modelo científico.");
        p2.setPrecio(80000.0);
        p2.setCategoria(catElectronicos);
        p2.setEstado(estadoNuevo);
        p2.setUbicacion(ubBogota);
        p2.setFechaPublicacion(java.time.LocalDateTime.now());
        p2.setActivo(true);
        p2.setPropietario(u2);
        productoRepository.save(p2);

    }

}
