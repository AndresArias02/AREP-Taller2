# TALLER DISEÑO Y ESTRUCTURACIÓN DE APLICACIONES DISTRIBUIDAS EN INTERNET

Este proyecto implementa una aplicación web de lista de compras (Shopping List) que permite agregar, ver y eliminar productos. La aplicación está desarrollada en Java, utilizando solo las bibliotecas de red de Java estándar, sin el uso de frameworks web como Spark o Spring.

## Comenzando

Las siguientes instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para fines de desarrollo y prueba.

### Construido con:
    
* [Git](https://git-scm.com) - Control de versiones
* [Maven](https://maven.apache.org) -  Manejador de dependencias
* [java](https://www.oracle.com/java/technologies/downloads/#java21) - Lenguaje de programación

### Requisitos:

#### ⚠️ Importante

Es necesario tener instalado Git, Maven y Java 21 para poder ejecutar el proyecto.

## Arquitectura de la Aplicación
### Cliente Servidor 

La aplicación se compone de dos partes principales:

1. **Servidor Web en Java:**
    - El servidor web es capaz de manejar múltiples solicitudes concurrentes utilizando un `ExecutorService` con un grupo de hilos.
    - Soporta la entrega de archivos estáticos como HTML, CSS, JavaScript e imágenes.
    - Implementa servicios REST que permiten la interacción asincrónica entre el cliente y el servidor.

2. **Aplicación Web Frontend:**
    - Construida usando HTML, CSS y JavaScript.
    - Interactúa con los servicios REST del backend para agregar, listar y eliminar productos de la lista de compras.

## Estructura del Proyecto

- **Servidor Web (`ShoppingList.java`)**: Implementa el servidor y maneja las solicitudes HTTP entrantes.
- **Manejo de Clientes (`ClientHandler.java`)**: Clase que se encarga de procesar cada solicitud de cliente de manera concurrente.
- **Servicios REST**:
    - **`addProductService`**: Servicio REST para agregar productos a la lista.
    - **`getProductsService`**: Servicio REST para obtener la lista de productos en formato JSON.
    - **`deleteProductService`**: Servicio REST para eliminar productos de la lista.
- **Frontend**:
    - **`shoppingCart.html`**: Página principal que permite la interacción del usuario con la lista de compras.
    - **`styles.css`**: Estilos CSS para la interfaz de usuario.
    - **`scripts.js`**: Lógica de frontend para manejar las solicitudes asíncronas y actualizar la interfaz.

## Instalación y ejecución 

Para instalar y ejecutar esta aplicación, sigue los siguientes pasos:

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/AndresArias02/AREP-Taller1.git
   cd AREP-taller1
   ```

2. **Compilar y ejecutar:**

    ```bash
   mvn clean compile
   mvn exec:java '-Dexec.mainClass=escuelaing.edu.co.arep.ShoppingList'
   ```

3. **Abrir la aplicación en un navegador web:**

   Navega a http://localhost:8080/shoppingCart.html para interactuar con la aplicación.

## Ejecutando las pruebas 

Para ejecutar las pruebas, ejecute el siguiente comando:

```bash
mvn test
```

## verionamiento 

![AREP LAB 01](https://img.shields.io/badge/AREP_LAB_01-v1.0.0-blue)

## Autores

- Andrés Arias - [AndresArias02](https://github.com/AndresArias02)

## Licencia

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Este proyecto está bajo la Licencia (MIT) - ver el archivo [LICENSE](LICENSE.md) para ver más detalles.

## Agradecimientos 

- Al profesor [Luis Daniel Benavides Navarro](https://ldbn.is.escuelaing.edu.co) por compartir sus conocimientos.
    
