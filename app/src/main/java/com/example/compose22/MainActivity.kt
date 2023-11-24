package com.example.compose22

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.volley.Request
import org.json.JSONException
import org.json.JSONObject
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.compose22.ui.theme.Compose22Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListadoArticulos(this)
        setContent {
            Compose22Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "Botones") {
                        composable("Botones") { botones(navController) }
                        composable("ConsultarArticulo") { ConsultaArticulo(navController) }
                        composable("InsertaArticulo") { InsertaArticulo(navController) }
                        composable("ListadoArticulos") { PantallaPrincipal(navController) }
                        composable("ConsultaModificarDeleteArticulo") { ConsultaModificaDeleteArticulo(navController) }
                    }
                }
            }
        }
    }
}

data class Articulo(val codigo: Int, val descripcion: String, val precio: Float)
