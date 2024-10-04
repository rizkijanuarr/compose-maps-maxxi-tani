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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapsMultipleMarker(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marker Multiple") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Marker Multiple")
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
fun PolygonMapScreenPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        GoogleMapsMultipleMarker(navController)
    }
}

@Composable
private fun Add() {
    val golf = LatLng(-7.300998, 112.714854)
    val pasarMalam = LatLng(-7.299594, 112.723824)
    val hayamWuruk = LatLng(-7.301062, 112.725004)
    val cameraPositionState = rememberCameraPositionState {
        var positionGolf = CameraPosition.fromLatLngZoom(golf, 15f)
        var positionPasarMalam = CameraPosition.fromLatLngZoom(pasarMalam, 15f)
        var positionhayamWuruk = CameraPosition.fromLatLngZoom(hayamWuruk, 15f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = golf),
            title = "Marker in Golf"
        )
        Marker(
            state = MarkerState(position = pasarMalam),
            title = "Marker in Pasar Malam"
        )
        Marker(
            state = MarkerState(position = hayamWuruk),
            title = "Marker in Hayam Wuruk"
        )
    }
}

