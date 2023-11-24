package com.example.compose22

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaArticulo(navHostController: NavHostController) {
    val contexto = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var codigo by remember { mutableStateOf("") }
        var descripcion by remember { mutableStateOf("") }
        var precio by remember { mutableStateOf("") }
        var mensaje by remember { mutableStateOf("") }

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = {
                Text("Código")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
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
                ConsultaCodigo(
                    codigo = codigo.toString(),
                    respuesta = {
                        if (it!=null) {
                            descripcion = it.descripcion
                            precio = it.precio.toString()
                            mensaje=""
                        } else {
                            mensaje = "No existe el código de producto ingresado"
                            descripcion=""
                            precio=""
                        }
                    },
                    contexto = contexto
                )
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Consultar por código")
        }
        Text(text = "$mensaje")
    }
}



fun ConsultaCodigo(codigo: String, respuesta: (Articulo?) -> Unit, contexto: Context) {
    val requestQueue = Volley.newRequestQueue(contexto)
    val url = "https://remindersgarciadopico.000webhostapp.com/consultar.php?codigo=$codigo"
    val requerimiento = JsonArrayRequest(
        Request.Method.GET,
        url,
        null,
        { response ->
            if (response.length() == 1) {
                try {
                    val objeto = JSONObject(response[0].toString())
                    val articulo = Articulo(
                        objeto.getString("codigo").toInt(),
                        objeto.getString("descripcion"),
                        objeto.getString("precio").toFloat()
                    )
                    respuesta(articulo)
                } catch (e: JSONException) {
                }
            }
            else
                respuesta(null);
        }
    ) { error ->
    }
    requestQueue.add(requerimiento)
}
