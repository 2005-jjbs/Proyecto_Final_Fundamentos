# CU01 - Registrar nuevo usuario

**Actor:** Visitante (Estudiante, Profesor o Egresado)

## Guión (Curso normal de eventos)

1. Actor ingresa su correo institucional (@javeriana.edu.co)
2. Sistema verifica que no exista otro usuario con ese correo
3. Actor ingresa su nombre completo
4. Actor ingresa su tipo de vinculación (Estudiante/Profesor/Egresado)
5. Actor ingresa su password
6. Sistema valida que el password tenga más de 8 caracteres con al menos un número y una mayúscula
7. Sistema crea el nuevo usuario con perfil universitario
8. Sistema envía correo de confirmación a la dirección institucional

## Excepciones (Caminos alternos)

**Excepción:** Ya exista otro usuario con ese correo institucional

- 2.1. Sistema muestra mensaje "Ya existe una cuenta con este correo institucional"
- 2.2. Sistema sugiere recuperación de contraseña
- 2.3. Termina

**Excepción:** El password no cumple con las reglas de seguridad

6.1. Sistema muestra mensaje "La contraseña debe tener mínimo 8 caracteres, incluir un número y una letra mayúscula"
6.2. Sistema mantiene los datos del formulario excepto la contraseña
6.3. Actor puede corregir la contraseña

**Excepción:** Correo no institucional

1.1. Sistema válida el dominio del correo
1.2. Sistema muestra mensaje "Debe usar su correo institucional @javeriana.edu.co"
1.3. Termina
<br>

---

> [Listado de casos de uso](../casos-de-uso.md)
