package com.minimal.ec135.util

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val badgeCount: Int? = null
)
