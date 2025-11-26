package com.ingesoft.redsocial.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String contenido;

    LocalDateTime fechaEnvio;

    Boolean leido;

    @ManyToOne
    Usuario remitente;

    @ManyToOne
    Usuario destinatario;

}

