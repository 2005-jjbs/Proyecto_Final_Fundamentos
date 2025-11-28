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

    @ManyToOne
    Categoria categoria;

    @ManyToOne
    Estado estado;

    @ManyToOne
    Ubicacion ubicacion;

    LocalDateTime fechaPublicacion;

    Boolean activo;

    @ManyToOne
    Usuario propietario;

    @OneToMany(mappedBy = "producto")
    List<Transaccion> transacciones;

}
