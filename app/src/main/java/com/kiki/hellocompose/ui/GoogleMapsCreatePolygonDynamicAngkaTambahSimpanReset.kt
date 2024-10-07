package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.maps.android.compose.CameraPositionState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleMapsCreatePolygonDynamicAngkaTambahSimpanReset(navController: NavController) {

    var canAddMarker by remember { mutableStateOf(false) }
    val polygonPoints = remember { mutableStateListOf<LatLng>() }
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current
    var initialPosition = LatLng(-7.318566892922274, 112.7328354352605)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polygon Screen Tambah Simpan") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("google_map_polygon_add_marker_angka_screen") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Google Maps Polygon Add Marker")
                    }
                }
            )
        },
        floatingActionButton = {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {

                FloatingActionButton(
                    onClick = {
                        // Cek apakah posisi kamera valid sebelum menambahkan marker
                        val centerPosition = cameraPositionState.position.target
                        if (centerPosition != null && centerPosition.latitude != 0.0 && centerPosition.longitude != 0.0) {
                            polygonPoints.add(centerPosition)
                            canAddMarker = true
                            Log.d("PolygonScreen", "Titik ditambahkan: $centerPosition")
                            Toast.makeText(context, "Titik ditambahkan: $centerPosition", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.e("PolygonScreen", "Posisi kamera tidak valid.")
                        }
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.Blue
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Titik")
                }

                FloatingActionButton(
                    onClick = {
                        val previousCount = polygonPoints.size
                        resetPolygonPoints(polygonPoints)
                        polygonPoints.clear()
                        canAddMarker = false
                        Log.d("PolygonScreen", "Semua titik direset.")
                        Toast.makeText(context, "Titik direset: $previousCount", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.Red
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset Markers")
                }

                FloatingActionButton(
                    onClick = {
                        savePolygonPoints(polygonPoints)
                        Log.d("PolygonScreen", "Titik disimpan.")
                        Toast.makeText(context, "Titik disimpan: ${polygonPoints.size}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.Green
                ) {
                    Icon(Icons.Default.Save, contentDescription = "Simpan Titik")
                }

                // Tombol Hapus terakhir
                if (polygonPoints.isNotEmpty()) {
                    FloatingActionButton(
                        onClick = {
                            deletePolygonPoints(polygonPoints)
                            val removedPoint = polygonPoints.last()
                            polygonPoints.removeAt(polygonPoints.lastIndex)
                            Log.d("PolygonScreen", "Titik dihapus: $removedPoint")
                            Toast.makeText(context, "Titik terakhir dihapus: $removedPoint", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.padding(16.dp),
                        containerColor = Color.Magenta
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = "Hapus Titik Terakhir")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            addPolygonMarker(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                polygonPoints = polygonPoints,
                cameraPositionState = cameraPositionState,
                onDistanceUpdate = { distance ->
                    var distanceText = String.format("%.2f m", distance)
                }
            )
        }
    }
}

@Composable
private fun addPolygonMarker(
    modifier: Modifier = Modifier,
    polygonPoints: SnapshotStateList<LatLng>,
    cameraPositionState: CameraPositionState,
    onDistanceUpdate: (Double) -> Unit
) {
    var distanceText: String by remember { mutableStateOf("0.0 m") }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            polygonPoints.forEachIndexed { index, point ->
                val markerIcon = createMarkerIconForTambahSimpanScreen(index + 1)

                Marker(
                    state = MarkerState(position = point),
                    icon = markerIcon,
                    title = "Marker ${index + 1}",
                )
            }

            if (polygonPoints.size >= 3) {
                Polygon(
                    points = polygonPoints,
                    clickable = true,
                    fillColor = Color(0x5500FF00),
                    strokeColor = Color.Blue,
                    strokeWidth = 5f,
                )
            }

            if (polygonPoints.size >= 2) {
                val distance = calculateDistance(polygonPoints[polygonPoints.size - 2], polygonPoints.last())
                onDistanceUpdate(distance)
                distanceText = "Jarak: ${distance.toInt()} m"
            }
        }

        Box(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center)
                .background(Color.Red, shape = CircleShape)
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-30).dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(4.dp)
        ) {
            Text(text = distanceText, color = Color.Black)
        }
    }
}


fun createMarkerIconForTambahSimpanScreen(number: Int): BitmapDescriptor {
    val markerSize = 100
    val bitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    paint.color = android.graphics.Color.RED
    canvas.drawCircle(markerSize / 2f, markerSize / 2f, markerSize / 2f, paint)

    paint.color = android.graphics.Color.WHITE
    paint.textSize = 50f
    paint.textAlign = Paint.Align.CENTER
    val textY = (canvas.height / 2f - (paint.descent() + paint.ascent()) / 2)
    canvas.drawText(number.toString(), (canvas.width / 2f), textY, paint)

    // Mengembalikan BitmapDescriptor
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

private fun savePolygonPoints(polygonPoints: SnapshotStateList<LatLng>) {
    val pointsJson = polygonPoints.map { latLng ->
        mapOf("latitude" to latLng.latitude, "longitude" to latLng.longitude)
    }

    val json = Gson().toJson(pointsJson)
    Log.d("Simpan Polygon Points", json)
}

private fun resetPolygonPoints(polygonPoints: SnapshotStateList<LatLng>) {
    val pointsJson = polygonPoints.map { latLng ->
        mapOf("latitude" to latLng.latitude, "longitude" to latLng.longitude)
    }

    val json = Gson().toJson(pointsJson)
    Log.d("Reset Polygon Points", json)
}

private fun deletePolygonPoints(polygonPoints: SnapshotStateList<LatLng>) {
    if (polygonPoints.isNotEmpty()) {
        val lastPoint = polygonPoints.last()
        Log.d("Hapus Polygon Points", "Titik dihapus: $lastPoint")
    }
}

private fun calculateDistance(start: LatLng, end: LatLng): Double {
    val R = 6371e3
    val φ1 = Math.toRadians(start.latitude)
    val φ2 = Math.toRadians(end.latitude)
    val Δφ = Math.toRadians(end.latitude - start.latitude)
    val Δλ = Math.toRadians(end.longitude - start.longitude)

    val a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
            Math.cos(φ1) * Math.cos(φ2) *
            Math.sin(Δλ / 2) * Math.sin(Δλ / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    return R * c
}