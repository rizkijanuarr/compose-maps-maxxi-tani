package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
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
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.kiki.hellocompose.ui.theme.HelloComposeTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolygonAddMarkerDynamicAngkaScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polygon Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("google_map_polygon_add_marker_angka_screen") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Google Maps Polygon Add Marker")
                    }
                }
            )
        }
    ) { paddingValues ->
        addPolygonMarker(Modifier.padding(paddingValues))
    }
}

@Preview(showBackground = true)
@Composable
fun PolygonAddMarkerDynamicAngkaScreenPreview() {
    HelloComposeTheme {
        val navController = rememberNavController()
        PolygonAddMarkerDynamicAngkaScreen(navController)
    }
}

@Composable
private fun addPolygonMarker(modifier: Modifier = Modifier) {
    // variable untuk menahan titik poligon dan jumlah penanda
    val polygonPoints = remember { mutableStateListOf<LatLng>() }
    val initialPosition = LatLng(-7.318566892922274, 112.7328354352605)

    // memusatkan kamera ke titik awal
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 13f)
    }

    // variable untuk penomoran dinamis
    var markerCount by remember { mutableStateOf(1) }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng ->
            // tambahkan titik baru ke poligon ketika di klik
            polygonPoints.add(latLng)
        }
    ) {
        // menggambar poligon sejumlah banyaknya titik
        if (polygonPoints.size >= 4) {
            Polygon(
                points = polygonPoints,
                clickable = true,
                fillColor = Color(0x5500FF00),
                strokeColor = Color.Blue,
                strokeWidth = 5f,
            )
        }

        // di loop untuk membuat penanda
        polygonPoints.forEachIndexed { index, point ->
            val markerIcon = createMarkerIcon(index + 1)

            Marker(
                state = MarkerState(position = point),
                icon = markerIcon,
                title = "Marker ${index + 1}",
            )
        }
    }
}

// function untuk membuat ikon penanda dengan angka
fun createMarkerIcon(number: Int): BitmapDescriptor {
    // membuat bitmap untuk ikon khusus
    val markerSize = 100
    val bitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    // action menggambar ikon penanda
    paint.color = android.graphics.Color.RED
    canvas.drawCircle(markerSize / 2f, markerSize / 2f, markerSize / 2f, paint)

    // menggambar angka dengan circle agar ke tengah
    paint.color = android.graphics.Color.WHITE
    paint.textSize = 50f
    paint.textAlign = Paint.Align.CENTER
    val textY = (canvas.height / 2f - (paint.descent() + paint.ascent()) / 2)
    canvas.drawText(number.toString(), (canvas.width / 2f), textY, paint)

    // kembalikan bitmap
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


