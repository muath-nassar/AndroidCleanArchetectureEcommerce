package com.muath.composecleanarch.core.ui.root


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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.muath.composecleanarch.R
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

@OptIn(ExperimentalMaterial3Api::class)
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
        Tab(Routes.PRODUCT_LIST, stringResource(R.string.bottom_nav_home), Icons.Filled.Home, Icons.Outlined.Home),
        Tab(Routes.FAVORITE, stringResource(R.string.bottom_nav_favorites), Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder),
        Tab(Routes.CART, stringResource(R.string.bottom_nav_cart), Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
        Tab(Routes.SETTINGS, stringResource(R.string.bottom_nav_settings), Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    Scaffold(
        topBar = {
            if (chrome.showTopBar)
                CenterAlignedTopAppBar(
                    title = { Text(chrome.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                        ) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = { chrome.navIcon?.invoke() },
                    actions = { if (chrome.actions != null) chrome.actions?.invoke() }
                )

        },
        bottomBar = {
            if (chrome.showBottomBar) MyBottomNav(
                tabs, navController,
            )
        },
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
fun MyBottomNav(
    tabs: List<Tab>, navController: NavHostController
) {
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
                label = { Text(tab.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                )
            )
        }
    }
}
