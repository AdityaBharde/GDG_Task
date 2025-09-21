package com.example.gdg_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.gdg_task.ui.theme.GDG_TaskTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore



class MainActivity : ComponentActivity() {

    private val viewModel = MainViewModel()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val firebaseInitialized = FirebaseApp.initializeApp(this) != null || FirebaseApp.getApps(this).isNotEmpty()

        installSplashScreen().apply{
            setKeepOnScreenCondition {
                !viewModel.isReady.value
            }
        }

        setContent {
            GDG_TaskTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize().padding(),
                    topBar = {
                        TopAppBar(
                            actions = {},
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                titleContentColor = MaterialTheme.colorScheme.onBackground,
                            ),
                            scrollBehavior = null,
                            title = {
                                Text("Google Developer Group",
                                    style = MaterialTheme.typography.titleMedium,
                                )
                            },
                            navigationIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.download),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.padding(14.dp)
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    val repo = remember(firebaseInitialized) {
                        if (firebaseInitialized) FirestoreContentRepository(FirebaseFirestore.getInstance()) else null
                    }
                    Column(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                    ) {
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            ContentScreen(
                                repository = repo,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

