package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapsPolygon(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polygon") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Polygon")
                    }
                }
            )
        }
    ) {
        Add()
    }
}

@Preview(showBackground = true)
@Composable
fun GoogleMapsPolygonPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        GoogleMapsPolygon(navController)
    }
}

@Composable
private fun Add() {
    val titikPolygon = listOf(
        LatLng(-7.318566892922274, 112.7328354352605),
        LatLng(-7.307555371879924, 112.7241815246647),
        LatLng(-7.305154639882503, 112.73583063198765)
    )

    val latLng = LatLng(-7.318566892922274, 112.7328354352605)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 13f)
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
    }
}
