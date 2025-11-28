# CU03 – Registrar Nuevo Producto (Crear Publicación)

Actor: Usuario vendedor

## Guion (Curso normal de eventos)

1. El usuario inicia el proceso de creación de una nueva publicación en el Marketplace.
2. El sistema presenta la interfaz o formulario para registrar la publicación.
3. El usuario completa el nombre del producto.
4. El usuario selecciona la categoría del producto.
5. El usuario ingresa el precio.
6. El usuario ingresa la descripción del producto.
7. El usuario adjunta una o más imágenes u otros archivos multimedia.
8. El sistema valida que los datos y archivos cumplan las reglas (campos obligatorios, formatos y tamaños).
9. Si la validación es exitosa, el sistema registra la nueva publicación.
10. El sistema notifica al usuario que la publicación fue creada con éxito.
11. La publicación queda disponible en el catálogo.

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
