package com.example.drevmassapp.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.drevmassapp.R
import com.example.drevmassapp.common.clickableWithoutRipple
import com.example.drevmassapp.ui.theme.BottomBarColor
import com.example.drevmassapp.ui.theme.Dark900
import com.example.drevmassapp.ui.theme.Gray600

@Composable
fun MainNavBar(
    navController: NavHostController
) {
    val screens = listOf(
        MainNavBarScreen.Course,
        MainNavBarScreen.Catalog,
        MainNavBarScreen.Basket,
        MainNavBarScreen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = BottomBarColor
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentRoute = currentRoute,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: MainNavBarScreen,
    currentRoute: String?,
    navController: NavHostController
) {
    val interactionSource = remember { MutableInteractionSource() }

    NavigationBarItem(
        icon = {
            Column(
                modifier = Modifier.clickableWithoutRipple(
                    interactionSource = interactionSource,
                    onClick = {
                        navController.navigate(screen.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = screen.icon),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = screen.title,
                    fontSize = 12.sp,
                    //fontFamily
                )
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Dark900,
            selectedTextColor = Dark900,
            unselectedTextColor = Gray600,
            unselectedIconColor = Gray600,
            indicatorColor = Color.Transparent
        ),
        selected = currentRoute == screen.route,

        onClick = {
            navController.navigate(screen.route) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

sealed class MainNavBarScreen(
    var route: String,
    var title: String,
    @DrawableRes val icon: Int,
) {
    data object Course : MainNavBarScreen(
        MainDestinations.CourseScreen_route,
        "Курсы",
        icon = R.drawable.ic_course
    )

    data object Catalog : MainNavBarScreen(
        MainDestinations.CatalogScreen_route,
        "Каталог",
        icon = R.drawable.ic_catalog
    )
    data object Basket : MainNavBarScreen(
        MainDestinations.BasketScreen_route,
        "Корзина",
        icon = R.drawable.ic_basket
    )

    data object Profile : MainNavBarScreen(
        MainDestinations.ProfileScreen_route,
        "Профиль",
        icon = R.drawable.ic_profile
    )
}
