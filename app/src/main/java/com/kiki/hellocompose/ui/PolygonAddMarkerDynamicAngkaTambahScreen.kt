package com.kiki.hellocompose.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolygonAddMarkerDynamicAngkaTambahScreen(navController: NavController) {
    // State untuk mengontrol penambahan marker
    var canAddMarker by remember { mutableStateOf(false) }
    // State untuk menahan titik poligon
    val polygonPoints = remember { mutableStateListOf<LatLng>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polygon Screen Tambah Reset") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("google_map_polygon_add_marker_angka_screen") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Google Maps Polygon Add Marker")
                    }
                }
            )
        },
        floatingActionButton = {
            // Floating Action Button untuk menambah marker
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = {
                        canAddMarker = true // Aktifkan flag untuk menambah titik
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.Blue
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Titik")
                }

                FloatingActionButton(
                    onClick = {
                        // Reset markers
                        polygonPoints.clear()
                        canAddMarker = false // Nonaktifkan penambahan marker setelah reset
                    },
                    modifier = Modifier.padding(16.dp),
                    containerColor = Color.Red
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset Markers")
                }
            }
        }
    ) { paddingValues ->
        // Menambahkan kolom untuk peta
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            // Memanggil fungsi untuk menambahkan penanda dinamis
            addPolygonMarker(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                polygonPoints = polygonPoints,
                canAddMarker = canAddMarker,
                onMarkerAdded = {
                    // Setelah menambah titik, tetap aktif untuk penambahan selanjutnya
                }
            )
        }
    }
}

@Composable
private fun addPolygonMarker(
    modifier: Modifier = Modifier,
    polygonPoints: SnapshotStateList<LatLng>,
    canAddMarker: Boolean,
    onMarkerAdded: () -> Unit
) {
    val initialPosition = LatLng(-7.318566892922274, 112.7328354352605)

    // Memusatkan kamera ke titik awal
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialPosition, 13f)
    }

    Column(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.weight(1f),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                // Hanya tambahkan titik jika "Tambah Titik" telah ditekan
                if (canAddMarker) {
                    polygonPoints.add(latLng)
                    onMarkerAdded() // Panggil callback jika perlu
                }
            }
        ) {
            // Menggambar poligon jika ada minimal 4 titik
            if (polygonPoints.size >= 4) {
                Polygon(
                    points = polygonPoints,
                    clickable = true,
                    fillColor = Color(0x5500FF00),
                    strokeColor = Color.Blue,
                    strokeWidth = 5f,
                )
            }

            // Tambahkan penanda untuk setiap titik
            polygonPoints.forEachIndexed { index, point ->
                val markerIcon = createMarkerIconForTambahScreen(index + 1)

                Marker(
                    state = MarkerState(position = point),
                    icon = markerIcon,
                    title = "Marker ${index + 1}",
                )
            }
        }
    }
}

// Function untuk membuat ikon penanda dengan angka
fun createMarkerIconForTambahScreen(number: Int): BitmapDescriptor {
    // Membuat Bitmap untuk ikon khusus
    val markerSize = 100
    val bitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint()

    // Menggambar lingkaran merah
    paint.color = android.graphics.Color.RED
    canvas.drawCircle(markerSize / 2f, markerSize / 2f, markerSize / 2f, paint)

    // Menggambar angka di tengah lingkaran
    paint.color = android.graphics.Color.WHITE
    paint.textSize = 50f
    paint.textAlign = Paint.Align.CENTER
    val textY = (canvas.height / 2f - (paint.descent() + paint.ascent()) / 2)
    canvas.drawText(number.toString(), (canvas.width / 2f), textY, paint)

    // Mengembalikan BitmapDescriptor
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}





