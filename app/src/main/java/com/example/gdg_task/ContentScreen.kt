package com.example.gdg_task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.gdg_task.ui.theme.GDG_TaskTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun ContentScreen(repository: FirestoreContentRepository?, modifier: Modifier = Modifier) {
    if (repository == null) {
        EmptyState(modifier)
        return
    }
    val items by repository.listenToContent().collectAsState(initial = emptyList())

    if (items.isEmpty()) {
        Text(
            text = "No content yet. Add documents to 'contents' in Firestore.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            textAlign = TextAlign.Center
        )
        return
    }

    val pagerState = rememberPagerState(pageCount = { items.size })

    var fullScreenImageUrl by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(items.size, fullScreenImageUrl) {
        if (items.isEmpty()) return@LaunchedEffect
        while (true) {
            delay(3000)
            if (fullScreenImageUrl == null) {
                val nextPage = (pagerState.currentPage + 1) % items.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        pageSpacing = 16.dp,
    ) { page ->
        ContentCard(items[page], onImageClick = { url ->
            if (url.isNotBlank()) fullScreenImageUrl = url
        })
    }

    if (fullScreenImageUrl != null) {
        Dialog(
            onDismissRequest = { fullScreenImageUrl = null },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .clickable { fullScreenImageUrl = null }
            ) {
                AsyncImage(
                    model = fullScreenImageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun ContentCard(item: ContentItem, onImageClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (item.imageUrl.isNotBlank()) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.text,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { onImageClick(item.imageUrl) },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(text = item.text, style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )
    }
}




@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Firebase not configured.\nAdd google-services.json or initialize FirebaseOptions manually.\nThen create a 'contents' collection with fields: text (String), imageUrl (String).",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}
