package com.shejan.easyread.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * EasyRead Shape System — Box-First Design Language.
 * All elements are sharp / near-square. Max corner radius is 6dp.
 */
val EasyReadShapes = Shapes(
    // Buttons, input fields, chips, list-item badges
    extraSmall = RoundedCornerShape(3.dp),
    // Cards (PDF list items), search bar container
    small      = RoundedCornerShape(4.dp),
    // Floating nav bar container, dialogs
    medium     = RoundedCornerShape(6.dp),
    // Bottom sheets (top corners only handled at call site)
    large      = RoundedCornerShape(6.dp),
    extraLarge = RoundedCornerShape(6.dp)
)
