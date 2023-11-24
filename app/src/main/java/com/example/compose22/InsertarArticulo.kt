package com.example.compose22

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.android.volley.Request
import org.json.JSONObject
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertaArticulo(navController: NavController) {
    val contexto = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var descripcion by remember { mutableStateOf("") }
        var precio by remember { mutableStateOf("") }
        var mensaje by remember { mutableStateOf("") }
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = {
                Text("Descripción")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true
        )
        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = {
                Text("Precio")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(
            onClick = {
                AltaArticulo(
                    descripcion = descripcion,
                    precio = precio,
                    contexto = contexto,
                    respuesta = {
                        if (it) {
                            mensaje = "se cargaron los datos"
                            descripcion = ""
                            precio = ""
                        } else
                            mensaje = "problemas en la carga"
                    }
                )
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Agregar")
        }
        Text(text = "$mensaje")
    }
}

fun AltaArticulo(
    descripcion: String, precio: String, contexto: Context, respuesta:
        (Boolean) -> Unit
) {
    val requestQueue = Volley.newRequestQueue(contexto)
    val url = "https://remindersgarciadopico.000webhostapp.com/insertar.php"
    val parametros = JSONObject()
    parametros.put("descripcion", descripcion)
    parametros.put("precio", precio)
    val requerimiento = JsonObjectRequest(Request.Method.POST,
        url,
        parametros,
        { response ->
            if (response.get("respuesta").toString().equals("ok"))
                respuesta(true)
            else
                respuesta(false)
        },
        { error ->
            respuesta(false)
        }
    )
    requestQueue.add(requerimiento)
}