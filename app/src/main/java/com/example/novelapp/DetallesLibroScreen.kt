package com.example.novelapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetallesLibroScreen(
    libro: Libro,
    onMarcarFavorito: () -> Unit,
    onAgregarResena: (String) -> Unit
) {
    var resena by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Título: ${libro.titulo}", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Autor: ${libro.autor}")
        Text(text = "Año de Publicación: ${libro.anioPublicacion}")
        Text(text = "Sinopsis: ${libro.sinopsis}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onMarcarFavorito) {
            Text(if (libro.esFavorita) "Quitar de Favoritos" else "Marcar como Favorita")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Reseñas", style = MaterialTheme.typography.titleMedium)

        // Reseñas
        LazyColumn {
            items(libro.resenas.size) { index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant) // Fondo diferente para la reseña
                        .padding(8.dp)
                ) {
                    Text(text = libro.resenas[index], style = MaterialTheme.typography.bodyMedium)
                }

                // Agregar un separador entre reseñas
                if (index < libro.resenas.size - 1) {
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para añadir nueva reseña
        TextField(
            value = resena,
            onValueChange = { resena = it },
            label = { Text("Añadir Reseña") }
        )
        Button(onClick = {
            if (resena.isNotBlank()) {
                onAgregarResena(resena)
                resena = ""
            }
        }) {
            Text("Agregar Reseña")
        }
    }
}
