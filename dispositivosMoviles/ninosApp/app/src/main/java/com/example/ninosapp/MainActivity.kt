package com.example.ninosapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ninosapp.ui.theme.NinosAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(this, R.raw.piano) // Reemplaza con el nombre real de tu canción
        mediaPlayer.isLooping = true // Repetir la canción
        mediaPlayer.setVolume(0.1f, 0.1f)

        setContent {
            val navController = rememberNavController()

            NinosAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = "main"
                ) {
                    composable("main") {
                        MainContent(navController)
                    }
                    composable("menu") {
                        PrincipalMenu(navController)
                    }
                    composable("primerJuego") {
                        PrimerJuego(navController)
                    }
                    composable("segundoJuego") {
                        segundoJuego(navController)
                    }
                    composable("tercerJuego") {
                        tercerJuego(navController)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}

@Composable
fun MainContent(navController: NavHostController) {
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.tituloprincipal), // Reemplaza R.drawable.tituloprincipal con la referencia correcta a tu imagen
                contentDescription = null, // Ajusta esto según sea necesario
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(CircleShape)// Ajusta el radio según tus necesidades
            )


            Box(
                modifier = Modifier
                    .padding(top = 150.dp)
                    .size(200.dp) // Tamaño total del botón
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate("menu")
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.animal8), // Reemplaza "animal8" con el nombre real de tu imagen en recursos.
                    contentDescription = null, // Puedes proporcionar una descripción si es necesario.
                    modifier = Modifier
                        .fillMaxSize()
                )

                Text(
                    text = "▶",
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 60.sp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 75.dp) // Ajusta este valor para centrar verticalmente el texto
                        .padding(start = 10.dp) // Ajusta este valor para centrar horizontalmente el texto
                        .padding(16.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }


        }
    }
}
