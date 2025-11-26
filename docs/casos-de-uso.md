# Casos de Uso

## Diagrama de Casos de Uso

```plantuml
@startuml
left to right direction
skin rose

actor Visitante
actor "Usuario Registrado" as Usuario

rectangle "JMark - Marketplace Universitario" {

    Visitante --> (Registrar nuevo usuario)
    Visitante --> (Iniciar sesión)
    
    Usuario --> (Visualizar catálogo de productos)
    Usuario --> (Buscar productos)
    Usuario --> (Ver detalles de producto)

}
@enduml
```

## Listado de Casos de Uso

| #    | Nombre                                                                       |
|------|------------------------------------------------------------------------------|
| CU01 | [Registrar nuevo usuario](casos-de-uso/CU01-Registrar-nuevo-usuario.md)      |
| CU02 | [Iniciar sesión](casos-de-uso/CU02-Iniciar-sesion.md)                        |
| CU03 | [Registrar nuevos productos](casos-de-uso/CU03-Registrar-Producto.md)        |
| CU04 | [Visualizar catalogo de productos](casos-de-uso/CU04-Visualizar-catalogo.md) |
