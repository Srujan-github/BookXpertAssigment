package labs.creative.bookxpertassigment.ui.presentation.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import labs.creative.bookxpertassigment.ui.presentation.imagepicker.ImagePickerScreen
import labs.creative.bookxpertassigment.ui.presentation.navigation.ButtomNavBar
import labs.creative.bookxpertassigment.ui.presentation.navigation.Screen
import labs.creative.bookxpertassigment.ui.presentation.pdfviewer.PdfViewerScreen
import labs.creative.bookxpertassigment.ui.presentation.records.UserRecordsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = getScreenTitle(backStackEntry?.destination?.route),
                    )
                },
                colors =  TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            ButtomNavBar(navController)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.UserRecords.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.UserRecords.route) { UserRecordsScreen(Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),snackbarHostState) }
            composable(Screen.ImagePicker.route) { ImagePickerScreen() }
            composable(Screen.PDFViewer.route) { PdfViewerScreen() }
        }
    }
}

fun getScreenTitle(route: String?): String {
    return when (route) {
        Screen.UserRecords.route -> "User Records"
        Screen.ImagePicker.route -> "Image Picker"
        Screen.PDFViewer.route -> "PDF Viewer"
        else -> "App"
    }
}
