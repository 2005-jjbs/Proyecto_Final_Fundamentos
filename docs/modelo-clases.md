# Modelo de Clases

## Diagrama de Clases

```plantuml
@startuml
class Usuario {
  - id: Long
  - correo: String
  - nombre: String
  - password: String
  - tipoVinculacion: String
  - fechaRegistro: LocalDateTime
  - verificado: Boolean
}

class Producto {
  - id: Long
  - titulo: String
  - descripcion: String
  - precio: Double
  - categoria: String
  - estado: String
  - ubicacion: String
  - fechaPublicacion: LocalDateTime
  - activo: Boolean
}

class Transaccion {
  - id: Long
  - fechaTransaccion: LocalDateTime
  - estado: String
  - montoTotal: Double
  - metodoPago: String
}

class Calificacion {
  - id: Long
  - puntuacion: Integer
  - comentario: String
  - fechaCalificacion: LocalDateTime
}

class Mensaje {
  - id: Long
  - contenido: String
  - fechaEnvio: LocalDateTime
  - leido: Boolean
}

' Relaciones principales
Usuario "1" -- "*" Producto : publica
Usuario "1" -- "*" Transaccion : compra
Producto "1" -- "*" Transaccion : incluido_en
Usuario "*" -- "*" Usuario : contacta >
Usuario "1" -- "*" Calificacion : emite
Usuario "1" -- "*" Calificacion : recibe
Transaccion "1" -- "1" Calificacion : tiene
Usuario "1" -- "*" Mensaje : envía
Usuario "1" -- "*" Mensaje : recibe

@enduml
```
