package com.ingesoft.redsocial.servicios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.MetodoPago;
import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.Transaccion;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.MetodoPagoRepository;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.TransaccionRepository;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;
import com.ingesoft.redsocial.servicios.TransaccionService;

@SpringBootTest
@Transactional
public class Cu06GenerarTransaccionTest {

    @Autowired
    TransaccionService transaccionService;

    @Autowired
    TransaccionRepository transaccionRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    MetodoPagoRepository metodoPagoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    void generarTransaccion_exito() throws Exception {
        transaccionRepository.deleteAll();
        productoRepository.deleteAll();
        metodoPagoRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario vendedor = new Usuario();
        vendedor.setLogin("seller1");
        vendedor.setNombre("Seller One");
        vendedor.setPassword("Seller123");
        usuarioRepository.save(vendedor);

        Usuario comprador = new Usuario();
        comprador.setLogin("buyer1");
        comprador.setNombre("Buyer One");
        comprador.setPassword("Buyer123");
        usuarioRepository.save(comprador);

        MetodoPago mp = new MetodoPago();
        mp.setNombre("PagoTest");
        mp.setNumeroCuenta(111222333L);
        metodoPagoRepository.save(mp);

        Producto producto = new Producto();
        producto.setTitulo("Objeto Test");
        producto.setDescripcion("Descripción");
        producto.setPrecio(5000.0);
        producto.setActivo(true);
        producto.setPropietario(vendedor);
        productoRepository.save(producto);

        Transaccion t = transaccionService.procesarPago(producto.getId(), mp.getId(), comprador.getLogin());

        assertNotNull(t);
        assertNotNull(t.getId());
        assertEquals(producto.getId(), t.getProducto().getId());
        assertEquals(mp.getId(), t.getMetodoPago().getId());
        assertEquals(comprador.getLogin(), t.getComprador().getLogin());

        Producto productoActualizado = productoRepository.findById(producto.getId()).orElseThrow();
        assertFalse(productoActualizado.getActivo(), "El producto debe quedar inactivo después de la compra");
    }

}

