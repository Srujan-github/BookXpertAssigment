package labs.creative.bookxpertassigment.ui.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


enum class Screen(val route: String, val title: String, val icon: ImageVector) {
    UserRecords("home", "UserRecords", Icons.Default.Person),
    ImagePicker("profile", "ImagePicker", Icons.Default.DateRange),
    PDFViewer("settings", "PDFViewer", Icons.Default.Email)
}

@Composable
fun ButtomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var currentScreen  = navBackStackEntry?.destination?.route ?: Screen.UserRecords.route
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Screen.entries.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentScreen == screen.route,
                onClick = {
                    currentScreen = screen.route
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

            )
        }
    }
}

