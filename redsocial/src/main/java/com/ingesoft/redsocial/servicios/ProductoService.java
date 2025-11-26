package com.ingesoft.redsocial.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.repositorios.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository repositorio;

    public Producto crear(Producto p) {
        return repositorio.save(p);
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Producto> listarTodos() {
        return repositorio.findAll();
    }

    public Producto actualizar(Producto p) {
        return repositorio.save(p);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

}

