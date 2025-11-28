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
  - categoria: Categoria
  - estado: Estado
  - ubicacion: Ubicacion
  - fechaPublicacion: LocalDateTime
  - activo: Boolean
}

class Categoria {
  - id: Long
  - nombre: String
  - descripcion: String
  - activo: Boolean
}

class Ubicacion {
  - id: Long
  - universidad: String
}

class Estado {
  - id: Long
  - nuevo/usado: String
}

class Transaccion {
  - id: Long
  - fechaTransaccion: LocalDateTime
  - estado: String
  - montoTotal: Double
  - metodoPago: MetodoPago
}

class MetodoPago {
  - id: Long
  - nombre: String
  - numeroCuenta: Long
}

' Relaciones principales
Usuario "1" -- "*" Producto : publica
Usuario "1" -- "*" Transaccion : compra
Producto "*" -- "1" Categoria : pertenece_a
Producto "*" -- "1" Ubicacion : ubicado_en
Producto "*" -- "1" Estado : tiene_estado
Transaccion "*" -- "1" MetodoPago : usa

@enduml
```
