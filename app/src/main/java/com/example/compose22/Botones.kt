package com.example.compose22

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun botones(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.baseDeDatos),
            fontSize = 40.sp,
            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.size(50.dp))

            Button(
                onClick = { navController.navigate("ConsultarArticulo") }, modifier = Modifier

                    .padding(15.dp, 0.dp)
            ) {
                Text(text = "Consultar Articulo")
            }
            Button(
                onClick = { navController.navigate("InsertaArticulo") }, modifier = Modifier

                    .padding(15.dp, 0.dp)
            ) {
                Text(text = "Inserta Articulo")
            }
            Button(
                onClick = { navController.navigate("ListadoArticulos") }, modifier = Modifier

                    .padding(15.dp, 0.dp)
            ) {
                Text(text = "Listado Articulos")
            }
            Button(
                onClick = { navController.navigate("ConsultaModificarDeleteArticulo") }, modifier = Modifier
                    .padding(15.dp, 0.dp)
            ) {
                Text(text = "Delete Articulo")
            }

    }
}