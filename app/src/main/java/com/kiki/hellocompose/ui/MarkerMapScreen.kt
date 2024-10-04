package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkerMapScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Marker Map Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("google_map_marker_screen") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Google Maps Marker")
                    }
                }
            )
        }
    ) {
        mapWithMarker()
    }
}

@Preview(showBackground = true)
@Composable
fun MarkerMapScreenPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        MarkerMapScreen(navController)
    }
}

@Composable
private fun mapWithMarker() {
    val surabaya = LatLng(-7.257386, 112.757297)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(surabaya, 15f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = surabaya),
            title = "Marker in Surabaya"
        )
    }
}

