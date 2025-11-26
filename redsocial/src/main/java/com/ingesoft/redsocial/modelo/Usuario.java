package com.ingesoft.redsocial.modelo;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {

    @Id
    String login;

    String correo;

    String nombre;

    String password;

    String tipoVinculacion;

    LocalDateTime fechaRegistro;

    Boolean verificado;

    @OneToMany(mappedBy = "propietario")
    List<Producto> productosPublicados;

    @OneToMany(mappedBy = "comprador")
    List<Transaccion> transaccionesCompra;

    @OneToMany(mappedBy = "emisor")
    List<Calificacion> calificacionesEmitidas;

    @OneToMany(mappedBy = "receptor")
    List<Calificacion> calificacionesRecibidas;

    @OneToMany(mappedBy = "remitente")
    List<Mensaje> mensajesEnviados;

    @OneToMany(mappedBy = "destinatario")
    List<Mensaje> mensajesRecibidos;

    // Constructor
    public Usuario() {
    }

    // Getters y Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoVinculacion() {
        return tipoVinculacion;
    }

    public void setTipoVinculacion(String tipoVinculacion) {
        this.tipoVinculacion = tipoVinculacion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

}
