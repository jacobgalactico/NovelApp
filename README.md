Estructura del Proyecto
1. MainActivity
Ubicación: com.example.novelapp.MainActivity

Esta clase principal se encarga de inicializar la aplicación y habilitar una experiencia de usuario inmersiva con enableEdgeToEdge(). Al iniciar, se muestra el componente principal App().

2. ListaLibrosScreen
Ubicación: com.example.novelapp.ListaLibrosScreen

Este composable muestra la lista de novelas en la biblioteca. Cada novela incluye las opciones de:

Eliminar: Botón para eliminar la novela.
Marcar como favorita: Botón para marcar o desmarcar una novela como favorita.
Ver detalles: Al hacer clic en un ítem, se navega a la pantalla de detalles de la novela.
3. Libro
Ubicación: com.example.novelapp.Libro

Un data class que representa una novela con los siguientes atributos:

titulo: El título de la novela.
autor: El autor de la novela.
anioPublicacion: Año de publicación de la novela.
sinopsis: Una breve descripción de la novela.
esFavorita: Indica si la novela está marcada como favorita.
resenas: Lista mutable que almacena las reseñas de los usuarios.
4. DetallesLibroScreen
Ubicación: com.example.novelapp.DetallesLibroScreen

Este composable muestra los detalles completos de una novela seleccionada:

Marcar como favorita: Botón para agregar o quitar la novela de las favoritas.
Reseñas: Se muestran las reseñas existentes y permite añadir nuevas reseñas.
5. AgregarNovelaScreen
Ubicación: com.example.novelapp.AgregarNovelaScreen

Pantalla que permite añadir una nueva novela ingresando los siguientes datos:

Título
Autor
Año de Publicación
Sinopsis
6. App
Ubicación: com.example.novelapp.App

Esta clase gestiona la navegación entre las diferentes pantallas:

Lista de novelas
Pantalla de agregar nueva novela
Pantalla de detalles de la novela
La aplicación utiliza remember para manejar los estados de las pantallas y los libros seleccionados.
