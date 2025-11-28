package com.ingesoft.redsocial.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Categoria;
import com.ingesoft.redsocial.repositorios.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repositorio;

    public Categoria crear(Categoria c) {
        return repositorio.save(c);
    }

    public Optional<Categoria> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Categoria> listarTodos() {
        return repositorio.findAll();
    }

    public Categoria actualizar(Categoria c) {
        return repositorio.save(c);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

}

