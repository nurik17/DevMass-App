package com.example.drevmassapp.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.drevmassapp.presentation.basket.BasketViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
    onCourseDetailsNavigate: (Int) -> Unit,
    viewModel: BasketViewModel = hiltViewModel(),
) {
    val basketCount by viewModel.basketCount.collectAsStateWithLifecycle()
    Log.d("MainScreen", "basketCount : $basketCount")

    Scaffold(
        bottomBar = {
            Column {
                MainNavBar(navController, basketCount)
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
