package com.gausslab.timeoffrequester.ui.comp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.baec23.ludwig.core.togglable.ToggleableIcon
import com.gausslab.timeoffrequester.ui.login.loginScreenRoute
import com.gausslab.timeoffrequester.ui.main.mainScreenRoute

val bottomNavBarItems = listOf(
    BottomNavBarItem(
        route = mainScreenRoute,
        iconImageVector = Icons.Default.Home,
        label = "Home"
    ),
    BottomNavBarItem(
        route = loginScreenRoute,
        iconImageVector = Icons.Default.AccountBox,
        label = "Login"
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