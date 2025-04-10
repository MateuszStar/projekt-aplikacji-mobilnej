package com.example.music_listener

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import com.example.music_listener.ui.theme.Music_listenerTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //MusicPlayerScreen(context = this)
            MusicSwitcherScreen()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview
@Composable
fun MusicTest()
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        Text(
            text = "hello"
        )
        Text(
            text = "there"
        )
    }
}

@Composable
fun MusicPlayerScreen(context: Context) {
    val mediaPlayer = remember(context) {
        MediaPlayer.create(context, R.raw.larosa)
    }

    var isPlaying by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                isPlaying = true
            }
        }) {
            Text("Play")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                isPlaying = false
            }
        }) {
            Text("Pause")
        }
    }

    // Dispose of the media player when the composable is removed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}

@Composable
fun MusicSwitcherScreen() {
    val song1 = "La Rosa Encantada by Chris Haugen"
    val song2 = "Last Cappuccino in Rio by Chris Haugen"

    val context = LocalContext.current

    var currentSong by remember { mutableStateOf(R.raw.larosa) }
    var isPlaying by remember { mutableStateOf(false) }

    // Recreate MediaPlayer when song changes
    var mediaPlayer by remember(currentSong) {
        mutableStateOf(MediaPlayer.create(context, currentSong))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Now Playing: ${if (currentSong == R.raw.larosa) song1 else song2}")

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                // Switch to song 1
                if (currentSong != R.raw.larosa) {
                    mediaPlayer.release()
                    currentSong = R.raw.larosa
                    mediaPlayer = MediaPlayer.create(context, R.raw.larosa)
                }
            }) {
                Text("Switch to song 1")
            }

            Button(onClick = {
                // Switch to song 2
                if (currentSong != R.raw.lastcapp) {
                    mediaPlayer.release()
                    currentSong = R.raw.lastcapp
                    mediaPlayer = MediaPlayer.create(context, R.raw.lastcapp)
                }
            }) {
                Text("Switch to song 2")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                    isPlaying = true
                }
            }) {
                Text("Play")
            }

            Button(onClick = {
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.pause()
                    isPlaying = false
                }
            }) {
                Text("Pause")
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
}
