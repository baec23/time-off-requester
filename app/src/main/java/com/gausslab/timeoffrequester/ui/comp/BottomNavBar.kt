package com.gausslab.timeoffrequester.ui.comp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.baec23.ludwig.component.toggleable.ToggleableIcon
import com.gausslab.timeoffrequester.ui.screen.main.mainScreenRoute
import com.gausslab.timeoffrequester.ui.screen.myprofile.myProfileScreenRoute
import com.gausslab.timeoffrequester.ui.screen.requestlist.requestListScreenRoute

//@Composable
//fun BottomNavBar(
//    modifier: Modifier = Modifier,
//    items: List<BottomNavigationItem>,
//    currNavScreenRoute: String?,
//    onBottomNavBarButtonPressed: (BottomNavigationItem) -> Unit,
//) {
//    NavigationBar(modifier = modifier) {
//        items.forEach { item ->
//            ToggleableIcon(
//                modifier = Modifier.weight(1f),
//                isToggled = item.route == currNavScreenRoute,
//                imageVector = item.iconImageVector,
//            ) {
//                onBottomNavBarButtonPressed(item)
//            }
//        }
//    }
//}
//
//data class BottomNavigationItem(
//    val iconImageVector: ImageVector,
//    val route: String,
//    val label: String? = null,
//)