# CU03 – Registrar Nuevo Producto (Crear Publicación)

Actor: Usuario vendedor

## Guion (Curso normal de eventos)

1. El usuario selecciona la opción **“Crear producto”** en el Marketplace.
2. El usuario ingresa el nombre del producto.
3. El usuario selecciona la categoría del producto.
4. El usuario ingresa el precio.
5. El usuario ingresa la descripción del producto.
6. El usuario adjunta una o más imágenes del producto.
7. El sistema valida que todos los datos requeridos estén completos y tengan un formato válido.
8. Si todo es correcto, el sistema registra la nueva publicación.
9. El sistema muestra un mensaje indicando que la publicación fue creada con éxito.
10. El producto aparece disponible en el catálogo general.

---

## Excepciones (Caminos alternos)

**E1 – Campos obligatorios incompletos**  
8.1. El sistema muestra un mensaje indicando el campo faltante.  
8.2. El sistema no permite publicar.  
8.3. Termina.

**E2 – Imagen no válida (peso o formato incorrecto)**  
7.1. El sistema muestra: *“Formato o tamaño de imagen no permitido.”*  
7.2. El usuario debe adjuntar otra imagen válida.

**E3 – Datos inválidos (precio negativo, caracteres no permitidos, etc.)**  
8.1. El sistema muestra el mensaje de error correspondiente.  
8.2. El flujo continúa cuando el usuario corrige la información.

---

## Postcondición

- El nuevo producto queda registrado y visible en el catálogo del Marketplace.

> [Regresar al diagrama](../casos-de-uso.md)
