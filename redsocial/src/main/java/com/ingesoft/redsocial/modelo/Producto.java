package com.ingesoft.redsocial.modelo;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ingesoft.redsocial.modelo.Usuario;
import com.ingesoft.redsocial.modelo.Transaccion;

@Entity
@Data
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titulo;

    String descripcion;

    Double precio;

    String categoria;

    String estado;

    String ubicacion;

    LocalDateTime fechaPublicacion;

    Boolean activo;

    @ManyToOne
    Usuario propietario;

    @OneToMany(mappedBy = "producto")
    List<Transaccion> transacciones;

}
