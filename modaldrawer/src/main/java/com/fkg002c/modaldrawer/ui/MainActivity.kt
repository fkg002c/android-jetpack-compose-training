package com.fkg002c.modaldrawer.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
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
import androidx.navigation.compose.rememberNavController
import com.fkg002c.modaldrawer.ui.navigation.AppNavHost
import com.fkg002c.modaldrawer.ui.navigation.menuItems
import com.fkg002c.modaldrawer.ui.theme.ComposeAppTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeAppTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
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
                                Text("Echo", modifier = Modifier.padding(16.dp))
                                HorizontalDivider()
                                Spacer(modifier = Modifier.height(16.dp))
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
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(text = "Echo")
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.open()
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
                        ) { innerPapping ->
                            AppNavHost(navHostController = navController, innerPapping)
                        }
                    }
                }
            }
        }
    }
}