package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var imageView: ImageView
    private lateinit var btnClassify: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        btnClassify = findViewById(R.id.btnClassify)

        btnClassify.setOnClickListener {
            // Al hacer clic en el botón, enviar la imagen al servidor y recibir la clasificación
            uploadImageAndClassify()
        }

        // Abrir la cámara al iniciar la aplicación
        dispatchTakePictureIntent()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        }
    }

    private fun uploadImageAndClassify() {
        // Convertir la imagen a Base64 para enviarla al servidor
        val imageBitmap = (imageView.drawable).toBitmap()
        val base64Image = bitmapToBase64(imageBitmap)

        // Añade esta verificación
        if (base64Image.isNullOrEmpty()) {
            println("Error: base64Image is null or empty")
            return
        }

        // Configurar Retrofit para realizar la solicitud al servidor
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.22.156:5000/")  // Reemplaza con la dirección IP de tu servidor Flask
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        // Crear una solicitud multipart con la imagen en Base64
        val imageBody = RequestBody.create("text/plain".toMediaTypeOrNull(), base64Image)
        val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageBody)

        // Enviar la solicitud al servidor
        val call = service.uploadImage(imagePart)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val classification = response.body()?.classification
                    // Mostrar la clasificación en un cuadro de texto
                    // (Añadir la lógica para mostrarlo en tu aplicación)
                    println("Clasificación: $classification")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                // Manejar el fallo de la solicitud
                t.printStackTrace()
            }
        })
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}