package com.pemmob.gempatracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.pemmob.gempatracker.data.network.RetrofitClient
import com.pemmob.gempatracker.data.repository.GempaRepository
import com.pemmob.gempatracker.ui.navigation.AppNavGraph
import com.pemmob.gempatracker.ui.theme.GempaTrackerTheme
import com.pemmob.gempatracker.ui.viewmodel.GempaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi Repository dan ViewModel
        val repository = GempaRepository(RetrofitClient.apiService)
        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return GempaViewModel(repository) as T
            }
        })[GempaViewModel::class.java]

        setContent {
            GempaTrackerTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}