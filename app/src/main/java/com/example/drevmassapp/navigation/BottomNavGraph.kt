package com.example.drevmassapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.drevmassapp.presentation.basket.BasketScreen
import com.example.drevmassapp.presentation.basket.BasketViewModel
import com.example.drevmassapp.presentation.catalog.CatalogScreen
import com.example.drevmassapp.presentation.catalog.CatalogViewModel
import com.example.drevmassapp.presentation.course.CourseScreen
import com.example.drevmassapp.presentation.profileScreen.ProfileScreen
import com.example.drevmassapp.presentation.profileScreen.ProfileViewModel

@Composable
fun BottomBarNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigateToProductDetails: (Int) -> Unit,
    navigateToMakeOrder:() -> Unit,
    navigateToPoints:() -> Unit,
    onPromocodeNavigate:() -> Unit,
    onUserDataNavigate:() -> Unit,
    onNotificationNavigate:() -> Unit,
    onInformationScreenNavigate:() -> Unit,
    navigateToLogin:() -> Unit,
    onCourseDetailsNavigate: (Int) -> Unit
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
            CourseScreen(onCourseDetailsNavigate = onCourseDetailsNavigate)
        }

        composable(route = MainDestinations.CatalogScreen_route) {
            val viewModel = hiltViewModel<CatalogViewModel>()
            CatalogScreen(
                viewModel = viewModel,
                navigateToProductDetails = navigateToProductDetails
            )
        }

        composable(route = MainDestinations.BasketScreen_route) {
            val viewModel = hiltViewModel<BasketViewModel>()
            BasketScreen(viewModel = viewModel, navigateToMakeOrder = navigateToMakeOrder)
        }
        composable(route = MainDestinations.ProfileScreen_route) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            ProfileScreen(
                viewModel = viewModel,
                navigateToPoints = navigateToPoints,
                onPromocodeNavigate = onPromocodeNavigate,
                onUserDataNavigate = onUserDataNavigate,
                onNotificationNavigate = onNotificationNavigate,
                onInformationScreenNavigate = onInformationScreenNavigate,
                navigateToLogin = navigateToLogin
            )
        }
    }
}