package com.ingesoft.redsocial.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}

