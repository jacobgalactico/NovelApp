package com.example.novelapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.novelapp.ui.theme.NovelAppTheme
import androidx.activity.compose.BackHandler

@Composable
fun App() {
    var pantallaActual by remember { mutableStateOf("lista") }
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }

    // Lista de libros de ejemplo
    val listaLibros = remember { mutableStateListOf(
        Libro("El Quijote", "Miguel de Cervantes", 1605, "Una gran obra clásica"),
        Libro("Cien años de soledad", "Gabriel García Márquez", 1967, "La historia de la familia Buendía")
    ) }

    NovelAppTheme {
        when (pantallaActual) {
            "lista" -> ListaLibrosScreen(
                libros = listaLibros,
                onEliminarLibro = { listaLibros.remove(it) },
                onMarcarFavorito = { libro ->
                    val index = listaLibros.indexOf(libro)
                    listaLibros[index] = libro.copy(esFavorita = !libro.esFavorita)
                },
                onVerDetalles = {
                    libroSeleccionado = it
                    pantallaActual = "detalles"
                },
                onAgregarClick = { pantallaActual = "agregar" }, // Navegar a la pantalla de agregar
                modifier = Modifier.fillMaxSize()
            )
            "agregar" -> AgregarNovelaScreen(onAgregarNovela = { nuevaNovela ->
                listaLibros.add(nuevaNovela)
                pantallaActual = "lista"
            })
            "detalles" -> libroSeleccionado?.let { libro ->
                // Aquí capturamos el evento de back y lo usamos para volver a la pantalla de la lista
                BackHandler {
                    pantallaActual = "lista" // Volver a la pantalla de lista de libros
                }

                DetallesLibroScreen(
                    libro = libro,
                    onMarcarFavorito = {
                        val index = listaLibros.indexOf(libro)
                        listaLibros[index] = libro.copy(esFavorita = !libro.esFavorita)
                    },
                    onAgregarResena = { resena ->
                        val index = listaLibros.indexOf(libro)
                        listaLibros[index].resenas.add(resena)
                    }
                )
            }
        }
    }
}
