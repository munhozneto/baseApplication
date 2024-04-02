package com.pmn.baseapplication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pmn.baseapplication.ui.navigation.Navigation.Args.ENTITY_ID

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.Routes.LIST
    ) {
        composable(
            route = Navigation.Routes.LIST
        ) {
            SomeEntityListScreenDestination(navController)
        }

        composable(
            route = Navigation.Routes.DETAILS,
            arguments = listOf(navArgument(name = ENTITY_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val entityId =
                requireNotNull(backStackEntry.arguments?.getString(ENTITY_ID)) { "Entity id is required as an argument" }
            SomeEntityScreenDestination(
                entityId = entityId.toLong(),
                navController = navController
            )
        }
    }
}

object Navigation {

    object Args {
        const val ENTITY_ID = "entity_id"
    }

    object Routes {
        const val LIST = "someEntityList"
        const val DETAILS = "$LIST/{$ENTITY_ID}"
    }

}

fun NavController.navigateToDetails(entityId: Long) {
    navigate(route = "${Navigation.Routes.LIST}/$entityId")
}