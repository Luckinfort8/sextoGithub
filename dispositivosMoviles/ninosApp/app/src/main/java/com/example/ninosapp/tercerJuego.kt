package com.example.ninosapp

import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun tercerJuego(navController: NavController) {
    val letrasRevueltas = listOf('O', 'B', 'E', 'T')
    val letrasCorrectas = "BOTE"
    val context = LocalContext.current

    var mediaPlayer by remember { mutableStateOf(MediaPlayer()) }
    var palabraIngresada by remember { mutableStateOf("") }
    var isBotonHabilitado by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.descubre), // Reemplaza R.drawable.tituloprincipal con la referencia correcta a tu imagen
                contentDescription = null, // Ajusta esto según sea necesario
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(CircleShape)
                    .padding(start = 1.dp)// Ajusta el radio según tus necesidades
            )
            // Mostrar la palabra ingresada
            Text(
                text = palabraIngresada,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 60.sp
            )
            // Centrar los botones con espacio entre ellos
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    letrasRevueltas.take(2).forEach { letra ->
                        AlphabetButton(
                            letter = letra,
                            mediaPlayer = mediaPlayer,
                            context = context,
                            onVowelTapped = { letraSeleccionada ->
                                if (palabraIngresada.length == 4) {
                                    // Reiniciar la palabra si ya tiene 4 letras
                                    palabraIngresada = ""
                                    isBotonHabilitado = false
                                }
                                palabraIngresada += letraSeleccionada.toString()
                                if (palabraIngresada == letrasCorrectas) {
                                    isBotonHabilitado = true
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(20.dp)) // Agregar espacio entre los botones
                    }
                }
                Spacer(modifier = Modifier.size(20.dp)) // Agregar espacio vertical entre las filas de botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    letrasRevueltas.takeLast(2).forEach { letra ->
                        AlphabetButton(
                            letter = letra,
                            mediaPlayer = mediaPlayer,
                            context = context,
                            onVowelTapped = { letraSeleccionada ->
                                if (palabraIngresada.length == 4) {
                                    // Reiniciar la palabra si ya tiene 4 letras
                                    palabraIngresada = ""
                                    isBotonHabilitado = false
                                }
                                palabraIngresada += letraSeleccionada.toString()
                                if (palabraIngresada == letrasCorrectas) {
                                    isBotonHabilitado = true
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(20.dp)) // Agregar espacio entre los botones
                    }
                }
            }
        }
    }

    // Agregar un botón para navegar al cuarto juego
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                navController.navigate("main")
            },
            modifier = Modifier.padding(16.dp),
            enabled = isBotonHabilitado
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
    // Agregar un efecto secundario para reproducir el audio al entrar a la actividad
    DisposableEffect(context) {
        val audioResourceId = context.resources.getIdentifier(
            "descubre",
            "raw",
            context.packageName
        )

        if (audioResourceId != 0) {
            val handler = Handler(Looper.getMainLooper())

            // Agregar una pausa de un segundo antes de reproducir el audio
            handler.postDelayed({
                mediaPlayer.apply {
                    reset()
                    setDataSource(context, Uri.parse("android.resource://${context.packageName}/$audioResourceId"))
                    prepare()
                    start()
                }
            }, 350)
        }

        // Agregar un efecto de limpieza para detener el audio al salir de la actividad
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }
}
