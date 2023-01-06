package com.gausslab.timeoffrequester.ui

import android.media.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,

) {

}

data class BottomNavItem(
    val name: String,
    val icon: ImageVector,
//    val navScreen: Unit
)

val bottomNavItems = listOf(
    BottomNavItem(
        name = "홈",
        icon = Icons.Default.Home,
//        navScreen={}
    ),
    BottomNavItem(
        name = "신청내역",
        icon = Icons.Default.Email,
//        navScreen = {}
    ),
    BottomNavItem(
        name = "내정보",
        icon = Icons.Default.Person,
//        navScreen = {}
    ),
)