package com.kiki.hellocompose.ui


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
                    composable("googleMaps") { GoogleMaps(navController) }
                    composable("googleMapsMarker") { GoogleMapsMarker(navController) }
                    composable("googleMapsMarkerMultiple") { GoogleMapsMultipleMarker(navController) }
                    composable("googleMapsPolygon") { GoogleMapsPolygon(navController) }
                    composable("googleMapsPolygonLabelCenter") { GoogleMapsPolygonLabelCenter (navController) }
                    composable("googleMapsCreatePolygon") { GoogleMapsCreatePolygon(navController) }
                    composable("googleMapsCreatePolygonMarkerAngka") { GoogleMapsCreatePolygonDynamicAngka(navController) }
                    composable("googleMapsCreatePolygonMarkerAngkaTambah") { GoogleMapsCreatePolygonDynamicAngkaTambah(navController) }
                    composable("googleMapsPolygonMarkerDynamicAngkaTambahSimpanReset") { GoogleMapsCreatePolygonDynamicAngkaTambahSimpanReset (navController) }

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
            // GOOGLE MAPS
            Button(
                onClick = {
                    navController.navigate("googleMaps")
                }
            ) {
                Text("Google Maps")
            }

            // GOOGLE MAPS MARKER
            Button(
                onClick = {
                    navController.navigate("googleMapsMarker")
                }
            ) {
                Text("Marker")
            }

            // GOOGLE MAPS MARKER MULTIPLE
            Button(
                onClick = {
                    navController.navigate("googleMapsMarkerMultiple")
                }
            ) {
                Text("Marker Multiple")
            }

            // GOOGLE MAPS POLYGON
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygon")
                }
            ) {
                Text("Polygon")
            }

            // GOOGLE MAPS POLYGON LABEL CENTER
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonLabelCenter")
                }
            ) {
                Text("Polygon Label Center")
            }

            // GOOGLE MAPS CREATE POLYGON
            Button(
                onClick = {
                    navController.navigate("googleMapsCreatePolygon")
                }
            ) {
                Text("Create Polygon")
            }

            // GOOGLE MAPS CREATE POLYGON MARKER DYNAMIC ANGKA
            Button(
                onClick = {
                    navController.navigate("googleMapsCreatePolygonMarkerAngka")
                }
            ) {
                Text("Create Polygon Marker Angka")
            }

            // GOOGLE MAPS CREATE POLYGON MARKER DYNAMIC ANGKA TAMBAH
            Button(
                onClick = {
                    navController.navigate("googleMapsCreatePolygonMarkerAngkaTambah")
                }
            ) {
                Text("Create Polygon Marker Angka Tambah")
            }

            // GOOGLE MAPS CREATE POLYGON MARKER DYNAMIC ANGKA TAMBAH SIMPAN RESET
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonMarkerDynamicAngkaTambahSimpanReset")
                }
            ) {
                Text("Create Polygon Marker Angka Tambah Simpan Reset")
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