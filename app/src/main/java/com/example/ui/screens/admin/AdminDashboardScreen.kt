package com.example.ui.screens.admin

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.StatCard
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyPrimary
import com.example.ui.viewmodel.Screen

@Composable
fun AdminDashboardScreen(
    totalMembers: Int,
    pendingMembers: Int,
    activeUnitsCount: Int,
    upcomingEventsCount: Int,
    totalDocsCount: Int,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_dashboard_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Control Center Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SdufNavyPrimary)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SdufGoldAccent.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SdufGoldAccent)
                    ) {
                        Text(
                            text = "CENTRAL ADMINISTRATIVE CONSOLE",
                            color = SdufGoldAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "SDUF Administration Panel",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Full dynamic governance control: update leadership, approve pending members, publish notifications, and edit portal branding without writing code.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Executive Metrics Grid
        item {
            Text(
                text = "ORGANIZATIONAL METRICS",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        title = "Total Members",
                        value = totalMembers.toString(),
                        subtitle = "Registered Roster",
                        icon = Icons.Default.Group,
                        accentColor = SdufNavyPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.AdminMembers) }
                    )

                    StatCard(
                        title = "Pending Approvals",
                        value = pendingMembers.toString(),
                        subtitle = if (pendingMembers > 0) "Action Required" else "All Cleared",
                        icon = Icons.Default.AdminPanelSettings,
                        accentColor = if (pendingMembers > 0) Color(0xFFD97706) else Color(0xFF10B981),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.AdminMembers) }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatCard(
                        title = "Active Units",
                        value = activeUnitsCount.toString(),
                        subtitle = "Provincial / Local",
                        icon = Icons.Default.LocationCity,
                        accentColor = Color(0xFF2563EB),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.AdminUnits) }
                    )

                    StatCard(
                        title = "Upcoming Events",
                        value = upcomingEventsCount.toString(),
                        subtitle = "Scheduled",
                        icon = Icons.Default.Event,
                        accentColor = Color(0xFF0D9488),
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(Screen.AdminEvents) }
                    )
                }
            }
        }

        // Administrative Management Modules Grid
        item {
            Text(
                text = "MANAGEMENT MODULES",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminModuleTile(
                        title = "Members Management",
                        badge = if (pendingMembers > 0) "$pendingMembers Pending" else null,
                        desc = "Approve applications, roster & statuses",
                        icon = Icons.Default.Group,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminMembers) }

                    AdminModuleTile(
                        title = "Leadership Directory",
                        desc = "Add, edit & reorder executives",
                        icon = Icons.Default.People,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminLeadership) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminModuleTile(
                        title = "Appointments",
                        desc = "Letters, terms & designations",
                        icon = Icons.AutoMirrored.Filled.Assignment,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminAppointments) }

                    AdminModuleTile(
                        title = "Departments Manager",
                        desc = "Heads, deputies & objectives",
                        icon = Icons.Default.AccountBalance,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminDepartments) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminModuleTile(
                        title = "Units & Hierarchy",
                        desc = "Provinces, districts & talukas",
                        icon = Icons.Default.LocationCity,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminUnits) }

                    AdminModuleTile(
                        title = "Notices & Circulars",
                        desc = "Create, draft & publish orders",
                        icon = Icons.Default.Notifications,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminNotifications) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminModuleTile(
                        title = "Events Manager",
                        desc = "Oaths, rallies & conventions",
                        icon = Icons.Default.Event,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminEvents) }

                    AdminModuleTile(
                        title = "Document Archive",
                        desc = "Constitution versions & bylaws",
                        icon = Icons.AutoMirrored.Filled.MenuBook,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminDocuments) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminModuleTile(
                        title = "Gallery Manager",
                        desc = "Upload photo albums & activities",
                        icon = Icons.Default.Collections,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminGallery) }

                    AdminModuleTile(
                        title = "Homepage & Branding",
                        desc = "Edit title, slogan & contact info",
                        icon = Icons.Default.Palette,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminBranding) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AdminModuleTile(
                        title = "Security Audit Logs",
                        desc = "Track every administrative edit",
                        icon = Icons.Default.HistoryEdu,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.AdminAuditLogs) }
                }
            }
        }
    }
}

@Composable
private fun AdminModuleTile(
    title: String,
    desc: String,
    icon: ImageVector,
    badge: String? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.testTag("admin_module_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SdufNavyPrimary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = SdufNavyPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (badge != null) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.errorContainer
                    ) {
                        Text(
                            text = badge,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp,
                maxLines = 2
            )
        }
    }
}
