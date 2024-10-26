package com.example.novelapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(userPreferences: UserPreferences, onBack: () -> Unit) {
    var isDarkMode by remember { mutableStateOf(userPreferences.isDarkModeEnabled()) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Configuración", style = MaterialTheme.typography.headlineSmall)

        // Opción de tema
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Tema Oscuro")
            Switch(
                checked = isDarkMode,
                onCheckedChange = {
                    isDarkMode = it
                    userPreferences.setDarkModeEnabled(it)
                }
            )
        }

        // Botón para regresar
        Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text("Volver")
        }
    }
}
