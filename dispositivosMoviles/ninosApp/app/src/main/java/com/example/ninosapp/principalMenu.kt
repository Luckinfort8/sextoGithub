package com.example.ninosapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PrincipalMenu(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Agregar la imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo), // Reemplaza "nombre_de_tu_imagen" con el nombre real de tu imagen en recursos.
            contentDescription = null, // Puedes proporcionar una descripción si es necesario.
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Escala y recorta la imagen para que llene toda la pantalla.
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.1f) // Ajusta la opacidad del fondo.
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Espacio en blanco antes del primer botón
                Spacer(modifier = Modifier.size(15.dp))

                Image(
                    painter = painterResource(id = R.drawable.animal7),
                    contentDescription = "Juego A",
                    modifier = Modifier
                        .padding(top = 80.dp)
                        .padding(start = 45.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate("primerJuego")
                        }
                )

                Spacer(modifier = Modifier.size(5.dp))

                // Botón Juego B
                Image(
                    painter = painterResource(id = R.drawable.animal6),
                    contentDescription = "Juego B",
                    modifier = Modifier
                        .padding(top = 90.dp)
                        .padding(start = 60.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate("segundoJuego")
                        }
                )

                Spacer(modifier = Modifier.size(5.dp))

                // Botón Juego C
                Image(
                    painter = painterResource(id = R.drawable.animal3),
                    contentDescription = "Juego C",
                    modifier = Modifier
                        .padding(top = 80.dp)
                        .padding(start = 85.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate("tercerJuego")
                        }
                )

            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        Button(
            onClick = {
                navController.navigate("main")
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "\uD83C\uDFE0",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 40.sp),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .padding(bottom = 8.dp)
            )
        }
    }

}



