package com.example.novelapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star

@Composable
fun ListaLibrosScreen(
    libros: List<Libro>,
    onEliminarLibro: (Libro) -> Unit,
    onMarcarFavorito: (Libro) -> Unit,
    onVerDetalles: (Libro) -> Unit,
    onAgregarClick: () -> Unit, // Callback para agregar nuevas novelas
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.weight(1f) // Ocupa todo el espacio disponible menos el botón
        ) {
            item {
                Spacer(modifier = Modifier.height(78.dp)) // Esto añade un margen al principio
            }
            items(libros.size) { index ->
                LibroItem(
                    libro = libros[index],
                    onEliminar = { onEliminarLibro(libros[index]) },
                    onMarcarFavorito = { onMarcarFavorito(libros[index]) },
                    onVerDetalles = { onVerDetalles(libros[index]) }
                )
            }
        }

        // Botón para ir a la pantalla de agregar novela
        Button(
            onClick = onAgregarClick,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Agregar Nueva Novela")
        }
    }
}

@Composable
fun LibroItem(
    libro: Libro,
    onEliminar: () -> Unit,
    onMarcarFavorito: () -> Unit,
    onVerDetalles: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (libro.esFavorita) Color.Yellow else Color.White)
            .padding(8.dp)
            .clickable(onClick = onVerDetalles),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = libro.titulo, style = MaterialTheme.typography.titleLarge)
            Text(text = "Autor: ${libro.autor}")
            Text(text = "Año: ${libro.anioPublicacion}")
        }
        Row {
            IconButton(onClick = onMarcarFavorito) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "Marcar como favorita")
            }
            IconButton(onClick = onEliminar) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar novela")
            }
        }
    }
}
