package com.example.parcial1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial1.ui.theme.Parcial1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parcial1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NotaValidatorUI()
                    }
                }
            }
        }
    }
}

@Composable
fun NotaValidatorUI() {
    var nota by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(Color(0xFFADD8E6)), // color celeste claro
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Parcial #1", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))
            Text("Reggie Guevara 8-995-1393")
            Text("Gabriel Cedeño 8-1001-1537")

            Spacer(modifier = Modifier.height(24.dp))
            Text("Ingrese la nota a validar", fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = nota,
                onValueChange = { nota = it },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.width(200.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val puntaje = nota.toIntOrNull()
                    resultado = when {
                        puntaje == null -> "Por favor ingrese una nota válida"
                        puntaje in 91..100 -> "A (Excelente)"
                        puntaje in 81..90 -> "B (Bueno)"
                        puntaje in 71..80 -> "C (Regular)"
                        puntaje in 61..70 -> "D (Mas o menos regular)"
                        puntaje < 61 -> "No Aprobado"
                        else -> "Nota fuera de rango"
                    }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.width(150.dp)
            ) {
                Text("Validar")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(resultado, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }


    }
}
