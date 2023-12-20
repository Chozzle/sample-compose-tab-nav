package io.github.sampletabnav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val items = listOf(TopLevelScreen.Profile, TopLevelScreen.FriendsList)
                items.forEach { screen ->
                    NavigationBarItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(text = stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.navGraphRoute } == true,
                        onClick = {
                            navController.navigate(screen.startDestinationRoute) {
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
        }
    ) { innerPadding ->
        val forwardBackMaterial3 = forwardBackMaterial3()
        val topLevelMaterial3 = topLevelMaterial3()
        val topLevelRoutes = listOf(TopLevelScreen.Profile.navGraphRoute, TopLevelScreen.FriendsList.navGraphRoute)
        NavHost(
            navController,
            startDestination = TopLevelScreen.Profile.navGraphRoute,
            Modifier.padding(innerPadding),
            enterTransition = {
                if (isBetweenTopLevelRoutes(topLevelRoutes)) {
                    topLevelMaterial3.enterTransition(this)
                } else {
                    forwardBackMaterial3.enterTransition(this)
                }
            },
            exitTransition = {
                if (isBetweenTopLevelRoutes(topLevelRoutes)) {
                    topLevelMaterial3.exitTransition(this)
                } else {
                    forwardBackMaterial3.exitTransition(this)
                }
            },
            popEnterTransition = {
                if (isBetweenTopLevelRoutes(topLevelRoutes)) {
                    topLevelMaterial3.popEnterTransition(this)
                } else {
                    forwardBackMaterial3.popEnterTransition(this)
                }
            },
            popExitTransition = {
                if (isBetweenTopLevelRoutes(topLevelRoutes)) {
                    topLevelMaterial3.popExitTransition(this)
                } else {
                    forwardBackMaterial3.popExitTransition(this)
                }
            },
        ) {
            navigation(
                startDestination = TopLevelScreen.Profile.startDestinationRoute,
                route = TopLevelScreen.Profile.navGraphRoute
            ) {
                composable(TopLevelScreen.Profile.startDestinationRoute) {
                    Profile(
                        onFriendsClick = {

                            // The problem lies here.
                            // EXPECTED BEHAVIOUR:
                            // Clicking the favourite friend button, it stays in the Profile tab
                            // and shows a forward transition to go into the detail about the friend.
                            // ACTUAL BEHAVIOUR:
                            // Clicking the favourite friend button, it switches tabs to the Friends list
                            // and shows a top level transition animation
                            navController.navigate("${TopLevelScreen.Profile.navGraphRoute}/friendDetail/$it")
                        }
                    )
                }
                composable("${TopLevelScreen.Profile.navGraphRoute}/friendDetail/{name}",
                    arguments = listOf(navArgument("name") { type = NavType.StringType })) {
                    FriendDetail(name = it.arguments!!.getString("name")!!)
                }
            }
            navigation(
                startDestination = TopLevelScreen.FriendsList.startDestinationRoute,
                route = TopLevelScreen.FriendsList.navGraphRoute
            ) {
                composable(TopLevelScreen.FriendsList.startDestinationRoute) {
                    FriendsList(
                        onClickFriendDetail = {
                            navController.navigate("${TopLevelScreen.FriendsList.navGraphRoute}/friendDetail/$it")
                        }
                    )
                }
                composable("${TopLevelScreen.FriendsList.navGraphRoute}/friendDetail/{name}",
                    arguments = listOf(navArgument("name") { type = NavType.StringType })) {
                    FriendDetail(name = it.arguments!!.getString("name")!!)
                }
            }
        }
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.isBetweenTopLevelRoutes(topLevelRoutes: List<String>): Boolean {
    val initialTopLevelAncestor = initialState.destination.hierarchy.find { it.route in topLevelRoutes }
    val targetTopLevelAncestor = targetState.destination.hierarchy.find { it.route in topLevelRoutes }
    return targetTopLevelAncestor != null && initialTopLevelAncestor != targetTopLevelAncestor
}