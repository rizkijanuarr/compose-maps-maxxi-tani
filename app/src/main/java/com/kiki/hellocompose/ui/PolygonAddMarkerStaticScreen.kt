package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import android.media.MediaParser.SeekPoint
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolygonAddMarkerStaticScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polygon Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("google_map_polygon_add_marker_screen") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Google Maps Polygon Add Marker")
                    }
                }
            )
        }
    ) {
        addPolygonMarker()
    }
}

@Preview(showBackground = true)
@Composable
fun PolygonAddMarkerScreenPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        PolygonAddMarkerStaticScreen(navController)
    }
}

@Composable
private fun addPolygonMarker() {

    val titikPolygon = listOf(
        LatLng(-7.318566892922274, 112.7328354352605),
        LatLng(-7.307555371879924, 112.7241815246647),
        LatLng(-7.305154639882503, 112.73583063198765)
    )
    val centerPoint = calculate(titikPolygon)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerPoint, 13f)
    }

    var isPolygonSelected by remember { mutableStateOf(false) }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Polygon(
            points = titikPolygon,
            clickable = true,
            fillColor = if (isPolygonSelected) Color.Red else Color.Green,
            strokeColor = Color.Blue,
            strokeWidth = 5f,
            tag = "Hello!",
            onClick = { polygon ->
                // Handle polygon click event
                isPolygonSelected = true
            }
        )
        Marker(
            state = MarkerState(position = centerPoint),
            title = "Hello!"
        )
    }
}

fun calculate(points: List<LatLng>): LatLng {
    var latSum = 0.0
    var lngSum = 0.0
    for (point in points) {
        latSum += point.latitude
        lngSum += point.longitude
    }
    var centerLat = latSum / points.size
    var centerLng = lngSum / points.size
    return LatLng(centerLat, centerLng)
}
