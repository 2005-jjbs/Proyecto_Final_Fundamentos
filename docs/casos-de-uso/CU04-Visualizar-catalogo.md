# CU04 - Visualizar catálogo de productos

**Actor:** Usuario registrado

## Guión (Curso normal de eventos)

1. Usuario selecciona la opción "Catálogo de productos" en el menú principal
2. Sistema muestra una lista de productos disponibles con imagen, título, precio y ubicación
3. Usuario aplica filtros (categoría, precio, ubicación o estado)
4. Sistema actualiza la lista según los filtros aplicados
5. Usuario selecciona un producto específico
6. Sistema muestra los detalles completos del producto (descripción, fotos, vendedor, contacto)

## Excepciones (Caminos alternos)

**Excepción:** No hay productos disponibles

2.1. Sistema muestra mensaje "No hay productos disponibles actualmente"
2.2. Sistema sugiere "Publicar un producto" si el usuario es vendedor
2.3. Termina

**Excepción:** Búsqueda sin resultados

4.1. Sistema muestra mensaje "No se encontraron productos con esos criterios"
4.2. Sistema sugiere ajustar los filtros de búsqueda
4.3. Usuario puede modificar o eliminar filtros

**Excepción:** Usuario no autenticado

1.1. Sistema redirige al inicio de sesión
1.2. Después de autenticarse, sistema redirige al catálogo de productos
1.3. Termina

**Excepción:** Error al cargar productos

2.4. Sistema muestra mensaje "Error al cargar el catálogo. Intente nuevamente"
2.5. Sistema ofrece botón "Reintentar"
2.6. Termina


<br>

---

> [Listado de casos de uso](../casos-de-uso.md)
