package com.daniel.base

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import org.koin.core.module.Module

abstract class Product {
    open fun onInitApplication(applicationContext: Context) {}

    open fun onInitNavigation(navController: NavController, navGraphBuilder: NavGraphBuilder) {}

    open fun getModules(): List<Module> = emptyList()
}