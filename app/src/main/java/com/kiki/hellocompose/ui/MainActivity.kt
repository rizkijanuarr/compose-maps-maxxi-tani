package com.kiki.hellocompose.ui

import BasicMapQuestScreen
import LocationMapQuestScreen
import MapZoomQuestScreen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kiki.hellocompose.data.api.Network
import com.kiki.hellocompose.data.response.AddressResponse
import com.kiki.hellocompose.ui.theme.HelloComposeTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("basicMapQuest") { BasicMapQuestScreen(navController) }
                    composable("mapZoomQuest") { MapZoomQuestScreen(navController) }
                    composable("mapLocationQuest") { LocationMapQuestScreen(navController) }
                    composable("googleMaps") { MapScreen(navController) }
                    composable("googleMapsMarker") { MarkerMapScreen(navController) }
                    composable("googleMapsMarkerMultiple") { MultipleMarkerMapScreen(navController) }
                    composable("googleMapsPolygon") { PolygonScreen(navController) }
                    composable("googleMapsPolygonMarker") { PolygonAddMarkerStaticScreen(navController) }
                    composable("googleMapsPolygonMarkerDynamic") { PolygonAddMarkerDynamicScreen(navController) }
                    composable("googleMapsPolygonMarkerDynamicAngka") { PolygonAddMarkerDynamicAngkaScreen(navController) }
                    composable("googleMapsPolygonMarkerDynamicAngkaTambah") { PolygonAddMarkerDynamicAngkaTambahScreen(navController) }
                    composable("googleMapsPolygonMarkerDynamicAngkaTambahSimpan") { PolygonAddMarkerDynamicAngkaTambahSimpanScreen(navController) }
                    composable("polylineTest") { PolylineTest(navController) }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController) {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    navController.navigate("basicMapQuest")
                }
            ) {
                Text("BasicMapQuest")
            }
            Button(
                onClick = {
                    navController.navigate("mapZoomQuest")
                }
            ) {
                Text("MapZoomQuest")
            }
            Button(
                onClick = {
                    navController.navigate("mapLocationQuest")
                }
            ) {
                Text("MapLocationQuest")
            }
            Button(
                onClick = {
                    navController.navigate("googleMaps")
                }
            ) {
                Text("GoogleMaps")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsMarker")
                }
            ) {
                Text("GoogleMapsMarker")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsMarkerMultiple")
                }
            ) {
                Text("GoogleMapsMarkerMultiple")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygon")
                }
            ) {
                Text("GoogleMapsPolygon")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonMarker")
                }
            ) {
                Text("GoogleMapsPolygonMarker")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonMarkerDynamic")
                }
            ) {
                Text("googleMapsPolygonMarkerDynamic")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonMarkerDynamicAngka")
                }
            ) {
                Text("googleMapsPolygonMarkerDynamicAngka")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonMarkerDynamicAngkaTambah")
                }
            ) {
                Text("google Maps Angka Tambah")
            }
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonMarkerDynamicAngkaTambahSimpan")
                }
            ) {
                Text("google Maps Angka Tambah Simpan")
            }
            Button(
                onClick = {
                    navController.navigate("polylineTest")
                }
            ) {
                Text("PolylineTest")
            }
            var getAddress by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    getAddress = true
                }
            ) {
                Text("Get Address")
            }
            LaunchedEffect(getAddress) {
                if (getAddress) {
                    getAddress = false
                    CoroutineScope(Dispatchers.IO).launch {
                        getAddress(navController)
                    }
                }
            }
        }
    }
}

private suspend fun getAddress(navController: NavController) {
    val service = Network.getService(navController.context)
    val addressResponse = service.getAddress( "Washington,DC")
    println(addressResponse.toString())
}