package com.ingesoft.redsocial.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.modelo.Producto;

@Entity
@Data
@NoArgsConstructor
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    LocalDateTime fechaTransaccion;

    String estado;

    Double montoTotal;

    String metodoPago;

    @ManyToOne
    Usuario comprador;

    @ManyToOne
    Producto producto;

}
