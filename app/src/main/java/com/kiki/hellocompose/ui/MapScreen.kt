package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.google.maps.android.compose.rememberCameraPositionState
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    val atasehir = LatLng(40.9971, 29.1007)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(atasehir, 15f)
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
            // Peta Google di bawah lingkaran
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            )

            // Lingkaran kecil merah di tengah layar
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
fun MapScreenPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        MapScreen(navController)
    }
}


