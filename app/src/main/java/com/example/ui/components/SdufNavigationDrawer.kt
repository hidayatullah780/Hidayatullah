package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.ContactSupport
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.entities.MemberEntity
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyLight
import com.example.ui.theme.SdufNavyPrimary
import com.example.ui.viewmodel.Screen

@Composable
fun SdufDrawerContent(
    currentScreen: Screen,
    currentUser: MemberEntity?,
    pendingCount: Int,
    onNavigate: (Screen) -> Unit,
    onSwitchPersona: (String) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showPersonaSelector by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        // Drawer Header with SDUF Identity
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(SdufNavyDark, SdufNavyPrimary)
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(54.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_sduf_logo),
                            contentDescription = "SDUF Logo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.padding(2.dp).clip(CircleShape)
                        )
                    }
                    Column {
                        Text(
                            text = "SDUF",
                            color = SdufGoldAccent,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Students' Democratic Union Federation",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Current Role Indicator & Quick Switcher
                Surface(
                    onClick = { showPersonaSelector = !showPersonaSelector },
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White.copy(alpha = 0.12f),
                    modifier = Modifier.fillMaxWidth().testTag("switch_persona_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = if (currentUser != null) currentUser.fullName else "Public Visitor",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (currentUser != null) "${currentUser.designation} • ${currentUser.role}" else "Browsing without login",
                                color = SdufGoldAccent,
                                fontSize = 10.sp
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.SwitchAccount,
                            contentDescription = "Switch Account Persona",
                            tint = SdufGoldAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                if (showPersonaSelector) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Black.copy(alpha = 0.25f))
                            .padding(6.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "QUICK DEMO PERSONA SWITCH:",
                            color = SdufGoldAccent,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                        PersonaItem("Super Admin: Chairman Hidayatullah") {
                            onSwitchPersona("CHAIRMAN")
                            showPersonaSelector = false
                        }
                        PersonaItem("Admin: General Secretary Saqib") {
                            onSwitchPersona("SECRETARY")
                            showPersonaSelector = false
                        }
                        PersonaItem("Member: Tariq Mahmood") {
                            onSwitchPersona("MEMBER")
                            showPersonaSelector = false
                        }
                        PersonaItem("Public Visitor Mode") {
                            onSwitchPersona("PUBLIC")
                            showPersonaSelector = false
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // PUBLIC NAVIGATION SECTION
        DrawerSectionHeader(title = "PUBLIC ORGANIZATION PORTAL")
        DrawerNavItem(
            label = "Home",
            icon = Icons.Default.Home,
            selected = currentScreen is Screen.Home,
            onClick = { onNavigate(Screen.Home) }
        )
        DrawerNavItem(
            label = "About SDUF & Constitution",
            icon = Icons.Default.Info,
            selected = currentScreen is Screen.About,
            onClick = { onNavigate(Screen.About) }
        )
        DrawerNavItem(
            label = "Central Leadership",
            icon = Icons.Default.People,
            selected = currentScreen is Screen.Leadership,
            onClick = { onNavigate(Screen.Leadership) }
        )
        DrawerNavItem(
            label = "Departments",
            icon = Icons.Default.AccountBalance,
            selected = currentScreen is Screen.Departments,
            onClick = { onNavigate(Screen.Departments) }
        )
        DrawerNavItem(
            label = "Organizational Units",
            icon = Icons.Default.LocationCity,
            selected = currentScreen is Screen.Units,
            onClick = { onNavigate(Screen.Units) }
        )
        DrawerNavItem(
            label = "Announcements",
            icon = Icons.Default.Campaign,
            selected = currentScreen is Screen.Announcements,
            onClick = { onNavigate(Screen.Announcements) }
        )
        DrawerNavItem(
            label = "Events Calendar",
            icon = Icons.Default.Event,
            selected = currentScreen is Screen.Events,
            onClick = { onNavigate(Screen.Events) }
        )
        DrawerNavItem(
            label = "Document Library",
            icon = Icons.Default.Description,
            selected = currentScreen is Screen.Documents,
            onClick = { onNavigate(Screen.Documents) }
        )
        DrawerNavItem(
            label = "Photo Gallery",
            icon = Icons.Default.Collections,
            selected = currentScreen is Screen.Gallery,
            onClick = { onNavigate(Screen.Gallery) }
        )
        DrawerNavItem(
            label = "Join SDUF / Registration",
            icon = Icons.Default.HowToReg,
            selected = currentScreen is Screen.MembershipApply,
            onClick = { onNavigate(Screen.MembershipApply) }
        )
        DrawerNavItem(
            label = "Verify Member Card",
            icon = Icons.Default.VerifiedUser,
            selected = currentScreen is Screen.VerifyCard,
            onClick = { onNavigate(Screen.VerifyCard) }
        )
        DrawerNavItem(
            label = "Contact & Secretariat",
            icon = Icons.AutoMirrored.Filled.ContactSupport,
            selected = currentScreen is Screen.Contact,
            onClick = { onNavigate(Screen.Contact) }
        )

        // MEMBER PORTAL SECTION
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DrawerSectionHeader(title = "MEMBER PORTAL")
        if (currentUser != null) {
            DrawerNavItem(
                label = "Member Dashboard",
                icon = Icons.Default.Dashboard,
                selected = currentScreen is Screen.MemberDashboard,
                onClick = { onNavigate(Screen.MemberDashboard) }
            )
            DrawerNavItem(
                label = "Digital Membership Card",
                icon = Icons.Default.Badge,
                selected = currentScreen is Screen.MemberCard,
                onClick = { onNavigate(Screen.MemberCard) }
            )
            DrawerNavItem(
                label = "My Profile",
                icon = Icons.Default.Person,
                selected = currentScreen is Screen.MemberProfile,
                onClick = { onNavigate(Screen.MemberProfile) }
            )
        } else {
            DrawerNavItem(
                label = "Login to Member Portal",
                icon = Icons.AutoMirrored.Filled.Login,
                selected = currentScreen is Screen.Login,
                onClick = { onNavigate(Screen.Login) }
            )
        }

        // ADMIN PANEL SECTION
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        DrawerSectionHeader(
            title = "ADMIN CONTROL PANEL",
            badge = if (pendingCount > 0) "$pendingCount Pending" else null
        )
        DrawerNavItem(
            label = "Admin Dashboard",
            icon = Icons.Default.AdminPanelSettings,
            selected = currentScreen is Screen.AdminDashboard,
            onClick = { onNavigate(Screen.AdminDashboard) }
        )
        DrawerNavItem(
            label = "Members Management",
            icon = Icons.Default.Group,
            badge = if (pendingCount > 0) pendingCount.toString() else null,
            selected = currentScreen is Screen.AdminMembers,
            onClick = { onNavigate(Screen.AdminMembers) }
        )
        DrawerNavItem(
            label = "Leadership Directory",
            icon = Icons.Default.People,
            selected = currentScreen is Screen.AdminLeadership,
            onClick = { onNavigate(Screen.AdminLeadership) }
        )
        DrawerNavItem(
            label = "Appointments Management",
            icon = Icons.AutoMirrored.Filled.Assignment,
            selected = currentScreen is Screen.AdminAppointments,
            onClick = { onNavigate(Screen.AdminAppointments) }
        )
        DrawerNavItem(
            label = "Departments Manager",
            icon = Icons.Default.AccountBalance,
            selected = currentScreen is Screen.AdminDepartments,
            onClick = { onNavigate(Screen.AdminDepartments) }
        )
        DrawerNavItem(
            label = "Units & Hierarchy",
            icon = Icons.Default.LocationCity,
            selected = currentScreen is Screen.AdminUnits,
            onClick = { onNavigate(Screen.AdminUnits) }
        )
        DrawerNavItem(
            label = "Notifications & Circulars",
            icon = Icons.Default.Notifications,
            selected = currentScreen is Screen.AdminNotifications,
            onClick = { onNavigate(Screen.AdminNotifications) }
        )
        DrawerNavItem(
            label = "Events Manager",
            icon = Icons.Default.Event,
            selected = currentScreen is Screen.AdminEvents,
            onClick = { onNavigate(Screen.AdminEvents) }
        )
        DrawerNavItem(
            label = "Documents & Versions",
            icon = Icons.AutoMirrored.Filled.MenuBook,
            selected = currentScreen is Screen.AdminDocuments,
            onClick = { onNavigate(Screen.AdminDocuments) }
        )
        DrawerNavItem(
            label = "Gallery Manager",
            icon = Icons.Default.Collections,
            selected = currentScreen is Screen.AdminGallery,
            onClick = { onNavigate(Screen.AdminGallery) }
        )
        DrawerNavItem(
            label = "Branding & Homepage Editor",
            icon = Icons.Default.Palette,
            selected = currentScreen is Screen.AdminBranding,
            onClick = { onNavigate(Screen.AdminBranding) }
        )
        DrawerNavItem(
            label = "Security Audit Logs",
            icon = Icons.Default.HistoryEdu,
            selected = currentScreen is Screen.AdminAuditLogs,
            onClick = { onNavigate(Screen.AdminAuditLogs) }
        )

        // Session Actions
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        if (currentUser != null) {
            DrawerNavItem(
                label = "Sign Out",
                icon = Icons.AutoMirrored.Filled.Logout,
                selected = false,
                onClick = onLogout,
                tint = MaterialTheme.colorScheme.error
            )
        } else {
            DrawerNavItem(
                label = "Sign In",
                icon = Icons.AutoMirrored.Filled.Login,
                selected = currentScreen is Screen.Login,
                onClick = { onNavigate(Screen.Login) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun PersonaItem(label: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp),
        color = Color.White.copy(alpha = 0.08f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "• $label",
            color = Color.White,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        )
    }
}

@Composable
private fun DrawerSectionHeader(title: String, badge: String? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            letterSpacing = 1.sp
        )
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
}

@Composable
private fun DrawerNavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    badge: String? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    NavigationDrawerItem(
        label = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = label,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 13.sp,
                    color = if (selected) MaterialTheme.colorScheme.primary else tint
                )
                if (badge != null) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.error
                    ) {
                        Text(
                            text = badge,
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) MaterialTheme.colorScheme.primary else tint
            )
        },
        selected = selected,
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .testTag("nav_item_${label.lowercase().replace(" ", "_")}"),
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            unselectedContainerColor = Color.Transparent
        )
    )
}
