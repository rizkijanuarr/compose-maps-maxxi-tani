package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapsCreatePolygon(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polygon Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Create Polygon")
                    }
                }
            )
        }
    ) { paddingValues ->
        Add(Modifier.padding(paddingValues))
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleMapsCreatePolygonPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        GoogleMapsCreatePolygon(navController)
    }
}

@Composable
private fun Add(modifier: Modifier = Modifier) {
    // State to hold polygon points
    val polygonPoints = remember { mutableStateListOf<LatLng>() }
    val initialPosition = LatLng(-7.318566892922274, 112.7328354352605)

    // Center the camera to the first point
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 13f)
    }

    var isPolygonSelected by remember { mutableStateOf(false) }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            // Add new point to polygon when the map is clicked
            polygonPoints.add(latLng)
        }
    ) {
        // Draw the polygon if there are at least 3 points
        if (polygonPoints.size >= 3) {
            Polygon(
                points = polygonPoints,
                clickable = true,
                fillColor = if (isPolygonSelected) Color.Red else Color.Green,
                strokeColor = Color.Blue,
                strokeWidth = 5f,
                onClick = { polygon ->
                    // Handle polygon click event
                    isPolygonSelected = true
                }
            )
        }

        // Add markers for each point
        polygonPoints.forEach { point ->
            Marker(
                state = MarkerState(position = point),
                title = "Marker at (${point.latitude}, ${point.longitude})"
            )
        }
    }
}

