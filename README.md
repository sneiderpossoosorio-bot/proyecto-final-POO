## Sistema POS para Cafetería y Restaurante

Este proyecto corresponde al desarrollo de un Sistema POS básico implementado en Java (NetBeans 22), orientado a la gestión operativa de una cafetería o restaurante. El sistema opera completamente por consola e incorpora los principios fundamentales de la Programación Orientada a Objetos (POO).




## ¿Qué es un Sistema POS para Restaurantes y Cafeterías?

Un Sistema POS (Point of Sale) para restaurantes y cafeterías es una herramienta informática que permite gestionar de forma eficiente las operaciones principales del negocio. Facilita procesos como:

- Registro de pedidos
- Control de inventario en tiempo real
- Gestión de mesas
- Facturación
- Registro de ventas
- Generación de reportes


Su propósito es optimizar la atención al cliente, mejorar la organización operativa y permitir un control administrativo preciso sobre las ventas y los recursos del establecimiento.



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



