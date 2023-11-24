package com.example.compose22

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

val articulos = mutableStateListOf<Articulo>()
fun ListadoArticulos(activity: Activity) {
    val url = "https://remindersgarciadopico.000webhostapp.com/listararticulos.php"
    val requestQueue = Volley.newRequestQueue(activity)
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET,
        url,
        null,
        { response ->
            val jsonArray = response.getJSONArray("lista")
            articulos.clear()
            for (i in 0 until jsonArray.length()) {
                val registro = jsonArray.getJSONObject(i)
                val codigo = registro.getString("codigo")
                val descripcion = registro.getString("descripcion")
                val precio = registro.getString("precio")
                articulos.add(Articulo(codigo.toInt(), descripcion,
                    precio.toFloat()))
            }
        },
        { error ->
        }
    )
    requestQueue.add(jsonObjectRequest)
}
@Composable
fun PantallaPrincipal(navHostController: NavHostController) {
    LazyColumn() {
        items(articulos) { articulo ->
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column() {
                    Text(
                        text = "Codigo: ${articulo.codigo}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(
                            start = 10.dp, end = 10.dp,
                            bottom = 5.dp
                        )
                    )
                    Text(
                        text = "Descripcion: ${articulo.descripcion}",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                    Text(
                        text = "Precio:${articulo.precio}",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }

}