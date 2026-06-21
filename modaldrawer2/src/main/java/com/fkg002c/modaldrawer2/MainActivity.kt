package com.fkg002c.modaldrawer2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fkg002c.modaldrawer2.ui.screen.LocalFilesStorageScreen
import com.fkg002c.modaldrawer2.ui.screen.WorkersInfoScreen
import com.fkg002c.modaldrawer2.ui.screen.SettingScreen
import com.fkg002c.modaldrawer2.ui.theme.ComposeAppTheme
import kotlinx.coroutines.launch

enum class Screen(val title: String) {
    SETTINGS("Settings"),
    WORKERS_INFO("Workers info"),
    LOCAL_FILES_STORAGE("Local Files storage")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()

    // 2. Observe the current back stack entry to track screen changes
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // 3. Match the current route to your Screen enum (fallback to default if null)
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = Screen.entries.find { it.name == currentRoute } ?: Screen.SETTINGS

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Application: ${currentScreen.title}") }, // Dynamically updates title
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            if (drawerState.isOpen) drawerState.close() else drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        BackHandler(enabled = drawerState.isOpen) {
            scope.launch {
                drawerState.close()
            }
        }
        val context = LocalContext.current
        var backPressedTime by remember { mutableLongStateOf(0L) }
        BackHandler(enabled = !drawerState.isOpen) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                (context as? ComponentActivity)?.finish() // Exit activity
            } else {
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(70.dp))
                    menuItems.forEachIndexed { index, item ->
                        val selected = index == selectedItemIndex
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item.title,
                                    color = if (selected) Color.Red else Color.Unspecified
                                )
                            },
                            selected = false,
                            onClick = {
                                navController.navigate(item.route) { popUpTo(0) }
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon, contentDescription = item.title,
                                    tint = if (selected) Color.Red else Color.Unspecified
                                )
                            },
                        )
                    }
                }
            },
            drawerState = drawerState
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.SETTINGS.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Screen.SETTINGS.name) { SettingScreen() }
                composable(route = Screen.WORKERS_INFO.name) { WorkersInfoScreen() }
                composable(route = Screen.LOCAL_FILES_STORAGE.name) { LocalFilesStorageScreen() }
            }
        }
    }
}