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
                    composable("polylineStatic") { PolylineStatic(navController) }
                    
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
                Text("1 Google Maps")
            }

            // GOOGLE MAPS MARKER
            Button(
                onClick = {
                    navController.navigate("googleMapsMarker")
                }
            ) {
                Text("2 Marker")
            }

            // GOOGLE MAPS MARKER MULTIPLE
            Button(
                onClick = {
                    navController.navigate("googleMapsMarkerMultiple")
                }
            ) {
                Text("3 Marker Multiple")
            }

            // GOOGLE MAPS POLYGON
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygon")
                }
            ) {
                Text("4 Polygon")
            }

            // GOOGLE MAPS POLYGON LABEL CENTER
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonLabelCenter")
                }
            ) {
                Text("5 Polygon Label Center")
            }

            // GOOGLE MAPS CREATE POLYGON
            Button(
                onClick = {
                    navController.navigate("googleMapsCreatePolygon")
                }
            ) {
                Text("6 Create Polygon")
            }

            // GOOGLE MAPS CREATE POLYGON MARKER DYNAMIC ANGKA
            Button(
                onClick = {
                    navController.navigate("googleMapsCreatePolygonMarkerAngka")
                }
            ) {
                Text("7 Create Polygon Marker Angka")
            }

            // GOOGLE MAPS CREATE POLYGON MARKER DYNAMIC ANGKA TAMBAH
            Button(
                onClick = {
                    navController.navigate("googleMapsCreatePolygonMarkerAngkaTambah")
                }
            ) {
                Text("8 Create Polygon Marker Angka Tambah")
            }

            // GOOGLE MAPS CREATE POLYGON MARKER DYNAMIC ANGKA TAMBAH SIMPAN RESET
            Button(
                onClick = {
                    navController.navigate("googleMapsPolygonMarkerDynamicAngkaTambahSimpanReset")
                }
            ) {
                Text("9 Create Polygon Marker Angka Tambah Simpan Reset")
            }

            // GOOGLE MAPS POLYLINE STATIC
            Button(
                onClick = {
                    navController.navigate("polylineStatic")
                }
            ) {
                Text("Polyline Static")
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