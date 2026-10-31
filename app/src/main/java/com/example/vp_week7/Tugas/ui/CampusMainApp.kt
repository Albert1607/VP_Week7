package com.example.vp_week7.Tugas.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.vp_week7.Tugas.navigation.ClubProjectsRoute
import com.example.vp_week7.Tugas.navigation.CommunitiesRoute
import com.example.vp_week7.Tugas.navigation.DashboardRoute
import com.example.vp_week7.Tugas.navigation.ProjectDetailRoute
import com.example.vp_week7.Tugas.ui.screens.ClubProjectsScreen
import com.example.vp_week7.Tugas.ui.screens.CommunitiesScreen
import com.example.vp_week7.Tugas.ui.screens.DashboardScreen
import com.example.vp_week7.Tugas.ui.screens.ProjectDetailScreen
import com.example.vp_week7.Tugas.viewmodel.CampusViewModel

sealed class BottomNavItem<T : Any>(
    val route: T,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem<DashboardRoute>(DashboardRoute, "Dashboard", Icons.Default.Dashboard)
    object Communities : BottomNavItem<CommunitiesRoute>(CommunitiesRoute, "Communities", Icons.Default.Groups)
}

@Composable
fun CampusMainApp(
    viewModel: CampusViewModel = viewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Bottom Bar is visible ONLY on top-level routes (Dashboard & Communities)
    val showBottomBar = currentDestination?.hierarchy?.any {
        it.hasRoute<DashboardRoute>() || it.hasRoute<CommunitiesRoute>()
    } == true

    val bottomNavItems = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Communities
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.hasRoute(item.route::class)
                        } == true

                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF2563EB),
                                selectedTextColor = Color(0xFF2563EB),
                                unselectedIconColor = Color(0xFF64748B),
                                unselectedTextColor = Color(0xFF64748B),
                                indicatorColor = Color(0xFFEFF6FF)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<DashboardRoute> {
                DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToCommunityProjects = { communityId ->
                        navController.navigate(ClubProjectsRoute(communityId))
                    },
                    onNavigateToProjectDetail = { projectId ->
                        navController.navigate(ProjectDetailRoute(projectId))
                    }
                )
            }

            composable<CommunitiesRoute> {
                CommunitiesScreen(
                    viewModel = viewModel,
                    onNavigateToCommunityProjects = { communityId ->
                        navController.navigate(ClubProjectsRoute(communityId))
                    }
                )
            }

            composable<ClubProjectsRoute> { backStackEntry ->
                val route: ClubProjectsRoute = backStackEntry.toRoute()
                ClubProjectsScreen(
                    communityId = route.communityId,
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onNavigateToProjectDetail = { projectId ->
                        navController.navigate(ProjectDetailRoute(projectId))
                    }
                )
            }

            composable<ProjectDetailRoute> { backStackEntry ->
                val route: ProjectDetailRoute = backStackEntry.toRoute()
                ProjectDetailScreen(
                    projectId = route.projectId,
                    viewModel = viewModel,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
