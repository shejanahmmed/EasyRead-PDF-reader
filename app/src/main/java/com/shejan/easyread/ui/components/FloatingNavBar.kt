package com.shejan.easyread.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class NavItem(
    val label: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val navItems = listOf(
    NavItem("Home",     "home",     Icons.Filled.Home,         Icons.Outlined.Home),
    NavItem("Library",  "library",  Icons.Filled.LibraryBooks, Icons.Outlined.LibraryBooks),
    NavItem("Search",   "search",   Icons.Filled.Search,       Icons.Outlined.Search),
    NavItem("Create",   "create",   Icons.Filled.Add,          Icons.Outlined.Add),
    NavItem("Settings", "settings", Icons.Filled.Settings,     Icons.Outlined.Settings)
)

@Composable
fun FloatingNavBar(
    currentRoute: String,
    onNavItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(6.dp),
                    ambientColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                    spotColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.12f)
                )
                .border(
                    width = 0.8.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.55f),
                    shape = RoundedCornerShape(6.dp)
                )
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val isSelected = currentRoute == item.route
                NavBarItem(
                    item = item,
                    isSelected = isSelected,
                    onClick = { onNavItemClick(item.route) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(200),
        label = "icon_color_${item.label}"
    )
    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) 24.dp else 0.dp,
        animationSpec = tween(250),
        label = "indicator_${item.label}"
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        Icon(
            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
            contentDescription = item.label,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )

        Text(
            text = item.label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = iconColor,
            maxLines = 1
        )

        // Animated indicator bar below label
        Box(
            modifier = Modifier
                .width(indicatorWidth)
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}
