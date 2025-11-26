package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@SpringBootTest
@Transactional
class Cu04VisualizarCatalogoTest {

    @Autowired
    ProductoService productoService;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void listarProductos_exito() throws Exception {
        productoRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario u = new Usuario();
        u.setLogin("vendedor2");
        u.setNombre("Vendedor 2");
        u.setPassword("Vendedor2");
        usuarioRepository.save(u);

        Producto p = new Producto();
        p.setTitulo("Auriculares Bluetooth");
        p.setDescripcion("Auriculares en buen estado");
        p.setPrecio(120000.0);
        p.setCategoria("Electrónica");
        p.setEstado("Nuevo");
        p.setUbicacion("Cali");
        p.setActivo(true);
        p.setPropietario(u);

        productoService.crear(p);

        List<Producto> todos = productoService.listarTodos();
        assertFalse(todos.isEmpty());

        boolean encontrado = todos.stream().anyMatch(x -> "Auriculares Bluetooth".equals(x.getTitulo()));
        assertTrue(encontrado, "El producto creado debe aparecer en el catálogo");
    }

}
