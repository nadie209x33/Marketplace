### **-- Capa de Datos --**

**Regla General**: Toda operación debe devolver un objeto indicando el estado del resultado.
*(Ej: `{ success: true, message: "Usuario creado.", data: { userId: 123 } }` o `{ success: false, message: "El email ya existe." }`)*

======================

## CREATE (Crear)

---

### `CrearUsuario`

* **Tablas Requeridas**: `Usuario`, `User_Info`, `OTP`
* **Variables Requeridas**: `nombre`, `apellido`, `correo_electronico`, `passkey`

**Pasos**:
1.  Crear una nueva entrada en `User_Info` con los datos (`nombre`, `apellido`, `correo_electronico`) y el campo `confirm_mail` en `FALSE`. Esta operación debe **retornar el `uinfo_ID`** del registro recién creado.
2.  Crear una nueva entrada en `OTP`, generando un código aleatorio de 8 dígitos para el campo `otp` (guardado como string) y asignando el `timestamp` actual. Esta operación debe **retornar el `otp_ID`** del registro recién creado.
3.  Crear una nueva entrada en `Usuario`, asignando los `uinfo_ID` y `otp_ID` retornados en los pasos anteriores. Asignar `active` en `TRUE` y `auth_level` en `0`.
4.  Retornar el estado de la operación (éxito/fallo y el ID del nuevo usuario).

---

### `ActivarCuenta`

* **Tablas Requeridas**: `Usuario`, `User_Info`, `OTP`
* **Variables Requeridas**: `user_ID`, `otp_ingresado`

**Pasos**:
1.  Buscar el `Usuario` con el `user_ID` proporcionado y obtener sus `uinfo_ID` y `otp_ID` asociados.
2.  Usar el `otp_ID` para buscar el registro en la tabla `OTP` y el `uinfo_ID` para buscar en `User_Info`.
3.  Realizar las siguientes validaciones. Si alguna falla, detener y retornar error:
    * El `otp_ingresado` coincide con el `otp` guardado en la base de datos.
    * La diferencia entre el `timestamp` actual y el `timestamp` del registro OTP es **inferior a 300 segundos (5 minutos)**.
    * El campo `confirm_mail` en `User_Info` es `FALSE`.
4.  Si todas las validaciones son exitosas, modificar el campo `confirm_mail` a `TRUE` en la tabla `User_Info`.
5.  Eliminar el **registro completo** de la tabla `OTP` correspondiente a ese `otp_ID`.
6.  Retornar estado de éxito.

---

### `CrearDireccion`

* **Tablas Requeridas**: `Usuario`, `User_Address`, `Address`
* **Variables Requeridas**: `user_ID`, `nombre_direccion`, `codigo_postal`, `calle`, `departamento`, `otros`

**Pasos**:
1.  Verificar que el `user_ID` existe, está activo (`active=TRUE`) y recuperar su `uinfo_ID` asociado.
2.  Crear una nueva entrada en la tabla `Address` con los datos de la dirección. Esta operación debe **retornar el `address_ID`** generado.
3.  Crear una nueva entrada en la tabla de enlace `User_Address`, relacionando el `uinfo_ID` (del paso 1) y el `address_ID` (del paso 2).
4.  Retornar estado de éxito.

---

### `CrearCategoria`

* **Tablas Requeridas**: `Categorias`, `Usuario`
* **Variables Requeridas**: `user_ID`, `nombre_categoria`, `id_subcategoria` (puede ser nulo)

**Pasos**:
1.  Verificar que el `user_ID` existe, está activo (`active=TRUE`) y tiene un `auth_level <= 1`.
2.  Crear una nueva entrada en `Categorias` con los datos proporcionados. Esta operación debe **retornar el `cat_id`** creado.
3.  Retornar estado de éxito y el `cat_id` generado.

---

### `CrearItemInventario`

* **Tablas Requeridas**: `Inventario`, `Usuario`
* **Variables Requeridas**: `user_ID`, `nombre_item`, `descripcion`, `stock` (cantidad), `precio`, `categoria_ID`, `imagen_ID`

**Pasos**:
1.  Verificar que el `user_ID` existe, está activo (`active=TRUE`) y tiene un `auth_level <= 1`.
2.  Crear una nueva entrada en `Inventario` con todas las variables. Asignar el campo `activo` a `TRUE`.
3.  Retornar estado de éxito.

---

### `AgregarItemACarrito`

* **Tablas Requeridas**: `List`, `Carro`, `Usuario`
* **Variables Requeridas**: `user_ID`, `item_ID`, `cantidad`

**Pasos**:
1.  Verificar que el `user_ID` existe y está activo.
2.  Buscar en la tabla `Carro` si ya existe un registro para este `user_ID`.
    * **Si existe**: Obtener el `list_ID` asociado.
    * **Si no existe**: Crear una nueva entrada en `Carro` con el `user_ID`. Esta operación debe **retornar el `list_ID`** generado.
3.  Con el `list_ID` obtenido, verificar si el `item_ID` ya existe en la tabla `List` para esa lista.
    * **Si ya existe**: Sumar la nueva `cantidad` a la cantidad existente.
    * **Si no existe**: Crear una nueva entrada en la tabla `List` con el `list_ID`, `item_ID` y `cantidad`.
4.  Retornar estado de éxito.

---

### `CrearPedidoDesdeCarrito`

* **Tablas Requeridas**: `Usuario`, `Carro`, `List`, `Inventario`, `Pedidos`, `Pago`
* **Variables Requeridas**: `user_ID`, `medio_de_pago`, `delivery_ID` (dirección)

