// ...existing code...
package com.ingesoft.redsocial.servicios;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.redsocial.modelo.MetodoPago;
import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.Transaccion;
import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.repositorios.MetodoPagoRepository;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.TransaccionRepository;
import com.ingesoft.redsocial.repositorios.UsuarioRepository;

@Service
public class TransaccionService {

    @Autowired
    ProductoRepository productoRepo;

    @Autowired
    MetodoPagoRepository metodoPagoRepo;

    @Autowired
    TransaccionRepository transRepo;

    @Autowired
    UsuarioRepository usuarioRepo;

    @Transactional
    public Transaccion procesarPago(Long productoId, Long metodoPagoId, String compradorLogin) throws Exception {
        if (productoId == null) throw new Exception("Producto inválido");
        if (metodoPagoId == null) throw new Exception("Método de pago inválido");
        if (compradorLogin == null) throw new Exception("Usuario no autenticado");

        Optional<Producto> productoOpt = productoRepo.findById(productoId);
        if (productoOpt.isEmpty()) throw new Exception("Producto no encontrado");
        Producto producto = productoOpt.get();
        if (producto.getActivo() == null || !producto.getActivo()) throw new Exception("Producto no disponible");

        Optional<MetodoPago> mpOpt = metodoPagoRepo.findById(metodoPagoId);
        if (mpOpt.isEmpty()) throw new Exception("Método de pago no encontrado");
        MetodoPago metodo = mpOpt.get();

        Usuario comprador = usuarioRepo.findById(compradorLogin).orElseThrow(() -> new Exception("Comprador no encontrado"));

        Transaccion t = new Transaccion();
        t.setProducto(producto);
        t.setMetodoPago(metodo);
        t.setComprador(comprador);
        t.setMontoTotal(producto.getPrecio());
        t.setFechaTransaccion(LocalDateTime.now());
        t.setEstado("PAGADA");

        Transaccion guardada = transRepo.save(t);

        // marcar producto como inactivo/reservado
        producto.setActivo(false);
        productoRepo.save(producto);

        return guardada;
    }

}
// ...existing code...

