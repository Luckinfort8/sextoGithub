import android.net.http.HttpResponseCache.install
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prueba.ui.theme.PruebaTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(val result: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }

        // Cambia la URL a la dirección donde se está ejecutando tu servidor Flask
        val url = "http://172.19.68.93:5000/predict"

        // Ruta de la imagen que deseas enviar
        val image_path = "carton.jpg"

        // Envía la imagen al servidor
        GlobalScope.launch(Dispatchers.IO) {
            val response = HttpClient(Android) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }.use { client ->
                val result: ServerResponse = client.post(url) {
                    body = ImageFile(image_path)
                }
                // Imprime la respuesta del servidor
                println(result)
            }
        }
    }
}

@Serializable
data class ImageFile(val image: String)

// A surface container using the 'background' color from the theme
@Composable
fun Greeting() {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val theme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello, Android Compose!",
            color = colors.primary,
            style = typography.headlineLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PruebaTheme {
        Greeting()
    }
}
