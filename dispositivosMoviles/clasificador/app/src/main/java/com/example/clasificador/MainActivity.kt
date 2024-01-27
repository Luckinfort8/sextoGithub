package com.example.clasificador

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clasificador.ui.theme.ClasificadorTheme
import okhttp3.Call
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiService {
    @Multipart
    @POST("/predict")
    fun uploadImage(@Part image: MultipartBody.Part): Call<PredictionResponse>
}

data class PredictionResponse(val predicted_class: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClasificadorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Pass your Flask server IP and port
                    GPT3ComposeApp("http://172.28.0.12:5002")
                }
            }
        }
    }
}

@Composable
fun GPT3ComposeApp(serverBaseUrl: String) {
    var responseText by remember { mutableStateOf("") }
    var imageFile by remember { mutableStateOf<File?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Display image here (if available)
        imageFile?.let { file ->
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }

        // Button to select image
        Button(
            onClick = {
                // Open image picker
                // Set the selected image file to the 'imageFile' variable
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Image")
        }

        // Button to send image to Flask server
        Button(
            onClick = {
                // Call your Flask server API to send the image and get the response
                imageFile?.let {
                    sendImageToServer(serverBaseUrl, it) { result ->
                        responseText = result.predicted_class
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send to Server")
        }

        // Display server response
        if (responseText.isNotEmpty()) {
            Text(
                text = responseText,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun sendImageToServer(serverBaseUrl: String, imageFile: File, onResult: (PredictionResponse) -> Unit) {
    val retrofit = Retrofit.Builder()
        .baseUrl(serverBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    val requestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
    val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestBody)

    apiService.uploadImage(imagePart).enqueue { _, response ->
        if (response.isSuccessful) {
            response.body()?.let { predictionResponse ->
                onResult(predictionResponse)
            }
        } else {
            // Handle error
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GPT3ComposeAppPreview() {
    ClasificadorTheme {
        GPT3ComposeApp("http://172.28.0.12:5002")
    }
}