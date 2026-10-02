package com.example.ui.screens.public

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SectionHeader
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufGoldDark
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AboutScreen(
    settings: Map<String, String>,
    modifier: Modifier = Modifier
) {
    val aboutText = settings["about_sduf"] ?: "Students' Democratic Union Federation (SDUF) is a premier progressive student movement founded on the tenets of social justice, constitutional democracy, student welfare, and visionary leadership development across universities and colleges."
    val preamble = settings["constitution_preamble"] ?: "We, the students and youth belonging to diverse academic disciplines, unite under the Students' Democratic Union Federation (SDUF) to protect academic liberties, dismantle educational inequalities, and champion constitutional student governance."

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("about_screen_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mission & Identity
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
                            text = "OUR FOUNDATIONAL MISSION",
                            color = SdufGoldAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Students' Democratic Union Federation",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = aboutText,
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Core Objectives
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Core Objectives",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    ObjectiveRow("Democratic Participation", "Fostering active voting culture and transparent campus council representation.")
                    ObjectiveRow("Academic Freedom", "Defending student rights against unjust disciplinary measures and institutional censorship.")
                    ObjectiveRow("Affordable Quality Education", "Advocating for reduced university tuition, expanded hostel accommodations, and merit-cum-need scholarships.")
                    ObjectiveRow("Youth Leadership Cultivation", "Equipping students with debate, parliamentary procedure, and administrative excellence.")
                }
            }
        }

        // Organizational Structure Hierarchy Diagram
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Organizational Hierarchy",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = "Configurable multi-tier governance model",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    HierarchyLevelBox("Founder & Chairman", SdufNavyDark, Color.White, true)
                    HierarchyConnector()
                    HierarchyLevelBox("Central Leadership (President & Secretariat)", SdufNavyPrimary, Color.White, true)
                    HierarchyConnector()
                    HierarchyLevelBox("Central Departments (Info, Finance, Org, Student Affairs)", Color(0xFF2563EB), Color.White)
                    HierarchyConnector()
                    HierarchyLevelBox("Provincial Units (Sindh & Regional Federations)", Color(0xFF0D9488), Color.White)
                    HierarchyConnector()
                    HierarchyLevelBox("District Units (e.g., Tando Allahyar, Hyderabad)", Color(0xFFD97706), Color.White)
                    HierarchyConnector()
                    HierarchyLevelBox("Taluka / Tehsil Units (e.g., Chamber)", Color(0xFF7C3AED), Color.White)
                    HierarchyConnector()
                    HierarchyLevelBox("Local & Institutional Units (Campus Chapters)", Color(0xFF475569), Color.White)
                }
            }
        }

        // Constitution Preamble
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = SdufGoldDark
                        )
                        Text(
                            text = "Constitution Preamble (Ratified v2.0)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = SdufNavyPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "“$preamble”",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ObjectiveRow(title: String, desc: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(SdufNavyPrimary.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = SdufNavyPrimary,
                modifier = Modifier.size(12.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun HierarchyLevelBox(
    title: String,
    bg: Color,
    textColor: Color,
    isProminent: Boolean = false
) {
    Surface(
        modifier = Modifier.fillMaxWidth(if (isProminent) 0.95f else 0.88f),
        shape = RoundedCornerShape(12.dp),
        color = bg,
        shadowElevation = if (isProminent) 4.dp else 1.dp
    ) {
        Text(
            text = title,
            color = textColor,
            fontSize = if (isProminent) 12.sp else 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun HierarchyConnector() {
    Icon(
        imageVector = Icons.Default.ArrowDownward,
        contentDescription = "hierarchy connector",
        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.size(16.dp).padding(vertical = 1.dp)
    )
}
