# CU06 - Generar Transacción (Compra de producto)

**Actor principal:** Usuario comprador  
**Actores secundarios:** Usuario vendedor, Sistema de pago (externo)

## Precondición

- El usuario comprador está autenticado.
- El producto está activo y disponible en el catálogo.
- Existen medios de pago habilitados en la plataforma.

## Guion (Curso normal de eventos)

1. El usuario visualiza el catálogo y selecciona el producto que desea comprar.
2. El sistema muestra la página de detalle del producto con información de precio, vendedor y opciones de compra.
3. El usuario inicia el proceso de compra y selecciona el medio de pago (tarjeta, transferencia).
4. El sistema solicita los datos necesarios para el medio de pago seleccionado (si aplica) y confirma el monto total.
5. El usuario confirma la compra.
6. El sistema procesa el pago mediante el método elegido ("integración con pasarela").
7. Si el pago es autorizado, el sistema crea el registro de transacción asociando comprador, vendedor, producto, monto y estado (pendiente/confirmada).
8. El sistema actualiza el estado del producto (por ejemplo: marcado como reservado o inactivo según la política) y registra la relación con la transacción.
9. El sistema notifica al comprador y al vendedor sobre el resultado de la operación.
10. El sistema muestra al usuario comprador la confirmación de la transacción y los pasos siguientes (entrega, contacto, comprobante).

---

## Excepciones (Caminos alternos)

**E1 – Producto ya no disponible**  
1.1. Al intentar iniciar la compra, el sistema informa que el producto ya no está disponible.  
1.2. El flujo termina o el usuario puede regresar al catálogo.

**E2 – Medio de pago inválido o no disponible**  
2.1. El sistema muestra las opciones válidas de pago y un mensaje indicando que el seleccionado no está disponible.  
2.2. El usuario selecciona otro medio de pago o cancela.

**E3 – Pago rechazado o error en la pasarela**  
3.1. El sistema informa al usuario que el pago fue rechazado o que ocurrió un error de procesamiento.  
3.2. El usuario puede reintentar con el mismo u otro medio de pago.

**E4 – Error al actualizar el estado del producto o crear la transacción**  
4.1. El sistema muestra un error: "No fue posible completar la compra. Intente nuevamente.".  
4.2. Se debe garantizar la idempotencia del proceso para evitar cobros duplicados.

---

## Postcondición

- Se genera un registro de transacción (si el pago fue autorizado) con la referencia correspondiente.
- El producto queda asociado a la transacción y su disponibilidad se ajusta según la política.
- Comprador y vendedor quedan notificados.

> [Listado de casos de uso](../casos-de-uso.md)
