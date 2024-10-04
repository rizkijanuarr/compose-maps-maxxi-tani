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
import com.kiki.hellocompose.data.api.Network

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationMapQuestScreen(navController: NavController) {
    val url = "https://www.mapquestapi.com/staticmap/v5/map?locations=Surabaya,Sidoarjo||Surabaya,Sidoarjo||Surabaya,Sidoarjo&size=600,400@2x&key=1epHEkn5zzpFjSAHVbCpOBdXjh2VG2DV"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LocationMapQuest") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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