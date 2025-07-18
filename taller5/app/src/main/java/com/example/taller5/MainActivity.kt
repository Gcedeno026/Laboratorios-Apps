package com.taller6  // Asegúrate de que el nombre del paquete sea "com.taller6"

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech

    // State variables for input and Bingo numbers
    private var bingoNumbers = mutableListOf<Int>()
    private var uid by mutableStateOf("")
    private var dimension by mutableStateOf(5)  // Default 5x5 card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize TTS
        tts = TextToSpeech(this, this)

        setContent {
            BingoApp()
        }
    }

    // Function to generate Bingo numbers
    private fun generateBingo(dimension: Int) {
        bingoNumbers.clear()
        val random = Random()
        for (i in 1..(dimension * dimension)) {
            bingoNumbers.add(random.nextInt(75) + 1)  // Numbers between 1 and 75
        }
    }

    // Function to initialize TextToSpeech
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val langResult = tts.setLanguage(Locale.getDefault())
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Idioma no soportado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error de inicialización de TTS", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to free TTS resources
    override fun onDestroy() {
        super.onDestroy()
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }

    // Composable function for the Bingo app UI
    @Composable
    fun BingoApp() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = uid,
                onValueChange = { uid = it },
                label = { Text("Código alfanumérico del jugador (UID)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = dimension.toString(),
                onValueChange = { dimension = it.toIntOrNull() ?: 5 },  // Default 5x5 if input is invalid
                label = { Text("Dimensión de la carta de Bingo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                generateBingo(dimension)
            }) {
                Text("Generar Bingo")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Displaying generated Bingo numbers
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                bingoNumbers.forEach {
                    Text(
                        text = it.toString(),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        BingoApp()
    }
}
