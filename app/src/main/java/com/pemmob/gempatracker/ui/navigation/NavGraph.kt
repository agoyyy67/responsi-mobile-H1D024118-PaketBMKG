package com.pemmob.gempatracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pemmob.gempatracker.ui.common.UiState
import com.pemmob.gempatracker.ui.screens.detail.DetailScreen
import com.pemmob.gempatracker.ui.screens.home.HomeScreen
import com.pemmob.gempatracker.ui.viewmodel.GempaViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    viewModel: GempaViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { index ->
                    navController.navigate(Screen.Detail.createRoute(index))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("gempaIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("gempaIndex") ?: -1
            val selectedGempa = (uiState as? UiState.Success)?.data?.getOrNull(index)

            DetailScreen(
                gempa = selectedGempa,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}