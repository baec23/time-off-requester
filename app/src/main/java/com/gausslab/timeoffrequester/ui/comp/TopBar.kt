package com.gausslab.timeoffrequester.ui.comp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.Navigation
import com.baec23.ludwig.component.toggleable.ToggleableIcon

val topBarItem = listOf(
    TopBarItem(
        iconImageVector = Icons.Default.ArrowBack,
        route = "뒤로가야함",
        label = "BackButton"
    ),
    TopBarItem(
        iconImageVector = Icons.Default.Menu,
        route = "logout",
        label = "Logout"
    )
)

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    items: List<TopBarItem>,
    currNavScreenRoute: String?,
    onTopBarButtonPressed: (TopBarItem) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        items.forEach{ item ->
            ToggleableIcon(
                modifier = Modifier.weight(1f),
                isToggled = item.route == currNavScreenRoute,
                imageVector = item.iconImageVector,
            ) {
                onTopBarButtonPressed(item)
            }
        }
    }
}


data class TopBarItem(
    val iconImageVector: ImageVector,
    val route: String,
    val label: String? = null,
)