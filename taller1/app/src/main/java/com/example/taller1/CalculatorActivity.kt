package com.example.taller1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Número 1") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Número 2") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val n1 = number1.toFloatOrNull() ?: 0f
                val n2 = number2.toFloatOrNull() ?: 0f
                result = (n1 + n2).toString()
            }) {
                Text("+")
            }
            Button(onClick = {
                val n1 = number1.toFloatOrNull() ?: 0f
                val n2 = number2.toFloatOrNull() ?: 0f
                result = (n1 - n2).toString()
            }) {
                Text("-")
            }
            Button(onClick = {
                val n1 = number1.toFloatOrNull() ?: 0f
                val n2 = number2.toFloatOrNull() ?: 0f
                result = (n1 * n2).toString()
            }) {
                Text("*")
            }
            Button(onClick = {
                val n1 = number1.toFloatOrNull() ?: 0f
                val n2 = number2.toFloatOrNull()
                result = if (n2 != null && n2 != 0f) {
                    (n1 / n2).toString()
                } else {
                    "Error"
                }
            }) {
                Text("/")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Resultado: $result")
    }
}
