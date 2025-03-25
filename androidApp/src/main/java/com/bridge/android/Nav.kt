package com.bridge.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import top.brightk.bridge.Greeting
import top.brightk.bridge.annotation.NavGraphInject


@Composable
fun MainScreen(navHostController: NavHostController){
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GreetingView(Greeting().greet(), navHostController = navHostController)
        }
    }
}

@Composable
fun MyNavHost(){
    val navController =  rememberNavController()
    NavHost(
        navController,
        startDestination = "main",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("main") {
            MainScreen(navController)
        }
        navInject(navController)
    }
}

@NavGraphInject
fun NavGraphBuilder.navInject(controller: NavHostController){

}