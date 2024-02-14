package com.minimal.ec135

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.minimal.ec135.screenLicence.LicencePlatesScreen
import com.minimal.ec135.screenScanner.CameraPermissionsGrantedContent
import com.minimal.ec135.screenScanner.ScannerScreen
import com.minimal.ec135.screenSettings.SettingsScreen
import com.minimal.ec135.ui.theme.EC135Theme
import com.minimal.ec135.util.BottomNavigationItem

object Screens {
    const val SCANNER = "scannerScreen"
    const val LICENCE_PLATES = "licencePlatesScreen"
    const val SETTINGS = "settingsScreen"
    const val CAMERA = "cameraScreen"
}

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Scanner",
        route = Screens.SCANNER,
        selectedIcon = R.drawable.ic_photo_camera_filled,
        unselectedIcon = R.drawable.ic_photo_camera_outline
    ),
    BottomNavigationItem(
        title = "Licence Plates",
        route = Screens.LICENCE_PLATES,
        selectedIcon = R.drawable.ic_bookmark_filled,
        unselectedIcon = R.drawable.ic_bookmark_outline
    ),
    BottomNavigationItem(
        title = "Settings",
        route = Screens.SETTINGS,
        selectedIcon = R.drawable.ic_settings_filled,
        unselectedIcon = R.drawable.ic_settings_outline
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EC135Theme {
                MyApp(stringResource(R.string.app_name))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MyApp(name: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var selectedScreenIndex by rememberSaveable { mutableIntStateOf(0) }
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(name) },
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_info_outline),
                                contentDescription = "Info"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    bottomNavigationItems.forEachIndexed { index, screen ->
                        NavigationBarItem(
                            selected = selectedScreenIndex == index,
                            onClick = {
                                selectedScreenIndex = index
                                navController.navigate(screen.route)
                            },
                            label = { Text(text = screen.title) },
                            alwaysShowLabel = false,
                            icon = {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        if (selectedScreenIndex == index) {
                                            screen.selectedIcon
                                        } else screen.unselectedIcon
                                    ),
                                    contentDescription = screen.title
                                )
                            }
                        )
                    }
                }
            }
        ) { contentPadding ->
            AppNavigation(
                context = LocalContext.current,
                navController = navController,
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
fun AppNavigation(context: Context, navController: NavHostController, modifier: Modifier) {
    Box(
        modifier = modifier,
    ) {
        NavHost(navController = navController, startDestination = Screens.SCANNER) {
            composable(Screens.SCANNER) { ScannerScreen(navController) }
            composable(Screens.LICENCE_PLATES) { LicencePlatesScreen() }
            composable(Screens.SETTINGS) { SettingsScreen() }
            composable(Screens.CAMERA) { CameraPermissionsGrantedContent(navController) }
        }
    }
}