package com.example.ninosapp

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
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
import kotlinx.coroutines.delay
import java.util.Locale
import java.util.logging.Handler

@Composable
fun PrimerJuego(navController: NavController) {
    val vowels = listOf('A', 'E', 'I', 'O', 'U')
    val context = LocalContext.current

    // Mantén un estado para el reproductor de medios
    val mediaPlayer by remember { mutableStateOf(MediaPlayer()) }
    var vocalesTocadas by remember { mutableStateOf(emptyList<Char>()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Agregar la imagen de fondo
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
                painter = painterResource(id = R.drawable.vocales), // Reemplaza R.drawable.tituloprincipal con la referencia correcta a tu imagen
                contentDescription = null, // Ajusta esto según sea necesario
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(CircleShape)// Ajusta el radio según tus necesidades
            )

            // Crear tres filas de botones: 'a' y 'e', 'i' y 'o', y 'u'
            CenteredGridRow(vowels.subList(0, 2), mediaPlayer, context, onVowelTapped = { vowel ->
                vocalesTocadas = (vocalesTocadas + vowel).distinct()
            })

            Spacer(modifier = Modifier.height(16.dp))
            CenteredGridRow(vowels.subList(2, 4), mediaPlayer, context, onVowelTapped = { vowel ->
                vocalesTocadas = (vocalesTocadas + vowel).distinct()
            })

            Spacer(modifier = Modifier.height(16.dp))
            CenteredGridRow(vowels.subList(4, 5), mediaPlayer, context, onVowelTapped = { vowel ->
                vocalesTocadas = (vocalesTocadas + vowel).distinct()
            })
        }
    }

    // Agregar un botón para navegar al segundo juego
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Button(
            onClick = {
                navController.navigate("segundoJuego")
            },
            modifier = Modifier.padding(16.dp),
            enabled = vocalesTocadas.size == 5,

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
            "vocales",
            "raw",
            context.packageName
        )

        if (audioResourceId != 0) {
            val handler = android.os.Handler(Looper.getMainLooper())

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

@Composable
fun CenteredGridRow(letters: List<Char>, mediaPlayer: MediaPlayer, context: Context, onVowelTapped: (Char) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        letters.forEachIndexed { index, letter ->
            AlphabetButton(letter = letter, mediaPlayer = mediaPlayer, context = context, onVowelTapped = onVowelTapped)
            if (index < letters.size - 1) {
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}

@Composable
fun AlphabetButton(letter: Char, mediaPlayer: MediaPlayer, context: Context, onVowelTapped: (Char) -> Unit) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .clickable {
                // Reproducir audio correspondiente a la letra
                val audioResourceId = context.resources.getIdentifier(
                    letter.toString().lowercase(Locale.getDefault()),
                    "raw",
                    context.packageName
                )

                if (audioResourceId != 0) {
                    mediaPlayer.apply {
                        reset()
                        setDataSource(context, Uri.parse("android.resource://${context.packageName}/$audioResourceId"))
                        prepare()
                        start()
                    }

                    onVowelTapped(letter) // Actualiza la lista de vocales tocadas
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.animal6), // Aquí estableces la imagen de fondo
            contentDescription = null,
            modifier = Modifier.size(200.dp),
             // Ajustar la imagen al tamaño del botón sin distorsionarla
        )

        Text(
            text = letter.toString(),
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 24.sp),
            color = Color.White,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 55.dp)
                .padding(end = 8.dp)
        )
    }
}


