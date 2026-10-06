package com.pemmob.gempatracker.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen/{gempaIndex}") {
        fun createRoute(index: Int) = "detail_screen/$index"
    }
}