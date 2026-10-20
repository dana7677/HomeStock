# 🏠 HomeStock

**Aplicación Android para organizar objetos domésticos y saber dónde se encuentran.**

> 🚧 Proyecto personal en desarrollo. Este README diferencia las funcionalidades actuales de las previstas.

## 🎯 Objetivo

HomeStock nace para resolver una situación cotidiana: saber qué objetos tenemos, cuántos hay y en qué lugar los hemos guardado. La idea es organizar distintas ubicaciones (por ejemplo, vivienda, trastero, habitación, armario o caja) y consultar su contenido desde el móvil.

## 🛠️ Tecnologías

- **Kotlin** — lenguaje de programación.
- **Jetpack Compose / Material 3** — interfaz declarativa de Android.
- **Android Studio** — entorno de desarrollo.
- **Navigation Compose** — navegación entre pantallas.
- **ViewModel y estado observable de Compose** — gestión de datos de la interfaz.
- **Git y GitHub** — control de versiones.

## ✅ Funcionalidades implementadas

- Registro de objetos con **nombre** y **cantidad**.
- Listado de objetos.
- Edición y eliminación de objetos.
- Estados de los objetos: **disponible**, **prestado** y **roto**.
- Validación de campos en el formulario.
- Navegación entre la pantalla principal y el formulario.
- Trabajo en una estructura de ubicaciones que pueden contener otras ubicaciones.

> El alcance exacto de la gestión de ubicaciones se irá actualizando conforme se complete y pruebe cada pantalla.

## 🧭 Funcionalidades previstas

- Organización completa de ubicaciones anidadas: **vivienda → habitación → armario → cajón → caja → objeto**.
- Movimiento de objetos y contenedores entre ubicaciones.
- Persistencia local mediante **Room**.
- Búsqueda de objetos por nombre y ubicación.
- Códigos **QR** asociados a cajas o ubicaciones para consultar su contenido.
- Registro mediante voz de objetos y ubicaciones.

Las funciones de esta sección **todavía no deben considerarse terminadas**.

## 🧱 Organización del proyecto

La aplicación separa la interfaz de usuario y el estado de los objetos mediante componentes de Compose y un `ObjectViewModel`. El modelo de datos y la jerarquía de ubicaciones evolucionan durante el desarrollo.

## 📷 Capturas de pantalla

_Pendiente: añadir capturas reales de la pantalla principal, formulario y navegación por ubicaciones._

## ▶️ Ejecución

1. Clona este repositorio cuando esté publicado.
2. Abre el proyecto en **Android Studio**.
3. Espera a que finalice la sincronización de Gradle.
4. Ejecuta la aplicación en un emulador o dispositivo Android compatible con la configuración del proyecto.

## 📌 Estado del proyecto

**En desarrollo activo.** Se prioriza una base funcional y comprensible antes de añadir características avanzadas.

## 👨‍💻 Autor

Daniel Prieto González — [GitHub](https://github.com/dana7677)
