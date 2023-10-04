package com.example.ninosapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PrincipalMenu() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Espacio en blanco antes del primer botón
            Spacer(modifier = Modifier.size(15.dp))

            Button(
                onClick = {
                    // Navegar a la pantalla del "Juego A"
                },
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Text(text = "Juego A")
            }

            Spacer(modifier = Modifier.size(5.dp))

            Button(
                onClick = {
                    // Navegar a la pantalla del "Juego B"
                },
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Text(text = "Juego B")
            }

            Spacer(modifier = Modifier.size(5.dp))

            Button(
                onClick = {
                    // Navegar a la pantalla del "Juego C"
                },
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Text(text = "Juego C")
            }

            Spacer(modifier = Modifier.size(5.dp))

            Button(
                onClick = {
                    // Navegar a la pantalla del "Juego D"
                },
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
            ) {
                Text(text = "Juego D")
            }

            // Espacio en blanco después del último botón
            Spacer(modifier = Modifier.size(15.dp))
        }
    }
}


