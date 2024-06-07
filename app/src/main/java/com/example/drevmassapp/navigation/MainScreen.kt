package com.example.drevmassapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    navigateToProductDetails: (Int) -> Unit,
    navigateToMakeOrder: () -> Unit,
    navigateToPoints: () -> Unit,
    onPromocodeNavigate: () -> Unit,
    onUserDataNavigate: () -> Unit,
    onNotificationNavigate: () -> Unit,
    onInformationScreenNavigate: () -> Unit,
    navigateToLogin: () -> Unit,
    onCourseDetailsNavigate: (Int) -> Unit
) {
    Scaffold(
        bottomBar = {
            Column {
                MainNavBar(navController)
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        BottomBarNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            navigateToProductDetails = navigateToProductDetails,
            navigateToMakeOrder = navigateToMakeOrder,
            navigateToPoints = navigateToPoints,
            onPromocodeNavigate = onPromocodeNavigate,
            onUserDataNavigate = onUserDataNavigate,
            onNotificationNavigate = onNotificationNavigate,
            onInformationScreenNavigate = onInformationScreenNavigate,
            navigateToLogin = navigateToLogin,
            onCourseDetailsNavigate = onCourseDetailsNavigate,
        )
    }
}
