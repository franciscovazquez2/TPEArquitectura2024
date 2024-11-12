# TPEArquitectura2024

Este proyecto consiste en la implementación de un sistema basado en microservicios para gestionar el alquiler de monopatines electrónicos en una ciudad. La solución incluye tanto una **aplicación móvil** para los usuarios finales como una **aplicación web** para la administración y mantenimiento del servicio.

## Descripción General

El sistema permite a los usuarios **alquilar monopatines** en diferentes paradas dentro de la ciudad, gestionar sus cuentas a través de una app móvil y a los administradores supervisar la flota mediante una aplicación web.

## Características Principales

### Aplicación Móvil para Usuarios
- Registro y autenticación con "Mercado Pago".
- Activación de monopatines mediante código QR.
- Mapas interactivos para localizar monopatines disponibles.
- Gestión de viajes y pausas.

### Aplicación Web para Administradores
- Gestión de monopatines y paradas.
- Reportes de uso y mantenimiento.
- Configuración de tarifas y políticas.

## Arquitectura del Sistema

El sistema está basado en una arquitectura de microservicios, con comunicación mediante un **API Gateway** y servicios de descubrimiento usando **Eureka Server**.

## Microservicios Implementados

| Microservicio          | Descripción                                                        |
|------------------------|--------------------------------------------------------------------|
| **microserv-auth**     | Gestión de autenticación y autorización de usuarios.               |
| **microserv-billing**  | Manejo de facturación y reportes de ingresos.                      |
| **microserv-scooter**  | Administración de la flota de monopatines.                         |
| **microserv-parking**  | Gestión de paradas de estacionamiento.                             |
| **microserv-travel**   | Registro y gestión de viajes.                                      |
| **microserv-maintenance** | Registro de tareas de mantenimiento.                           |
| **microserv-user-account** | Gestión de cuentas de usuarios y suscripciones.               |
| **microserv-gateway**  | Enrutamiento de solicitudes.                                       |
| **microserv-config**   | Configuración centralizada.                                        |
| **microserv-eureka**   | Descubrimiento de servicios.                                       |
