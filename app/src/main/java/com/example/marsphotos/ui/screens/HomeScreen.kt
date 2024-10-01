
package com.example.marsphotos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.marsphotos.R
import com.example.marsphotos.ui.theme.MarsPhotosTheme

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
        is MarsUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
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
                LazyColumn {
                    items(photosState.photos) { photo ->
                        Image(
                            painter = rememberImagePainter(data = photo.imgSrc),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(8.dp)
                        )
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

