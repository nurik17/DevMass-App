package com.example.drevmassapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.drevmassapp.presentation.catalog.CatalogScreen
import com.example.drevmassapp.presentation.catalog.CatalogViewModel

@Composable
fun BottomBarNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.CourseScreen_route,
        modifier = modifier,
        enterTransition = {
            EnterTransition.None
        },
        exitTransition = {
            ExitTransition.None
        },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(route = MainDestinations.CourseScreen_route) {

        }

        composable(route = MainDestinations.CatalogScreen_route) {
            val viewModel = hiltViewModel<CatalogViewModel>()
            CatalogScreen(viewModel = viewModel)
        }

        composable(route = MainDestinations.BasketScreen_route) {

        }
        composable(route = MainDestinations.ProfileScreen_route) {
        }
    }
}