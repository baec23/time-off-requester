package com.gausslab.timeoffrequester.ui.comp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.baec23.ludwig.component.toggleable.ToggleableIcon
import com.gausslab.timeoffrequester.ui.screen.login.loginScreenRoute
import com.gausslab.timeoffrequester.ui.screen.main.mainScreenRoute
import com.gausslab.timeoffrequester.ui.screen.myprofile.myProfileScreenRoute
import com.gausslab.timeoffrequester.ui.screen.requestlist.requestListScreenRoute

val bottomNavBarItems = listOf(
    BottomNavBarItem(
        route = mainScreenRoute,
        iconImageVector = Icons.Default.Home,
        label = "Home",
    ),
    BottomNavBarItem(
        route = requestListScreenRoute,
        iconImageVector = Icons.Default.Info,
        label = "RequestList"
    ),
    BottomNavBarItem(
        route = myProfileScreenRoute,
        iconImageVector = Icons.Default.Person,
        label = "MyProfile"
    )
)

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavBarItem>,
    currNavScreenRoute: String?,
    onBottomNavBarButtonPressed: (BottomNavBarItem) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            ToggleableIcon(
                modifier = Modifier.weight(1f),
                isToggled = item.route == currNavScreenRoute,
                imageVector = item.iconImageVector,
            ) {
                onBottomNavBarButtonPressed(item)
            }
        }
    }
}

data class BottomNavBarItem(
    val iconImageVector: ImageVector,
    val route: String,
    val label: String? = null,
)