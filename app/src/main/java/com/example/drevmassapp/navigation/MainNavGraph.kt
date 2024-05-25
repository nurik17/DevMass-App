package com.example.drevmassapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drevmassapp.presentation.basket.makeOrder.MakeOrderScreen
import com.example.drevmassapp.presentation.basket.makeOrder.MakeOrderViewModel
import com.example.drevmassapp.presentation.catalog.detail.ProductDetailScreen
import com.example.drevmassapp.presentation.catalog.detail.ProductDetailViewModel
import com.example.drevmassapp.presentation.login.LoginScreen
import com.example.drevmassapp.presentation.login.LoginViewModel
import com.example.drevmassapp.presentation.onboarding.OnBoardingScreen
import com.example.drevmassapp.presentation.registration.SignUpScreen
import com.example.drevmassapp.presentation.registration.SingUpViewModel
import com.example.drevmassapp.presentation.splash.SplashScreen

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    bottomNavController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = MainDestinations.MainScreen_route,
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
        composable(route = MainDestinations.MainScreen_route) {
            MainScreen(
                navigateToProductDetails = { id ->
                    navController.navigate("${MainDestinations.ProductDetailScreen_route}/$id")
                },
                bottomNavController = bottomNavController,
                navigateToMakeOrder = {
                    navController.navigate(MainDestinations.MakeOrderScreen_route)
                }
            )
        }

        composable(route = MainDestinations.SPLASH_ROUTE) {
            SplashScreen(navigateToOnBoardingScreen = { navController.navigate(MainDestinations.OnBoardingScreen_route) })
        }

        composable(route = MainDestinations.OnBoardingScreen_route) {
            OnBoardingScreen(
                navigateToRegistration = { navController.navigate(MainDestinations.RegistrationScreen_route) },
                navigateToLogin = { navController.navigate(MainDestinations.LoginScreen_route) }
            )
        }
        composable(route = MainDestinations.RegistrationScreen_route) {
            val viewModel: SingUpViewModel = hiltViewModel()
            SignUpScreen(
                viewModel = viewModel,
                navigateToLogin = { navController.navigate(MainDestinations.LoginScreen_route) },
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(route = MainDestinations.LoginScreen_route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                viewModel = viewModel,
                navigateToRegistration = { navController.navigate(MainDestinations.RegistrationScreen_route) },
                navigateBack = { navController.popBackStack() },
                navigateToMain = { navController.navigate(MainDestinations.MainScreen_route) }
            )
        }

        composable(route = "${MainDestinations.ProductDetailScreen_route}/{id}") { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            val viewModel: ProductDetailViewModel = hiltViewModel()
            ProductDetailScreen(
                id = id,
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToBasket = {
                    navController.navigate(MainDestinations.MainScreen_route)
                    bottomNavController.navigate(MainDestinations.BasketScreen_route)
                }
            )
        }

        composable(route = MainDestinations.MakeOrderScreen_route) {
            val viewModel = hiltViewModel<MakeOrderViewModel>()
            MakeOrderScreen(
                viewModel = viewModel,
                navigateBack = { navController.popBackStack() },
                navigateToMainScreen = { navController.navigate(MainDestinations.MainScreen_route) }
            )
        }
    }
}

private object MainScreens {
    const val SPLASH = "Splash"
    const val OnBoardingScreen = "OnBoardingScreen"

    const val RegistrationScreen = "RegistrationScreen"
    const val LoginScreen = "LoginScreen"

    const val CourseScreen = "CourseScreen"
    const val CatalogScreen = "CatalogScreen"
    const val BasketScreen = "BasketScreen"
    const val ProfileScreen = "ProfileScreen"

    const val ProductDetailScreen = "ProductDetailScreen"
    const val MakeOrderScreen = "MakeOrderScreen"

    const val MainScreen = "MainScreen"

}

object MainDestinations {
    const val SPLASH_ROUTE = MainScreens.SPLASH
    const val OnBoardingScreen_route = MainScreens.OnBoardingScreen

    const val RegistrationScreen_route = MainScreens.RegistrationScreen
    const val LoginScreen_route = MainScreens.LoginScreen

    const val CourseScreen_route = MainScreens.CourseScreen
    const val CatalogScreen_route = MainScreens.CatalogScreen
    const val BasketScreen_route = MainScreens.BasketScreen
    const val ProfileScreen_route = MainScreens.ProfileScreen

    const val ProductDetailScreen_route = MainScreens.ProductDetailScreen
    const val MakeOrderScreen_route = MainScreens.MakeOrderScreen


    const val MainScreen_route = MainScreens.MainScreen

}