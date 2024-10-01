package com.example.marsphotos.ui.screens

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.example.marsphotos.R
import com.example.marsphotos.network.MarsPhoto

/**
 * Depending on the state, display the appropriate screen
 */
@Composable
fun HomeScreen(
    marsUiState: MarsUiState,
    modifier: Modifier = Modifier
) {
    when (marsUiState) {
        is MarsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is MarsUiState.Success -> ResultScreen(
            marsUiState, modifier = modifier.fillMaxWidth()
        )

        is MarsUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

/**
 *  Display a loading image
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 *  Display an error message and icon when there's an error
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying a LazyColumn with the photos retrieved.
 */
@Composable
fun ResultScreen(photosState: MarsUiState, modifier: Modifier = Modifier) {
    // Display different content based on the state
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        when (photosState) {
            is MarsUiState.Success -> {
                // Display a LazyColumn with images when data is successfully loaded
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3)
                ) {
                    items(photosState.photos) { photo ->
                        ImageWithDialog(photo)
                    }
                }
            }

            is MarsUiState.Error -> {
                // Display an error message if there's an error
                Text(text = "Error loading photos")
            }

            is MarsUiState.Loading -> {
                // Display a loading indicator while data is being loaded
                CircularProgressIndicator(modifier = Modifier.wrapContentSize(Alignment.Center))
            }
        }
    }
}

@Composable
fun ImageWithDialog(photo: MarsPhoto) {
    var openDialog by remember { mutableStateOf(false) }

    // Imagen inicial
    Image(
        painter = rememberImagePainter(data = photo.imgSrc),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
            .clickable {
                openDialog = true // Abre el diálogo cuando se haga clic en la imagen
            }
    )

    // Diálogo que muestra la imagen expandida
    if (openDialog) {
        Dialog(
            onDismissRequest = { openDialog = false }, // Cierra el diálogo al hacer clic fuera
            content =
            {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(color = Color.Transparent)
                        .clickable { openDialog = false  },
                    verticalArrangement = Arrangement.Center

                ) {
                    Image(
                        painter = rememberImagePainter(data = photo.imgSrc),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth() // Imagen expandida a pantalla completa
                    )
                }

            }
        )
    }
}
