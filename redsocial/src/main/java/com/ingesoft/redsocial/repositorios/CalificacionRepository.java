package com.ingesoft.redsocial.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.redsocial.modelo.Calificacion;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

}

