-- Capa de datos --
Recuerde siempre devolver el estatus de las operaciones
======================
---
Create
----
Crear un usuario:
    Requiere Tablas:Usuario, User_Info y OTP
    Requiere variables: Nombre, Apellido, correo electronico, passkey
    Nombre de la interfas: CUSER

    1) Crear una entrada de User_Info, el campo confirm_mail debe tener el valor FALSE;
    2) Recupere el ultimo valor de uinfo_ID creado en la base de datos;
    4) Cree una nueva entrada en OTP, asigne el timestamp actual y genere un codigo aleatoreo de 8 digitos para el campo OTP, asegurese de registrarlo como un string;
    2) Recupere el ultimo valor de opt_ID creado en la base de datos;
    3) Cree una nueva entrada en Usuario, asigne la llave foranea previamente recuperada a uinfo_ID y a otp_ID, asigne active en TRUE, asigne aunth_level en 0;
    
Activacion de cuenta:
    Requiere Tablas: Usuario, User_Info, OTP
    Requiere variables: user_ID, otp
    
    1) Obtenga el parametro uinfo_ID de Usuario;
    3) Valide que el otp que se da por input conincide, el timestamp del registro menos el timestamp actual es inferior a 60 y que el parametro confirm_mail no es TRUE;
    4) Elimine el parametro otp de OTP;
    2) Modifique la casilla confirm_mail a TRUE;
    
Crear nueva direccion:
    Requiere Tablas: Usuario, User_Address y Address
    Requiere vasibles: ID de usuario, Nombre de la nueva direccion, codigo postal, calle, departamento y otros
    
    1) Verifique que el usuario existe y active=True;
    3) Recupere el uinfo_ID de la tabla Usuarios correspondiente al ID de usuario asociado;
    2) Cree una nueva entrada en la tabla Address con las variables restantes;
    3) Recupere el ultimo valor de address_ID creado en la base de datos;
    4) Cree una nueva entrada en User_Address donde relacione el uinfo_ID y el address_ID;
    
Crear categorias:
    Requiere Tablas: Categorias, Usuario
    Requiere variables: Nombre de la categoria, id de subcategoria, user_ID
    
    1) Verifique que el usuario existe, active=True, aunth_level <= 1;
    2) Cree una entrada de Categoria, use las variables dadas;
    3) Recupere y retorne el valor de cat_id creado en la base de datos;
    
Crear imagenes:
    Requiere Tablas: Images, Usuario
    Requiere variables: URI imagen
    
    1) Verifique que el usuario existe, active=True, aunth_level <= 1;
    2) Cree una entrada de Images, use las variables dadas;
    3) Recupere y retorne el valor de img_ID creado en la base de datos;
    
Crear un nuevo item a la venta:
    Requiere Tablas: Inventario, ID Categoria, ID Imagen, descripcion, precio
    Requiere variables: nombre, descripcion, cantidad (stock), precio
    
    1) Verifique que el usuario existe, active=True, aunth_level <= 1;
    2) Cree una nueva entrada en Inventario usando todas las variables disponibles, asigne el campo activo=TRUE;
    
Agregar al carrito:
    Requiere Tablas: List, Carro, Inventario, confing, Usuario
    Requiere variables: item_ID, user_ID, cantidad
    
    1) Verifique que el usuario existe, active=True;
    
    2) Acceda a Carro y confirme que existe un registro con el ID de usuario:
    
        No) Proceda a tabla config y revise la PK en busqueda de "max_list", en caso de que el parametro no exista, creelo e inicie el contador en 1, en caso contrario almacene el valor actual del contador. Luego de almacenarlo aumentelo en 1. Luego cree una nueva entrada en Carro que contenga el id del usuario y el id de lista (El valor qu, el parametro previamente extrajo previamente).
        
        Si) Almacene el parametro list_ID
        
    3) Acceda a Lista y creela entrada, Almacene el ID de su item,  el ID de la lista y la cantidad en la tabla
    
Agregar a la lista de espera/deseos:
    Requiere Tablas: Lista_de_Espera, Usuario
    Requiere variables: user_ID, item_ID
    
    1) Verifique que el usuario existe, active=True;
    2) Acceda a la tabla Lista_de_Espera y incluya la informacion.
    
    
Generacion de cobro:
    Requiere Tablas: Usuario, Carro, List, Inventario.
    Requiere variables: Medio de pago, delibery_ID
    
    1) Verifique el usuario existe, active=True;
    2) Verifique el campo list_ID de la tabla Carro este asignado para el user_ID
    3) Obtenga la lista del contenido del carro, esto se consigue al filtrar la tabla List en el valor list_ID, obtenga list_ID desde la tabla Carro
    4) Opere un Left Join entre la tabla List y Inventario en el parametro item_ID, corrobore que para todo campo quantity es mayor a cantidad.
    5) Sobre el resultado anterior calcule el precio final de todos los items dispuestos y reservelo.
    6) Reserve el list_ID y liminelo de la tabla Carro.
    7) Asigne el list_ID a Pedidos y llene los demas campos con la informaccion brindada. deje la variable status en FALSE.
    8) Recupere el ultimo valor de pago_ID creado en la base de datos;
    6) Cree una entrada en Pago, por el monto previamente calculado, asigne el medio, solicite el ID de transacion segun el medio seleccionado y agregue un time_stamp, del paso previo asigne el pedido_ID correspondiente.
    7) indique al servicio designado monitorear el estado de la transacion.
    
Actualizacion del estado del pago:

    Requiere Tablas: Pago, Pedios
    Requiere variables: pago_ID
    
    1) Obtenga la columna segun PK en la tabla Pago, encuentre el valor pedido_ID y almacenelo.
    2) Actualice a TRUE el estado del satus en la tabla Pedidos
    

----
Update
----

Acualizar info del usuario:
    Solo se puede actualizar nombre y mail
    
    
    
    Requiere Tablas: Usuario, User_Info y OTP
    

Eliminar usuario:
Localizar el ID del usuario y luego cambiar "active" a false