**Pasos**:
1.  Verificar que el `user_ID` existe, está activo y tiene un `list_ID` en la tabla `Carro`.
2.  Obtener todos los items de la tabla `List` que correspondan a ese `list_ID`.
3.  Para cada item, verificar que la cantidad solicitada sea menor o igual al stock disponible en la tabla `Inventario` (`List.cantidad <= Inventario.stock`). Si algún item no tiene stock, retornar error.
4.  Calcular el precio total sumando (`precio * cantidad`) para todos los items del carrito.
5.  Crear una nueva entrada en la tabla `Pedidos` con el `list_ID`, `delivery_ID` y el total calculado. Asignar el `status` a `'PENDIENTE_PAGO'`. Esta operación debe **retornar el `pedido_ID`** generado.
6.  Crear una nueva entrada en `Pago` con el `pedido_ID` (del paso anterior), el `monto` total, el `medio_de_pago` y un `timestamp`.
7.  Eliminar la entrada del `user_ID` en la tabla `Carro`. La tabla `List` conserva el registro como historial del pedido.
8.  Retornar estado de éxito con la información del pedido y el pago.

======================

## UPDATE (Actualizar)

---

### `ActualizarDatosUsuario`

* **Tablas Requeridas**: `User_Info`, `OTP`
* **Variables Requeridas**: `user_ID`, `nombre` (opcional), `apellido` (opcional), `correo_electronico` (opcional)

**Pasos**:
1.  Verificar que el `user_ID` existe y está activo. Obtener el `uinfo_ID`.
2.  Si se proporcionan `nombre` o `apellido`, actualizar esos campos en la tabla `User_Info` para el `uinfo_ID` correspondiente.
3.  **Si se proporciona `correo_electronico`**:
    a.  Actualizar el campo `correo_electronico` en `User_Info`.
    b.  Poner el campo `confirm_mail` en `FALSE`.
    c.  Crear un **nuevo registro OTP** para el usuario (ver `CrearUsuario` paso 2) y actualizar el `otp_ID` en la tabla `Usuario`.
    d.  (Lógica externa) Enviar un email de verificación a la nueva dirección.
4.  Retornar estado de éxito.

---

### `ActualizarCantidadEnCarrito`

* **Tablas Requeridas**: `List`, `Carro`
* **Variables Requeridas**: `user_ID`, `item_ID`, `nueva_cantidad`

**Pasos**:
1.  Verificar que el `user_ID` existe y obtener su `list_ID` de la tabla `Carro`.
2.  Si `nueva_cantidad` es mayor que 0:
    a.  Buscar en la tabla `List` la entrada que coincida con `list_ID` y `item_ID`.
    b.  Actualizar el campo `cantidad` a `nueva_cantidad`.
3.  Si `nueva_cantidad` es 0, ejecutar la lógica de `EliminarItemDelCarrito` (ver sección DELETE).
4.  Retornar estado de éxito.

---

### `ActualizarEstadoPago`

* **Tablas Requeridas**: `Pago`, `Pedidos`, `Inventario`
* **Variables Requeridas**: `pago_ID`, `nuevo_estado` (ej: 'APROBADO', 'RECHAZADO')

**Pasos**:
1.  Buscar el registro en la tabla `Pago` usando el `pago_ID`. Obtener su `pedido_ID` asociado.
2.  Actualizar el campo `status` en la tabla `Pago`.
3.  Usar el `pedido_ID` para buscar en la tabla `Pedidos` y actualizar su `status` (ej: a 'PAGADO' o 'FALLIDO').
4.  **Si `nuevo_estado` es 'APROBADO'**:
    a.  Obtener el `list_ID` del pedido.
    b.  Recorrer todos los items en la tabla `List` para ese `list_ID`.
    c.  Por cada item, restar la `cantidad` comprada del `stock` en la tabla `Inventario`.
5.  Retornar estado de éxito.

======================

## DELETE (Eliminar)

---

### `EliminarUsuario (Soft Delete)`

* **Tablas Requeridas**: `Usuario`
* **Variables Requeridas**: `user_ID`

**Pasos**:
1.  Localizar el registro en la tabla `Usuario` por su `user_ID`.
2.  Cambiar el valor del campo `active` a `FALSE`.
3.  Retornar estado de éxito.

---

### `EliminarItemDelCarrito`

* **Tablas Requeridas**: `List`, `Carro`
* **Variables Requeridas**: `user_ID`, `item_ID`

**Pasos**:
1.  Verificar que el `user_ID` existe y obtener su `list_ID` de la tabla `Carro`.
2.  Buscar en la tabla `List` el registro que coincida exactamente con el `list_ID` y el `item_ID`.
3.  Eliminar ese registro de la tabla `List`.
4.  Retornar estado de éxito.

---

### `EliminarDireccion`

* **Tablas Requeridas**: `User_Address`, `Address`
* **Variables Requeridas**: `user_ID`, `address_ID`

**Pasos**:
1.  Verificar que el `user_ID` existe y obtener su `uinfo_ID`.
2.  Buscar en la tabla `User_Address` un registro que coincida con el `uinfo_ID` y el `address_ID` para confirmar que la dirección pertenece al usuario. Si no existe, retornar error.
3.  Eliminar el registro de enlace de la tabla `User_Address`.
4.  (Opcional) Contar cuántos usuarios más están usando ese `address_ID`. Si el contador es 0, eliminar el registro de la tabla `Address`.
5.  Retornar estado de éxito.