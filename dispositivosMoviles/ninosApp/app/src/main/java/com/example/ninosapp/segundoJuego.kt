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
fun segundoJuego(navController: NavController) {
    val vowels = listOf('V', 'B')
    val context = LocalContext.current

    var mediaPlayer by remember { mutableStateOf(MediaPlayer()) }
    var vocalesTocadas by remember { mutableStateOf(emptyList<Char>()) }
    var letraSeleccionada by remember { mutableStateOf('_') }
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
                painter = painterResource(id = R.drawable.vaca), // Reemplaza R.drawable.tituloprincipal con la referencia correcta a tu imagen
                contentDescription = null, // Ajusta esto según sea necesario
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(CircleShape)// Ajusta el radio según tus necesidades
            )

            Spacer(modifier = Modifier.size(20.dp))

            // Mostrar la letra seleccionada o guion bajo
            Text(
                text = letraSeleccionada.toString() + "ACA",
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 60.sp
            )

            // Agregar los botones "V" y "B"
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 90.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                // Botón para la "V"
                AlphabetButton(
                    letter = 'V',
                    mediaPlayer = mediaPlayer,
                    context = context,
                    onVowelTapped = { vowel ->
                        vocalesTocadas = (vocalesTocadas + vowel).distinct()
                        letraSeleccionada = vowel
                        isBotonHabilitado = true
                    }
                )

                Spacer(modifier = Modifier.width(20.dp))

                // Botón para la "B"
                AlphabetButton(
                    letter = 'B',
                    mediaPlayer = mediaPlayer,
                    context = context,
                    onVowelTapped = { vowel ->
                        letraSeleccionada = vowel
                        isBotonHabilitado = false
                    }
                )
            }
        }
    }

    // Agregar un botón para navegar al tercer juego
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                navController.navigate("tercerJuego")
            },
            modifier = Modifier.padding(16.dp),
            // Habilitar el botón solo si la letra seleccionada no es 'b'
            enabled = isBotonHabilitado
        ) {
            Text(
                text = "▶",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 35.sp),
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
            "vaca",
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
