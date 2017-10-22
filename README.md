## [Máster en Ingeniería Web por la Universidad Politécnica de Madrid (miw-upm)](http://miw.etsisi.upm.es)
### *Front-end para Móviles* - Práctica FEM1 - **Carlos Blanco Vaquerizo**
#### FEM. Gestión de Ficheros y Preferencias
* A partir de la app del juego conocido como [Solitario Celta](https://es.wikipedia.org/wiki/Senku), que consiste en ir saltando piezas en horizontal o en vertical con el objetivo de que finalmente quede el menor número de piezas en el tablero.
* En este proyecto se trabajará sobre los aspectos relacionados con la persistencia de datos (empleando ficheros) y las preferencias del usuario.
* Para ello se recomienda comenzar con la lectura de los documentos [Settings](https://developer.android.com/guide/topics/ui/settings.html?hl=es) y [Saving Data](https://developer.android.com/training/basics/data-storage/files.html) Files.


![Solitario Celta](https://github.com/socrtx/MIW-FEM1-Senku-Karlos/blob/master/app/src/main/screens.jpg)

Partiendo del proyecto Solitario Celta, se deberá continuar con el desarrollo de la aplicación implementando las siguientes opciones:

* **Reiniciar partida**: al pulsar el botón "reiniciar" se mostrará un diálogo de confirmación. En caso de respuesta afirmativa se procederá a reiniciar la partida actual.
* Guardar partida: esta opción permite guardar la situación actual del tablero. Sólo es necesario guardar una única partida.
* **Recuperar partida**: si existe, recupera el estado de una partida guardada (si se ha modificado la partida actual, se deberá solicitar confirmación).
* **Guardar puntuación**: al finalizar cada partida se deberá guardar la información necesaria para generar un listado de resultados. Dicho listado deberá incluir -al menos- el nombre del jugador, el día y hora de la partida y el número de piezas que quedaron en el tablero.
* **Mejores resultados**: esta opción mostrará el histórico con los mejores resultados obtenidos ordenados por número de piezas. Incluirá una opción para eliminar todos los resultados guardados.
> **Opcionalmente**: se propone
> * Mostrar el número de fichas que quedan,
> * Añadir un cronómetro a la partida (y/o guardar el tiempo empleado),
> * Permitir guardar más de una partida,
> * Filtrar los mejores resultados por número de fichas o por jugador
> * Añadir preferencias para modificar los colores empleados por la app, etc.

> **Notas**:
> * Sólo se deberá subir un único fichero comprimido con el proyecto. Antes de generarlo se deberán eliminar los directorios y ficheros innecesarios (/.git, /.gradle, /.idea, /build, /app/build, ...)
> * Se añadirá un fichero en el directorio raíz del proyecto con las aclaraciones y observaciones que se consideren oportunas.