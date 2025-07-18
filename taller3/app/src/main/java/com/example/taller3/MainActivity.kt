package com.example.taller3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.taller3.ui.theme.Taller3Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Taller3Theme {
                GuessNumberGame()
            }
        }
    }
}

@Composable
fun GuessNumberGame() {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var message by remember { mutableStateOf("¡Adivina el número entre 0 y 100!") }
    var remainingAttempts by remember { mutableStateOf(3) }
    val targetNumber = remember { Random.nextInt(0, 101) }
    var gameOver by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = message, style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Ingresa un número") },
            enabled = !gameOver
        )

        Button(
            onClick = {
                val guess = userInput.text.toIntOrNull()
                if (guess == null || guess !in 0..100) {
                    message = "Por favor, ingresa un número válido entre 0 y 100."
                    return@Button
                }

                if (guess == targetNumber) {
                    message = "🎉 ¡Correcto! El número era $targetNumber."
                    gameOver = true
                } else {
                    remainingAttempts--
                    if (remainingAttempts == 0) {
                        message = "❌ No adivinaste. El número era $targetNumber."
                        gameOver = true
                    } else {
                        val hint = if (guess < targetNumber) "mayor" else "menor"
                        message = "Incorrecto. El número es $hint. Te quedan $remainingAttempts intento(s)."
                    }
                }
                userInput = TextFieldValue("")
            },
            enabled = !gameOver
        ) {
            Text("Adivinar")
        }

        if (gameOver) {
            Button(onClick = {
                userInput = TextFieldValue("")
                message = "¡Adivina el número entre 0 y 100!"
                remainingAttempts = 3
                gameOver = false
            }) {
                Text("Reiniciar juego")
            }
        }
    }
}
