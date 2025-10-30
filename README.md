## Sistema POS para Cafetería y Restaurante

Este proyecto corresponde al desarrollo de un Sistema POS básico implementado en Java (NetBeans 22), orientado a la gestión operativa de una cafetería o restaurante. El sistema opera completamente por consola e incorpora los principios fundamentales de la Programación Orientada a Objetos (POO).



## Objetivos del Sistema

* Gestionar mesas con estados: Disponible, Reservada y Ocupada.
* Administrar inventario en tiempo real.
* Registrar pedidos y generar facturas.
* Gestionar clientes y empleados.
* Registrar y calcular ventas mediante una caja.
* Generar reportes diarios y mensuales, incluyendo estadísticas de ventas.




## Clases Principales

- Persona:	Clase base para Cliente y Empleado
- Cliente:	Hereda de Persona
- Empleado:	Hereda de Persona, incluye identificación del empleado
- Producto:	Información básica del producto (nombre y precio)
- Inventario:	Control dinámico de existencias por producto
- Mesa:	Gestión del estado de cada mesa
- Pedido:	Registro de productos solicitados por un cliente
- Factura:	Representación del comprobante de venta
- Caja:	Registro de ingresos y control de venta total
- Reporte:	Generación de reportes diarios y mensuales



