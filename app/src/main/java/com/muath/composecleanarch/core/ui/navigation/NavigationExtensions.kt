package com.muath.composecleanarch.core.ui.navigation

import androidx.navigation.NavController


fun NavController.navToProductsListScreen(){
    this.navigate(Routes.PRODUCT_LIST)
}

fun NavController.navToProductDetailsScreen(id: Int){
    this.navigate(Routes.PRODUCT_DETAILS + "/$id")
}