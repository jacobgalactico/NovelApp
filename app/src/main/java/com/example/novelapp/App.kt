package com.example.novelapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.novelapp.ui.theme.NovelAppTheme
import androidx.activity.compose.BackHandler
import androidx.compose.ui.platform.LocalContext


@Composable
fun App() {
    var pantallaActual by remember { mutableStateOf("lista") }
    var libroSeleccionado by remember { mutableStateOf<Libro?>(null) }

    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val databaseHelper = remember { DatabaseHelper(context) }

    // Cargamos las novelas desde SQLite
    val listaLibros = remember { mutableStateListOf<Libro>().apply { addAll(databaseHelper.getAllNovels()) } }

    NovelAppTheme(darkTheme = userPreferences.isDarkModeEnabled()) {
        when (pantallaActual) {
            "lista" -> {
                ListaLibrosScreen(
                    libros = listaLibros,
                    onEliminarLibro = { libro ->
                        databaseHelper.deleteNovel(libro.titulo)
                        listaLibros.remove(libro)
                    },
                    onMarcarFavorito = { libro ->
                        val index = listaLibros.indexOf(libro)
                        val nuevoLibro = libro.copy(esFavorita = !libro.esFavorita)
                        listaLibros[index] = nuevoLibro
                        databaseHelper.updateNovel(nuevoLibro)
                    },
                    onVerDetalles = {
                        libroSeleccionado = it
                        pantallaActual = "detalles"
                    },
                    onAgregarClick = { pantallaActual = "agregar" },
                    modifier = Modifier.fillMaxSize()
                )
            }
            "agregar" -> AgregarNovelaScreen(onAgregarNovela = { nuevaNovela ->
                databaseHelper.addNovel(nuevaNovela)
                listaLibros.add(nuevaNovela)
                pantallaActual = "lista"
            })
            "detalles" -> libroSeleccionado?.let { libro ->
                BackHandler {
                    pantallaActual = "lista"
                }
                DetallesLibroScreen(
                    libro = libro,
                    onMarcarFavorito = {
                        val index = listaLibros.indexOf(libro)
                        val nuevoLibro = libro.copy(esFavorita = !libro.esFavorita)
                        listaLibros[index] = nuevoLibro
                        databaseHelper.updateNovel(nuevoLibro)
                    },
                    onAgregarResena = { resena ->
                        val index = listaLibros.indexOf(libro)
                        listaLibros[index].resenas.add(resena)
                    }
                )
            }
            "configuracion" -> {
                SettingsScreen(
                    userPreferences = userPreferences,
                    onBack = { pantallaActual = "lista" }
                )
            }
        }
    }
}
