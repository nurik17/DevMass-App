package com.example.drevmassapp.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    bottomNavController: NavHostController,
    navigateToProductDetails: (Int) -> Unit,
    navigateToMakeOrder: () -> Unit,
    navigateToPoints: () -> Unit,
) {
    Scaffold(
        bottomBar = {
            Column {
                MainNavBar(bottomNavController)
            }
        },
        contentWindowInsets = WindowInsets.navigationBars
    ) { paddingValues ->
        BottomBarNavGraph(
            modifier = Modifier.padding(paddingValues),
            navController = bottomNavController,
            navigateToProductDetails = navigateToProductDetails,
            navigateToMakeOrder = navigateToMakeOrder,
            navigateToPoints = navigateToPoints,
        )
    }
}
