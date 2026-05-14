package com.example.devara_kaadu.navigation

/** Sealed class defining all navigation routes in the app. */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object GroveDirectory : Screen("grove_directory")
    object GroveDetail : Screen("grove_detail/{groveId}") {
        fun createRoute(groveId: Int) = "grove_detail/$groveId"
    }
    object SpeciesScan : Screen("species_scan")
    object MythLegend : Screen("myth_legend")
    object NearbyGrove : Screen("nearby_grove")
    object ConservationAlert : Screen("conservation_alert")
    object GuardianMode : Screen("guardian_mode")
    object EcoEducation : Screen("eco_education")
    object Settings : Screen("settings")
    object Search : Screen("search")
}
