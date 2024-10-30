
# Proyecto: Detección de Mutantes

Magneto ha encomendado el desarrollo de un sistema capaz de identificar mutantes a partir de sus secuencias de ADN, ayudando en su lucha contra los X-Men. Este proyecto desarrollado en Java con Spring Boot implementa un algoritmo para detectar mutantes, expone una API REST y almacena datos en una base de datos H2.

## Descripción

El sistema evalúa si un humano es mutante o no, basándose en secuencias de ADN de NxN compuestas únicamente por las letras A, T, C y G. Se considera mutante si en el ADN se detectan más de una secuencia de cuatro letras iguales de forma horizontal, vertical u oblicua.

## Ejemplo de uso

```java
String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
boolean isMutant = isMutant(dna); // Devuelve "true" en este caso
```

## Requisitos del Proyecto

### Nivel 1
- **Algoritmo**: Desarrollar un algoritmo eficiente en Java para identificar secuencias mutantes.
- **Arquitectura**: Implementar el proyecto en una arquitectura en capas con controladores, servicios y repositorios.

### Nivel 2
- **API REST**: Crear un servicio en `/mutant/` que detecte mutantes a partir de una secuencia de ADN en formato JSON, recibiendo peticiones HTTP POST.
  ```json
  POST /mutant/
  { "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"] }
  ```
  - **Respuesta**: HTTP 200-OK para mutantes, 403-Forbidden para no mutantes.
- **Hosting**: Desplegar la API en un servicio de cloud computing (ej. Render).

### Nivel 3
- **Base de Datos H2**: Almacenar los ADN verificados, sin duplicados.
- **Estadísticas**: Implementar el endpoint `/stats` para devolver estadísticas de ADN en JSON:
  ```json
  {
      "count_mutant_dna": 40,
      "count_human_dna": 100,
      "ratio": 0.4
  }
  ```
- **Pruebas de Carga**: Utilizar JMeter para garantizar la estabilidad ante altos volúmenes de tráfico.
- **Pruebas Automáticas**: Tests con JUnit con un code coverage > 80%.

## Instrucciones de Ejecución

1. **Clonar el repositorio**:
   ```bash
   git clone <URL_REPOSITORIO>
   cd <NOMBRE_DEL_PROYECTO>
   ```

2. **Configurar el entorno**:
   - Asegúrate de tener Java y Maven instalados.
   - Configura las propiedades de la base de datos H2 en `application.properties`.

3. **Ejecutar el proyecto**:
   ```bash
   mvn spring-boot:run
   ```

4. **Pruebas**:
   - Ejecuta las pruebas automáticas:
     ```bash
     mvn test
     ```

5. **Pruebas de rendimiento**:
   - Configura y ejecuta pruebas de carga con JMeter.

6. **Acceder a los servicios**:
   - Detección de mutantes: `POST /mutant/`
   - Estadísticas: `GET /stats`

## Documentación

Para más información sobre la arquitectura, diagramas de secuencia y resultados de las pruebas de carga, consulta la carpeta `docs` del proyecto.
