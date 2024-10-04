import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapZoomQuestScreen(navController: NavController) {
    val url = "https://www.mapquestapi.com/staticmap/v5/map?key=1epHEkn5zzpFjSAHVbCpOBdXjh2VG2DV&center=Surabaya+Gresik,Sidoarjo&zoom=10&type=hyb&size=1000,800@2x"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MapZoomQuest") },
                navigationIcon = {
                    IconButton (onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            AndroidView(factory = {
                WebView(it).apply {
                    loadUrl(url)
                }
            }, update = {
                it.loadUrl(url)
            })
        }
    }
}