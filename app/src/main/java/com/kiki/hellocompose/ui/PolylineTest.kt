package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolylineTest(navController: NavController) {
    val atasehir = LatLng(40.9971, 29.1007)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(atasehir, 15f)
    }

    var uiSettings by remember {
        mutableStateOf(MapUiSettings(zoomControlsEnabled = true))
    }

    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.HYBRID))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Map Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("google_map_screen") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Google Maps")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val routeCoordinates = listOf(
                LatLng(40.9967,29.0570),
                LatLng(40.9900,30.0570),
                LatLng(41.0322,29.0216),
                LatLng(41.0333,29.0910)
            )

            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings
            ) {
                Polyline(
                    points = routeCoordinates,
                    clickable = true,
                    color = Color.Blue,
                    width = 5f
                )
            }

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
                    .background(Color.Red, shape = CircleShape)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PolylineTestPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        MapScreen(navController)
    }
}


