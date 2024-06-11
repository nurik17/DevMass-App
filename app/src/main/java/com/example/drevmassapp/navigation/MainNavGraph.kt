package com.example.drevmassapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drevmassapp.navigation.MainDestinations.MyPointsScreen_route
import com.example.drevmassapp.presentation.basket.makeOrder.MakeOrderScreen
import com.example.drevmassapp.presentation.basket.makeOrder.MakeOrderViewModel
import com.example.drevmassapp.presentation.catalog.detail.ProductDetailScreen
import com.example.drevmassapp.presentation.catalog.detail.ProductDetailViewModel
import com.example.drevmassapp.presentation.course.bookMark.BookMarkScreen
import com.example.drevmassapp.presentation.course.detail.CourseDetailScreen
import com.example.drevmassapp.presentation.course.lessonDetail.LessonDetailsScreen
import com.example.drevmassapp.presentation.course.lessonDetail.VideoPlayerScreen
import com.example.drevmassapp.presentation.login.LoginScreen
import com.example.drevmassapp.presentation.login.LoginViewModel
import com.example.drevmassapp.presentation.onboarding.OnBoardingScreen
import com.example.drevmassapp.presentation.profileScreen.information.InformationScreen
import com.example.drevmassapp.presentation.profileScreen.myPoints.MyPointsScreen
import com.example.drevmassapp.presentation.profileScreen.myPoints.MyPointsViewModel
import com.example.drevmassapp.presentation.profileScreen.notification.NotificationScreen
import com.example.drevmassapp.presentation.profileScreen.promocode.PromocodeScreen
import com.example.drevmassapp.presentation.profileScreen.promocode.PromocodeScreenViewModel
import com.example.drevmassapp.presentation.profileScreen.userData.UserDataScreen
import com.example.drevmassapp.presentation.registration.SignUpScreen
import com.example.drevmassapp.presentation.registration.SingUpViewModel
import com.example.drevmassapp.presentation.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
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

        composable(route = MainDestinations.MainScreen_route) {
            MainScreen(
                navigateToProductDetails = { id ->
                    navController.navigate("${MainDestinations.ProductDetailScreen_route}/$id")
                },
                navigateToMakeOrder = {
                    navController.navigate(MainDestinations.MakeOrderScreen_route)
                },
                navigateToPoints = {
                    navController.navigate(MyPointsScreen_route)
                },
                onPromocodeNavigate = {
                    navController.navigate(MainDestinations.PromocodeScreen_route)
                },
                onUserDataNavigate = {
                    navController.navigate(MainDestinations.UserDataScreen_route)
                },
                onNotificationNavigate = {
                    navController.navigate(MainDestinations.NotificationScreen_route)
                },
                onInformationScreenNavigate = {
                    navController.navigate(MainDestinations.InformationScreen_route)
                },
                navigateToLogin = {
                    navController.navigate(MainDestinations.LoginScreen_route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
                },
                onCourseDetailsNavigate = { id ->
                    navController.navigate("${MainDestinations.CourseDetailScreen_route}/$id")
                },
            )
        }

        composable(route = MainDestinations.UserDataScreen_route) {
            UserDataScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                navController = navController,
                navigateOnBoarding = {
                    navController.navigate(MainDestinations.OnBoardingScreen_route) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }
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
                    navController.navigate(MainDestinations.BasketScreen_route)
                }
            )
        }

        composable(route = "${MainDestinations.CourseDetailScreen_route}/{id}") { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getString("id")
            CourseDetailScreen(
                courseId = id,
                navigateBack = {
                    navController.popBackStack()
                },
                onLessonDetailNavigate = { lessonId, courseId ->
                    navController.navigate("${MainDestinations.LessonDetailsScreen_route}/$lessonId/$courseId")
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

        composable(route = MyPointsScreen_route) {
            val viewModel = hiltViewModel<MyPointsViewModel>()
            MyPointsScreen(
                viewModel = viewModel,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = MainDestinations.PromocodeScreen_route) {
            val viewModel = hiltViewModel<PromocodeScreenViewModel>()
            PromocodeScreen(
                viewModel = viewModel,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = MainDestinations.NotificationScreen_route) {
            NotificationScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = MainDestinations.InformationScreen_route) {
            InformationScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = "${MainDestinations.LessonDetailsScreen_route}/{lessonId}/{courseId}") { navBackStackEntry ->
            val lessonId = navBackStackEntry.arguments?.getString("lessonId")
            val courseId = navBackStackEntry.arguments?.getString("courseId")
            LessonDetailsScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                courseId = courseId,
                lessonId = lessonId,
                navigateToVideoPlayerScreen = { string ->
                    navController.navigate("${MainDestinations.VideoPlayerScreen_route}/$string")
                }
            )
        }

        composable(route = MainDestinations.BookMarkScreen_route) {
            BookMarkScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = "${MainDestinations.VideoPlayerScreen_route}/{string}") { navBackStackEntry ->
            val link = navBackStackEntry.arguments?.getString("string")
            link?.let { VideoPlayerScreen(link = it) }
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
    const val CourseDetailScreen = "CourseDetailScreen"
    const val LessonDetailsScreen = "LessonDetailsScreen"
    const val VideoPlayerScreen = "VideoPlayerScreen"
    const val BookMarkScreen = "BookMarkScreen"

    const val MainScreen = "MainScreen"

    const val MyPointsScreen = "MyPointsScreen"
    const val PromocodeScreen = "PromocodeScreen"
    const val UserDataScreen = "UserDataScreen"
    const val NotificationScreen = "NotificationScreen"
    const val InformationScreen = "InformationScreen"


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
    const val CourseDetailScreen_route = MainScreens.CourseDetailScreen
    const val LessonDetailsScreen_route = MainScreens.LessonDetailsScreen
    const val VideoPlayerScreen_route = MainScreens.VideoPlayerScreen
    const val BookMarkScreen_route = MainScreens.BookMarkScreen

    const val MainScreen_route = MainScreens.MainScreen

    const val MyPointsScreen_route = MainScreens.MyPointsScreen
    const val PromocodeScreen_route = MainScreens.PromocodeScreen
    const val UserDataScreen_route = MainScreens.UserDataScreen
    const val NotificationScreen_route = MainScreens.NotificationScreen
    const val InformationScreen_route = MainScreens.InformationScreen
}