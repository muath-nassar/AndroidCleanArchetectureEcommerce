package com.muath.composecleanarch.core.ui.root

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muath.composecleanarch.core.ui.navigation.AppNavHost
import com.muath.composecleanarch.core.ui.navigation.Routes
import kotlinx.coroutines.launch

// bottom tabs (top-level destinations)
data class Tab(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun RootScaffold(
) {
    var chrome by remember { mutableStateOf(ChromeConfig()) }
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    // simple helper so screens can call it without suspend
    val showSnackBar: (String, String?) -> Unit = { message, action ->
        scope.launch { snackBarHostState.showSnackbar(message, actionLabel = action) }
    }
    val tabs = listOf(
        Tab(Routes.PRODUCT_LIST, "Home", Icons.Filled.Home, Icons.Outlined.Home),
        Tab(Routes.FAVORITE, "Favorite", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
        Tab(Routes.CART, "Cart", Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
        Tab(Routes.SETTINGS, "Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    Scaffold(
        topBar = {
            if (chrome.showTopBar)
                SimpleTopBar(title = chrome.title?: "")
        },
        bottomBar = { if (chrome.showBottomBar) MyBottomNav(tabs, navController) },
        floatingActionButton = { chrome.fab?.invoke() },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        AppNavHost(
            modifier = Modifier.padding(innerPadding),
            applyChrome = { chrome = it },
            showSnackBar = showSnackBar,
            navController = navController
        )
    }
}


@Composable
fun SimpleTopBar(
    title: String,
    navIcon: (@Composable (() -> Unit))? = null,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    Surface(tonalElevation = 3.dp, color = MaterialTheme.colorScheme.primary) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            navIcon?.invoke()
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (navIcon != null) 8.dp else 0.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            actions?.let {
                Row(content = actions)
            }

        }
    }
}


@Composable
fun MyBottomNav(tabs: List<Tab>, navController: NavHostController) {
    // helper: is a dest inside a given top-level route hierarchy?
    fun NavDestination?.inHierarchy(route: String): Boolean =
        this?.hierarchy?.any { it.route == route } == true
    // observe current destination
    val backStackEntry by navController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination
    NavigationBar {
        tabs.forEach { tab ->
            val selected = destination.inHierarchy(tab.route)
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = tab.label
                    )
                },
                label = { Text(tab.label) }
            )
        }
    }
}
