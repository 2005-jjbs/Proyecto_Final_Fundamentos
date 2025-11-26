package com.ingesoft.redsocial.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Transaccion;
import com.ingesoft.redsocial.repositorios.TransaccionRepository;

@Service
public class TransaccionService {

    @Autowired
    TransaccionRepository repositorio;

    public Transaccion crear(Transaccion t) {
        return repositorio.save(t);
    }

    public Optional<Transaccion> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Transaccion> listarTodos() {
        return repositorio.findAll();
    }

    public Transaccion actualizar(Transaccion t) {
        return repositorio.save(t);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

}

