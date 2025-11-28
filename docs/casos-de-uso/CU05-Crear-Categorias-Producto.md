# CU05 - Seleccionar o Crear Categoría en formulario de producto

**Actor:** Usuario vendedor

## Precondición

- El usuario está autenticado y accede al formulario de creación de producto.
- Existe un listado de categorías cargadas en el sistema (puede estar vacío).

## Guión (Curso normal de eventos)

1. El usuario inicia el proceso de creación de una nueva publicación.
2. El sistema muestra el formulario para registrar la publicación, incluyendo el campo de categoría con un menú de categorías existentes y la opción "Otra".
3. El usuario selecciona una categoría del menú.
4. Si el usuario selecciona "Otra", el sistema despliega un campo para ingresar el nombre de la nueva categoría.
5. El usuario ingresa el nombre de la nueva categoría (cuando corresponde) o confirma la selección de la categoría existente.
6. El sistema válida que la categoría ingresada o seleccionada sea válida (no esté vacía y cumpla reglas de formato) y que, si es nueva, no sea duplicada.
7. Si la validación es exitosa, el sistema asigna la categoría al producto en creación.
8. Si corresponde, el sistema registra la nueva categoría en el catálogo de categorías.
9. El usuario continúa con el resto del formulario y completa la creación del producto.
10. El sistema muestra confirmación y la categoría queda asociada al producto.

---

## Excepciones (Caminos alternos)

**E1 – No hay categorías disponibles**
1.1. El menú de categorías está vacío; el sistema muestra la opción "Otra" y un mensaje que indica que no hay categorías predefinidas.

1.2. El usuario ingresa una nueva categoría.

1.3. El flujo continúa en el paso 6 del guión.

**E2 – Nombre de categoría vacío o formato inválido**
2.1. El sistema muestra un mensaje indicando que el nombre de categoría es obligatorio o que el formato no es válido.

2.2. El usuario corrige la entrada.

2.3. El flujo continúa cuando la entrada es válida.

**E3 – Categoría ya existente (al intentar crear una nueva)**
3.1. El sistema informa que la categoría ya existe y sugiere seleccionarla desde la lista.

3.2. El usuario puede elegir la categoría existente o ingresar otro nombre.

**E4 – Error al guardar la nueva categoría**
4.1. El sistema muestra un mensaje de error: "No fue posible guardar la categoría. Intente nuevamente.".

4.2. El usuario puede reintentar o cancelar la creación de la categoría.

---

## Postcondición

- El producto en creación queda asociado a una categoría (existente o recién creada).
- Si se creó una categoría nueva, queda registrada en el catálogo de categorías del sistema (sujeta a las políticas del sistema, por ejemplo, aprobación administrativa si aplica).

> [Listado de casos de uso](../casos-de-uso.md)
