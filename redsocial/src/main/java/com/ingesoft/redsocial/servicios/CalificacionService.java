package com.ingesoft.redsocial.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Calificacion;
import com.ingesoft.redsocial.repositorios.CalificacionRepository;

@Service
public class CalificacionService {

    @Autowired
    CalificacionRepository repositorio;

    public Calificacion crear(Calificacion c) {
        return repositorio.save(c);
    }

    public Optional<Calificacion> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Calificacion> listarTodos() {
        return repositorio.findAll();
    }

    public Calificacion actualizar(Calificacion c) {
        return repositorio.save(c);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

}

