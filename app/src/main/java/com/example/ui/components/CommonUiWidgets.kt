package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufGoldDark
import com.example.ui.theme.SdufGreen
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyLight
import com.example.ui.theme.SdufNavyPrimary
import com.example.ui.theme.SdufRed

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, icon) = when (status.uppercase()) {
        "ACTIVE", "PUBLISHED", "UPCOMING" -> Triple(
            Color(0xFFDCFCE7),
            Color(0xFF15803D),
            Icons.Default.CheckCircle
        )
        "PENDING", "DRAFT", "SCHEDULED" -> Triple(
            Color(0xFFFEF3C7),
            Color(0xFFB45309),
            Icons.Default.Info
        )
        "SUSPENDED", "CANCELLED", "REVOKED" -> Triple(
            Color(0xFFFEE2E2),
            Color(0xFFB91C1C),
            Icons.Default.Warning
        )
        "ARCHIVED", "COMPLETED", "INACTIVE" -> Triple(
            Color(0xFFF1F5F9),
            Color(0xFF475569),
            Icons.Default.Info
        )
        else -> Triple(
            Color(0xFFE2E8F0),
            Color(0xFF334155),
            Icons.Default.Info
        )
    }

    Surface(
        modifier = modifier.testTag("status_badge_${status.lowercase()}"),
        shape = RoundedCornerShape(12.dp),
        color = bgColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = status.uppercase(),
                color = textColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String? = null,
    icon: ImageVector,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    accentColor: Color = SdufNavyPrimary,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.labelSmall,
                        color = accentColor
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (actionText != null && onActionClick != null) {
            Surface(
                onClick = onActionClick,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = actionText,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun StylizedQrMatrix(
    seedString: String,
    sizeDp: Dp = 100.dp,
    matrixColor: Color = SdufNavyDark,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(sizeDp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(8.dp))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(sizeDp - 16.dp)) {
            val cols = 15
            val cellSize = size.width / cols
            val hash = seedString.hashCode()

            // Draw corner positioning squares
            fun drawCornerFinder(offsetX: Float, offsetY: Float) {
                drawRect(
                    color = matrixColor,
                    topLeft = Offset(offsetX, offsetY),
                    size = Size(cellSize * 4, cellSize * 4)
                )
                drawRect(
                    color = Color.White,
                    topLeft = Offset(offsetX + cellSize, offsetY + cellSize),
                    size = Size(cellSize * 2, cellSize * 2)
                )
                drawRect(
                    color = matrixColor,
                    topLeft = Offset(offsetX + cellSize * 1.5f, offsetY + cellSize * 1.5f),
                    size = Size(cellSize, cellSize)
                )
            }

            drawCornerFinder(0f, 0f)
            drawCornerFinder((cols - 4) * cellSize, 0f)
            drawCornerFinder(0f, (cols - 4) * cellSize)

            // Fill pseudorandom grid based on hash
            for (r in 0 until cols) {
                for (c in 0 until cols) {
                    val inTopLeft = r < 5 && c < 5
                    val inTopRight = r < 5 && c >= cols - 5
                    val inBottomLeft = r >= cols - 5 && c < 5
                    if (!inTopLeft && !inTopRight && !inBottomLeft) {
                        val bit = (hash xor (r * 31 + c * 17)) % 3 == 0
                        if (bit) {
                            drawRect(
                                color = matrixColor,
                                topLeft = Offset(c * cellSize, r * cellSize),
                                size = Size(cellSize * 0.9f, cellSize * 0.9f)
                            )
                        }
                    }
                }
            }
        }
    }
}
