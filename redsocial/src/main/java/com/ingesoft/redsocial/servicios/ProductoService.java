package com.ingesoft.redsocial.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.redsocial.modelo.Producto;
import com.ingesoft.redsocial.modelo.Categoria;
import com.ingesoft.redsocial.modelo.Ubicacion;
import com.ingesoft.redsocial.modelo.Estado;
import com.ingesoft.redsocial.repositorios.ProductoRepository;
import com.ingesoft.redsocial.repositorios.CategoriaRepository;
import com.ingesoft.redsocial.repositorios.UbicacionRepository;
import com.ingesoft.redsocial.repositorios.EstadoRepository;

@Service
public class ProductoService {

    private final ProductoRepository repositorio;

    @Autowired
    CategoriaRepository categoriaRepo;

    @Autowired
    UbicacionRepository ubicacionRepo;

    @Autowired
    EstadoRepository estadoRepo;

    @Autowired
    public ProductoService(ProductoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Producto crear(Producto p) throws Exception {
        // validar categoria, ubicacion y estado si vienen referenciados
        if (p.getCategoria() != null && p.getCategoria().getId() != null) {
            Optional<Categoria> c = categoriaRepo.findById(p.getCategoria().getId());
            if (c.isEmpty()) throw new Exception("Categoría no encontrada");
            p.setCategoria(c.get());
        }

        if (p.getUbicacion() != null && p.getUbicacion().getId() != null) {
            Optional<Ubicacion> u = ubicacionRepo.findById(p.getUbicacion().getId());
            if (u.isEmpty()) throw new Exception("Ubicación no encontrada");
            p.setUbicacion(u.get());
        }

        if (p.getEstado() != null && p.getEstado().getId() != null) {
            Optional<Estado> e = estadoRepo.findById(p.getEstado().getId());
            if (e.isEmpty()) throw new Exception("Estado no encontrado");
            p.setEstado(e.get());
        }

        return repositorio.save(p);
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Producto> listarTodos() {
        return repositorio.findAll();
    }

    public Producto actualizar(Producto p) throws Exception {
        // Reutilizar validaciones de creación
        return crear(p);
    }

    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
