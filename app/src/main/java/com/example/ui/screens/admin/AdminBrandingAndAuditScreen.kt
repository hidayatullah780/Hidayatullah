package com.example.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.AuditLogEntity
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AdminBrandingAndAuditScreen(
    settings: Map<String, String>,
    auditLogs: List<AuditLogEntity>,
    onUpdateSettings: (Map<String, String>) -> Unit,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabs = listOf("Branding & Homepage Editor", "Security Audit Logs")

    // Editable settings state
    var orgName by remember(settings) { mutableStateOf(settings["org_name"] ?: "Students' Democratic Union Federation") }
    var slogan by remember(settings) { mutableStateOf(settings["slogan"] ?: "Democratic participation, student representation and organizational development.") }
    var heroTitle by remember(settings) { mutableStateOf(settings["hero_title"] ?: "Students' Democratic Union Federation") }
    var heroSubtitle by remember(settings) { mutableStateOf(settings["hero_subtitle"] ?: slogan) }
    var heroButtonText by remember(settings) { mutableStateOf(settings["hero_button_text"] ?: "Explore SDUF") }
    var contactEmail by remember(settings) { mutableStateOf(settings["contact_email"] ?: "central.secretariat@sduf.org") }
    var contactPhone by remember(settings) { mutableStateOf(settings["contact_phone"] ?: "+92 300 1234567") }
    var officeAddress by remember(settings) { mutableStateOf(settings["office_address"] ?: "Central Secretariat SDUF, Student Movement Complex, Sindh, Pakistan") }
    var aboutText by remember(settings) { mutableStateOf(settings["about_sduf"] ?: "") }
    var preambleText by remember(settings) { mutableStateOf(settings["constitution_preamble"] ?: "") }

    var savedBanner by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_branding_audit_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SdufNavyPrimary)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "PORTAL CUSTOMIZATION & AUDIT TRAIL",
                    color = SdufGoldAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Live Content & Security Control",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Modify core organizational copy dynamically without source code redeployments, and inspect timestamped admin audit trails.",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 12.sp
                )
            }
        }

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 12.sp
                        )
                    }
                )
            }
        }

        if (selectedTab == 0) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "1. Identity & Slogan",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SdufNavyPrimary
                            )

                            OutlinedTextField(
                                value = orgName,
                                onValueChange = { orgName = it },
                                label = { Text("Organization Name") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = slogan,
                                onValueChange = { slogan = it },
                                label = { Text("Official Slogan") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                            Text(
                                text = "2. Homepage Hero Section",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SdufNavyPrimary
                            )

                            OutlinedTextField(
                                value = heroTitle,
                                onValueChange = { heroTitle = it },
                                label = { Text("Hero Title") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = heroSubtitle,
                                onValueChange = { heroSubtitle = it },
                                label = { Text("Hero Subtitle") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = heroButtonText,
                                onValueChange = { heroButtonText = it },
                                label = { Text("Hero Primary Action Button Label") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                            Text(
                                text = "3. Official Mission & Constitution Preamble",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SdufNavyPrimary
                            )

                            OutlinedTextField(
                                value = aboutText,
                                onValueChange = { aboutText = it },
                                label = { Text("About SDUF Narrative") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            OutlinedTextField(
                                value = preambleText,
                                onValueChange = { preambleText = it },
                                label = { Text("Constitution Preamble") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                            Text(
                                text = "4. Secretariat Contact Details",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = SdufNavyPrimary
                            )

                            OutlinedTextField(
                                value = contactEmail,
                                onValueChange = { contactEmail = it },
                                label = { Text("Official Email") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = contactPhone,
                                onValueChange = { contactPhone = it },
                                label = { Text("Helpline Phone") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = officeAddress,
                                onValueChange = { officeAddress = it },
                                label = { Text("Headquarters Address") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Button(
                                onClick = {
                                    val updatedMap = mapOf(
                                        "org_name" to orgName.trim(),
                                        "slogan" to slogan.trim(),
                                        "hero_title" to heroTitle.trim(),
                                        "hero_subtitle" to heroSubtitle.trim(),
                                        "hero_button_text" to heroButtonText.trim(),
                                        "about_sduf" to aboutText.trim(),
                                        "constitution_preamble" to preambleText.trim(),
                                        "contact_email" to contactEmail.trim(),
                                        "contact_phone" to contactPhone.trim(),
                                        "office_address" to officeAddress.trim()
                                    )
                                    onUpdateSettings(updatedMap)
                                    savedBanner = true
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SdufNavyPrimary),
                                modifier = Modifier.fillMaxWidth().height(48.dp).testTag("save_branding_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = null,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text("Publish Branding & Homepage Changes", fontWeight = FontWeight.Bold)
                            }

                            if (savedBanner) {
                                Text(
                                    text = "✓ Changes published! Public app and homepage updated dynamically.",
                                    color = Color(0xFF15803D),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(auditLogs) { log ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                                ) {
                                    Text(
                                        text = "Admin: ${log.adminName}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Text(
                                    text = log.formattedDate,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = log.action,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Record Affected: ${log.recordAffected}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (log.oldValue.isNotBlank() || log.newValue.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        if (log.oldValue.isNotBlank()) {
                                            Text(
                                                text = "Old: ${log.oldValue}",
                                                fontSize = 11.sp,
                                                color = Color(0xFFB91C1C),
                                                fontFamily = FontFamily.Monospace
                                            )
                                        }
                                        if (log.newValue.isNotBlank()) {
                                            Text(
                                                text = "New: ${log.newValue}",
                                                fontSize = 11.sp,
                                                color = Color(0xFF15803D),
                                                fontFamily = FontFamily.Monospace,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
