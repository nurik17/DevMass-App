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
import com.example.drevmassapp.presentation.login.LoginScreen
import com.example.drevmassapp.presentation.splash.SplashScreen
import com.example.drevmassapp.presentation.onboarding.OnBoardingScreen
import com.example.drevmassapp.presentation.registration.SignUpScreen
import com.example.drevmassapp.presentation.registration.SingUpViewModel

@Composable
fun MainNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = MainDestinations.SPLASH_ROUTE,
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
                navigateToLogin = { navController.navigate(MainDestinations.LoginScreen_route) },
                navigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
        composable(route = MainDestinations.LoginScreen_route) {
            LoginScreen(
                navigateToRegistration = { navController.navigate(MainDestinations.RegistrationScreen_route) },
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(route = MainDestinations.MainScreen_route) {
            MainScreen()
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


    const val MainScreen_route = MainScreens.MainScreen

}