package com.example.ui.screens.member

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.MemberEntity
import com.example.ui.components.OfficialMembershipCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyPrimary
import com.example.ui.viewmodel.Screen

@Composable
fun MemberDashboardScreen(
    member: MemberEntity,
    onNavigate: (Screen) -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("member_dashboard_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Card with Official Credentials
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = SdufNavyPrimary)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = SdufGoldAccent.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SdufGoldAccent)
                        ) {
                            Text(
                                text = "MEMBER PORTAL",
                                color = SdufGoldAccent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                letterSpacing = 1.sp
                            )
                        }
                        StatusBadge(status = member.status)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Welcome, ${member.fullName}",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Column {
                            Text(
                                text = "MEMBER ID",
                                color = SdufGoldAccent,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = member.memberId,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        Column {
                            Text(
                                text = "DESIGNATION",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = member.designation,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Column {
                            Text(
                                text = "UNIT",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = member.unitName,
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        // Digital Membership Card Preview
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Official Membership Card",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedButton(
                        onClick = { onNavigate(Screen.MemberCard) },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Full Card View", fontSize = 11.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OfficialMembershipCard(
                    member = member,
                    onVerifyClick = { onNavigate(Screen.VerifyCard) }
                )
            }
        }

        // Quick Service Navigation Grid
        item {
            Text(
                text = "MEMBER SERVICES & RECORDS",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MemberActionCard(
                        title = "My Profile",
                        desc = "View & edit personal contact details",
                        icon = Icons.Default.Person,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.MemberProfile) }

                    MemberActionCard(
                        title = "Digital Card",
                        desc = "Pass & QR verification matrix",
                        icon = Icons.Default.Badge,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.MemberCard) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MemberActionCard(
                        title = "Notifications",
                        desc = "Official announcements & notices",
                        icon = Icons.Default.Notifications,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.Announcements) }

                    MemberActionCard(
                        title = "Events",
                        desc = "Assemblies & oath ceremonies",
                        icon = Icons.Default.Event,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.Events) }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MemberActionCard(
                        title = "Documents",
                        desc = "Constitution & bylaws archive",
                        icon = Icons.Default.Description,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.Documents) }

                    MemberActionCard(
                        title = "Org Structure",
                        desc = "Hierarchy & central wings",
                        icon = Icons.Default.LocationCity,
                        modifier = Modifier.weight(1f)
                    ) { onNavigate(Screen.About) }
                }
            }
        }

        // Logout card
        item {
            OutlinedButton(
                onClick = onLogout,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth().height(48.dp).testTag("member_logout_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = "Sign Out of Portal", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun MemberActionCard(
    title: String,
    desc: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.testTag("member_action_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
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
                maxLines = 2
            )
        }
    }
}
