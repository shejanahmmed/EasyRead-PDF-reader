package com.shejan.easyread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shejan.easyread.ui.components.FloatingNavBar
import com.shejan.easyread.ui.create.CreateScreen
import com.shejan.easyread.ui.home.AllFilesScreen
import com.shejan.easyread.ui.home.HomeScreen
import com.shejan.easyread.ui.library.LibraryScreen
import com.shejan.easyread.ui.search.SearchScreen
import com.shejan.easyread.ui.settings.SettingsScreen
import com.shejan.easyread.ui.theme.EasyReadTheme
import com.shejan.easyread.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Fully transparent system bars
        enableEdgeToEdge()

        setContent {
            val themePreference by themeViewModel.theme.collectAsState()
            EasyReadTheme(themePreference = themePreference) {
                EasyReadApp(themeViewModel = themeViewModel)
            }
        }
    }
}

@Composable
private fun EasyReadApp(themeViewModel: ThemeViewModel) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "home"

    // Routes that should hide the floating nav bar (e.g. PDF reader)
    val hideNavBarRoutes = setOf("reader/{fileUri}")
    val showNavBar = hideNavBarRoutes.none { currentRoute.startsWith(it.substringBefore("{")) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0),
        bottomBar = {
            AnimatedVisibility(
                visible = showNavBar,
                enter = slideInVertically { it } + fadeIn(),
                exit  = slideOutVertically { it } + fadeOut()
            ) {
                FloatingNavBar(
                    currentRoute = currentRoute,
                    onNavItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { _ ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        onPdfClick = { pdf ->
                            val encoded = java.net.URLEncoder.encode(pdf.uri, "UTF-8")
                            navController.navigate("reader/$encoded")
                        },
                        onNavigate = { route ->
                            navController.navigate(route)
                        }
                    )
                }
                composable("all_files") {
                    AllFilesScreen()
                }
                composable("library") {
                    LibraryScreen(onPdfClick = { pdf ->
                        val encoded = java.net.URLEncoder.encode(pdf.uri, "UTF-8")
                        navController.navigate("reader/$encoded")
                    })
                }
                composable("search") {
                    SearchScreen(
                        onPdfClick = { pdf ->
                            val encoded = java.net.URLEncoder.encode(pdf.uri, "UTF-8")
                            navController.navigate("reader/$encoded")
                        }
                    )
                }
                composable("create") {
                    CreateScreen()
                }
                composable("settings") {
                    SettingsScreen(themeViewModel = themeViewModel)
                }
                // PDF reader — added later
                composable("reader/{fileUri}") { _ ->
                    // Placeholder — PdfReaderScreen will go here
                    androidx.compose.material3.Text("Reader — coming soon")
                }
            }
        }
    }
}