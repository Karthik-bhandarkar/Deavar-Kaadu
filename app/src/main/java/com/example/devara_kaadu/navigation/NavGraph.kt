package com.example.devara_kaadu.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.devara_kaadu.ui.screens.*

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.GroveDirectory.route) {
            GroveDirectoryScreen(
                onGroveClick = { groveId ->
                    navController.navigate(Screen.GroveDetail.createRoute(groveId))
                },
                onBack = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.GroveDetail.route,
            arguments = listOf(navArgument("groveId") { type = NavType.IntType })
        ) { backStackEntry ->
            val groveId = backStackEntry.arguments?.getInt("groveId") ?: return@composable
            GroveDetailScreen(
                groveId = groveId,
                onBack = { navController.navigateUp() },
                onExploreSpecies = { navController.navigate(Screen.SpeciesScan.route) },
                onReportIssue = { navController.navigate(Screen.ConservationAlert.route) }
            )
        }

        composable(Screen.SpeciesScan.route) {
            SpeciesScanScreen(onBack = { navController.navigateUp() })
        }

        composable(Screen.MythLegend.route) {
            MythLegendScreen(onBack = { navController.navigateUp() })
        }

        composable(Screen.NearbyGrove.route) {
            NearbyGroveScreen(
                onBack = { navController.navigateUp() },
                onGroveClick = { groveId ->
                    navController.navigate(Screen.GroveDetail.createRoute(groveId))
                }
            )
        }

        composable(Screen.ConservationAlert.route) {
            ConservationAlertScreen(onBack = { navController.navigateUp() })
        }

        composable(Screen.GuardianMode.route) {
            GuardianModeScreen(onBack = { navController.navigateUp() })
        }

        composable(Screen.EcoEducation.route) {
            EcoEducationScreen(onBack = { navController.navigateUp() })
        }

        composable(Screen.Settings.route) {
            SettingsScreen(onBack = { navController.navigateUp() })
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onBack = { navController.navigateUp() },
                onGroveClick = { groveId ->
                    navController.navigate(Screen.GroveDetail.createRoute(groveId))
                }
            )
        }
    }
}
