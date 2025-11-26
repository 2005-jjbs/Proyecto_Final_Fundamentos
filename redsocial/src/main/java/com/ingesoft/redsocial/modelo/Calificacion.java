package com.ingesoft.redsocial.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer puntuacion;

    String comentario;

    LocalDateTime fechaCalificacion;

    @ManyToOne
    Usuario emisor;

    @ManyToOne
    Usuario receptor;

    @OneToOne
    Transaccion transaccion;

}

