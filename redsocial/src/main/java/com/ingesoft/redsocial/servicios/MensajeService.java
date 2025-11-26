package com.ingesoft.redsocial.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Mensaje;
import com.ingesoft.redsocial.repositorios.MensajeRepository;

@Service
public class MensajeService {

    @Autowired
    MensajeRepository repositorio;

    public Mensaje crear(Mensaje m) {
        return repositorio.save(m);
    }

    public Optional<Mensaje> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Mensaje> listarTodos() {
        return repositorio.findAll();
    }

    public Mensaje actualizar(Mensaje m) {
        return repositorio.save(m);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

}

