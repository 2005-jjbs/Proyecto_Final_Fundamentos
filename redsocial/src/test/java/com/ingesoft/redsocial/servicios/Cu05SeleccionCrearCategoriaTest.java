package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.Categoria;
import com.ingesoft.redsocial.modelo.Estado;
import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.Ubicacion;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.CategoriaRepository;
import com.ingesoft.redsocial.repositorios.EstadoRepository;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.UbicacionRepository;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@SpringBootTest
@Transactional
public class Cu05SeleccionCrearCategoriaTest {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    UbicacionRepository ubicacionRepository;

    @Test
    void crearCategoriaYUsarlaEnProducto_exito() throws Exception {
        categoriaRepository.deleteAll();
        productoRepository.deleteAll();
        usuarioRepository.deleteAll();
        estadoRepository.deleteAll();
        ubicacionRepository.deleteAll();

        Usuario u = new Usuario();
        u.setLogin("vendedor_cat");
        u.setNombre("Vendedor Cat");
        u.setPassword("Pwd12345");
        usuarioRepository.save(u);

        Categoria cat = new Categoria();
        cat.setNombre("Artículos de prueba");
        categoriaRepository.save(cat);

        Estado est = new Estado();
        est.setNombre("Nuevo");
        estadoRepository.save(est);

        Ubicacion ub = new Ubicacion();
        ub.setUniversidad("Sede Prueba");
        ubicacionRepository.save(ub);

        Producto p = new Producto();
        p.setTitulo("Objeto de prueba");
        p.setDescripcion("Descripción");
        p.setPrecio(1000.0);
        p.setCategoria(cat);
        p.setEstado(est);
        p.setUbicacion(ub);
        p.setActivo(true);
        p.setPropietario(u);

        Producto creado = productoRepository.save(p);

        assertNotNull(creado.getId());
        assertNotNull(creado.getCategoria());
        assertEquals("Artículos de prueba", creado.getCategoria().getNombre());
    }

}

