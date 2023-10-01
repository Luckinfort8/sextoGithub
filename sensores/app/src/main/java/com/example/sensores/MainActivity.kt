package com.example.sensores

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lightSensor: Sensor? = null
    private var orientationSensor: Sensor? = null
    private lateinit var accelerationTextView: TextView
    private lateinit var lightTextView: TextView
    private lateinit var orientationTextView: TextView
    private var sensorsEnabled = true
    private var accelerationMagnitude: Float? = null
    private val lightUmbral = 100.0
    private lateinit var vibrator: Vibrator


    private lateinit var toggleSensorButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        accelerationTextView = findViewById(R.id.accelerationTextView)
        lightTextView = findViewById(R.id.lightTextView)
        orientationTextView = findViewById(R.id.orientationTextView)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        toggleSensorButton = findViewById(R.id.toggleSensorButton)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        toggleSensorButton.setOnClickListener {
            if (sensorsEnabled) {
                sensorManager.unregisterListener(this)
                sensorsEnabled = false
                toggleSensorButton.text = "Habilitar sensores"

                vibrator.vibrate(100)
            } else {
                accelerometer?.also { accelerometer ->
                    sensorManager.registerListener(this, accelerometer,
                        SensorManager.SENSOR_DELAY_NORMAL)
                }

                lightSensor?.also { lightSensor ->
                    sensorManager.registerListener(this, lightSensor,
                        SensorManager.SENSOR_DELAY_NORMAL)
                }

                orientationSensor?.also { orientationSensor ->
                    sensorManager.registerListener(this, orientationSensor,
                        SensorManager.SENSOR_DELAY_NORMAL)
                }

                sensorsEnabled = true
                toggleSensorButton.text = "Deshabilitar sensores"

                vibrator.vibrate(50)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
        lightSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
        orientationSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario hacer nada aquí.
    }
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor.type) {
                Sensor.TYPE_ACCELEROMETER -> {
                    val accelerationX = event.values[0]
                    val accelerationY = event.values[1]
                    val accelerationZ = event.values[2]
                    // Calcular la magnitud de la aceleración total
                    accelerationMagnitude = Math.sqrt(
                        (accelerationX * accelerationX +
                                accelerationY * accelerationY +
                                accelerationZ * accelerationZ).toDouble()
                    ).toFloat()

                    // Actualizar el TextView con la magnitud de la aceleración total
                    accelerationTextView.text = "Aceleración:\nX: $accelerationX\nY: $accelerationY\nZ: $accelerationZ\nMagnitud: $accelerationMagnitude"
                }
                Sensor.TYPE_LIGHT -> {
                    val lightValue = event.values[0]
                    lightTextView.text = "Luz: $lightValue"

                    //verificar si la luz esta por debajo del umbral
                    if (lightValue < lightUmbral) {
                        // Si la luz está por debajo del umbral, mostrar un mensaje
                        // en el TextView
                        lightTextView.text = "Luz: $lightValue\nNo veo :("
                    } else {
                        // Si la luz está por encima del umbral, mostrar un mensaje
                        // en el TextView
                        lightTextView.text = "Luz: $lightValue\nYa veo :)."
                    }
                }
                Sensor.TYPE_ORIENTATION -> {
                    val azimuth = event.values[0]
                    val pitch = event.values[1]
                    val roll = event.values[2]
                    orientationTextView.text = "Orientación:\nAzimuth: $azimuth\nPitch: $pitch\nRoll: $roll"
                }
            }
        }
    }

}