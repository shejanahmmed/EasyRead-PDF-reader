package com.shejan.easyread.ui.settings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrightnessAuto
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shejan.easyread.ui.theme.ThemePreference
import com.shejan.easyread.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    themeViewModel: ThemeViewModel = viewModel()
) {
    val currentTheme by themeViewModel.theme.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Settings",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Customize your experience",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            // ── Appearance section ────────────────────────────────────────────
            SettingsSectionLabel("Appearance")

            SettingsCard {
                ThemeOptionRow(
                    icon       = Icons.Outlined.BrightnessAuto,
                    label      = "System default",
                    description = "Follows your device theme",
                    selected   = currentTheme == ThemePreference.SYSTEM,
                    onClick    = { themeViewModel.setTheme(ThemePreference.SYSTEM) }
                )
                RowDivider()
                ThemeOptionRow(
                    icon       = Icons.Outlined.LightMode,
                    label      = "Light",
                    description = "Always use light mode",
                    selected   = currentTheme == ThemePreference.LIGHT,
                    onClick    = { themeViewModel.setTheme(ThemePreference.LIGHT) }
                )
                RowDivider()
                ThemeOptionRow(
                    icon       = Icons.Outlined.DarkMode,
                    label      = "Dark",
                    description = "Always use dark mode",
                    selected   = currentTheme == ThemePreference.DARK,
                    onClick    = { themeViewModel.setTheme(ThemePreference.DARK) }
                )
            }

            // ── About section ─────────────────────────────────────────────────
            SettingsSectionLabel("About")

            SettingsCard {
                NavLinkRow(
                    icon        = Icons.Outlined.Shield,
                    label       = "Privacy Policy",
                    description = "How we handle your data",
                    onClick     = { /* TODO */ }
                )
                RowDivider()
                NavLinkRow(
                    icon        = Icons.Outlined.Person,
                    label       = "About Developer",
                    description = "Meet the person behind EasyRead",
                    onClick     = { /* TODO */ }
                )
                RowDivider()
                NavLinkRow(
                    icon        = Icons.Outlined.Info,
                    label       = "Version Info",
                    description = "App version and build details",
                    onClick     = { /* TODO */ }
                )
            }

            Spacer(Modifier.height(80.dp)) // bottom nav clearance
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Section label
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun SettingsSectionLabel(title: String) {
    Text(
        text  = title.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        letterSpacing = androidx.compose.ui.unit.TextUnit(
            1.2f, androidx.compose.ui.unit.TextUnitType.Sp
        )
    )
}

// ─────────────────────────────────────────────────────────────────────────────
// Card container
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content()
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Theme option row
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun ThemeOptionRow(
    icon: ImageVector,
    label: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val iconBg by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        else
            MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(200),
        label = "icon_bg"
    )
    val iconTint by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(200),
        label = "icon_tint"
    )
    val dotColor by animateColorAsState(
        targetValue = if (selected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.outline,
        animationSpec = tween(200),
        label = "dot_color"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Icon box
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }

        // Text
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text  = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text  = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Radio dot
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = if (selected) 5.dp else 1.5.dp,
                    color = dotColor,
                    shape = CircleShape
                )
        )
        Spacer(Modifier.width(2.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Nav link row (chevron-style, no radio dot)
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun NavLinkRow(
    icon: ImageVector,
    label: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Icon box
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(20.dp)
            )
        }

        // Text
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text  = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text  = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Chevron
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(18.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Row divider
// ─────────────────────────────────────────────────────────────────────────────
@Composable
private fun RowDivider() {
    HorizontalDivider(
        modifier  = Modifier.padding(start = 70.dp),
        thickness = 0.5.dp,
        color     = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
    )
}
