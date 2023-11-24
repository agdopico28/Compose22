package com.example.compose22

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavHostController
import com.android.volley.Request
import org.json.JSONException
import org.json.JSONObject
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonObjectRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaModificaDeleteArticulo(navHostController: NavHostController) {
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
                ConsultaCodigoArticulo(
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
        Button(
            onClick = {
                Modificar(
                    articulo =
                    Articulo(codigo.toInt(),descripcion,precio.toFloat()),
                    respuesta = {
                        if (it)
                            mensaje="Los datos fueron modificados"
                        else
                            mensaje = "No existe el código de producto ingresado"
                    },
                    contexto = contexto
                )
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Modificar")
        }
        Button(
            onClick = {
                Borrar(
                    codigo = codigo,
                    respuesta = {
                        if (it) {
                            mensaje = "Se eliminó el artículo"
                            codigo=""
                            descripcion=""
                            precio=""
                        } else
                            mensaje = "No existe el código de producto ingresado"
                    },
                    contexto = contexto
                )
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Borrar artículo (ingresando código)")
        }
        Text(text = "$mensaje")
    }
}

fun ConsultaCodigoArticulo(codigo: String, respuesta: (Articulo?) -> Unit, contexto: Context) {
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
fun Modificar(articulo: Articulo, respuesta: (Boolean) -> Unit, contexto: Context)
{
    val requestQueue = Volley.newRequestQueue(contexto)
    val url = "https://remindersgarciadopico.000webhostapp.com/modificar.php"
    val parametros = JSONObject()
    parametros.put("codigo", articulo.codigo.toString())
    parametros.put("descripcion", articulo.descripcion)
    parametros.put("precio", articulo.precio.toString())
    val requerimiento = JsonObjectRequest(
        Request.Method.POST,
        url,
        parametros,
        { response ->
            try {
                val resu = response["resultado"].toString()
                if (resu == "1")
                    respuesta(true)
                else
                    respuesta(false)
            } catch (e: JSONException) {
                respuesta(false)
            }
        }
    ) { error -> respuesta(false) }
    requestQueue.add(requerimiento)
}
fun Borrar(codigo: String, respuesta: (Boolean) -> Unit, contexto: Context) {
    val requestQueue = Volley.newRequestQueue(contexto)
    val url = "https://remindersgarciadopico.000webhostapp.com/borrar.php"
    val parametros = JSONObject()
    parametros.put("codigo", codigo)
    val requerimiento = JsonObjectRequest(
        Request.Method.POST,
        url,
        parametros,
        { response ->
            try {
                val resu = response["resultado"].toString()
                if (resu == "1")
                    respuesta(true)
                else
                    respuesta(false)
            } catch (e: JSONException) {
                respuesta(false)
            }
        }
    ) { error -> respuesta(false) }
    requestQueue.add(requerimiento)
}