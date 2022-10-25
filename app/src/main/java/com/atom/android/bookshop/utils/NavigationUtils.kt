package com.atom.android.bookshop.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph


fun NavController.navigateSafe(direction: NavDirections) {
    val currentDestination = this.currentDestination
    if (currentDestination != null) {
        val navAction = currentDestination.getAction(direction.actionId)
        if (navAction != null) {
            val destinationId: Int = navAction.destinationId
            val currentNode: NavGraph? =
                if (currentDestination is NavGraph) currentDestination else currentDestination.parent
            if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                this.navigate(direction)
            }
        }
    }
}

fun NavController.navigateSafe(actionID: Int) {
    val currentDestination = this.currentDestination
    if (currentDestination != null) {
        val navAction = currentDestination.getAction(actionID)
        if (navAction != null) {
            val destinationId: Int = navAction.destinationId
            val currentNode: NavGraph? =
                if (currentDestination is NavGraph) currentDestination else currentDestination.parent
            if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                this.navigate(actionID)
            }
        }
    }
}
