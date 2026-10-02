package com.example.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.LeadershipEntity
import com.example.ui.components.StatusBadge
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AdminLeadershipScreen(
    leadershipList: List<LeadershipEntity>,
    onSaveLeader: (LeadershipEntity) -> Unit,
    onDeleteLeader: (LeadershipEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingLeader by remember { mutableStateOf<LeadershipEntity?>(null) }
    var isNew by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_leadership_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SdufNavyPrimary)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "EXECUTIVE APPOINTMENT ROSTER",
                        color = SdufGoldAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Leadership Management",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Add, modify, or reorder central leadership records. Any updates saved here immediately reflect across the public app and website.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            isNew = true
                            editingLeader = LeadershipEntity(
                                id = 0L,
                                name = "",
                                position = "",
                                department = "Central Executive",
                                bio = "",
                                appointmentDate = "02 Oct 2026",
                                displayOrder = leadershipList.size + 1,
                                status = "ACTIVE"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SdufGoldAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("add_leader_button")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = SdufNavyPrimary)
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Add Leadership Person", color = SdufNavyPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        items(leadershipList) { leader ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_leader_card_${leader.id}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                                ) {
                                    Text(
                                        text = "#${leader.displayOrder}",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.size(6.dp))
                                Text(
                                    text = leader.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = leader.position,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        StatusBadge(status = leader.status)
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Department: ${leader.department} • Appointed: ${leader.appointmentDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = leader.bio,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            isNew = false
                            editingLeader = leader
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Leader")
                        }
                        IconButton(onClick = { onDeleteLeader(leader) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }

    editingLeader?.let { current ->
        var name by remember { mutableStateOf(current.name) }
        var position by remember { mutableStateOf(current.position) }
        var department by remember { mutableStateOf(current.department) }
        var bio by remember { mutableStateOf(current.bio) }
        var appointmentDate by remember { mutableStateOf(current.appointmentDate) }
        var displayOrder by remember { mutableIntStateOf(current.displayOrder) }
        var status by remember { mutableStateOf(current.status) }

        AlertDialog(
            onDismissRequest = { editingLeader = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            name = name.trim(),
                            position = position.trim(),
                            department = department.trim(),
                            bio = bio.trim(),
                            appointmentDate = appointmentDate.trim(),
                            displayOrder = displayOrder,
                            status = status
                        )
                        onSaveLeader(updated)
                        editingLeader = null
                    },
                    enabled = name.isNotBlank() && position.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = SdufNavyPrimary)
                ) {
                    Text("Save & Publish")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingLeader = null }) {
                    Text("Cancel")
                }
            },
            title = {
                Text(if (isNew) "Add Leadership Person" else "Edit Leadership Person")
            },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Person Name *") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("leader_name_input")
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = position,
                            onValueChange = { position = it },
                            label = { Text("Position / Title *") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("leader_position_input")
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = department,
                            onValueChange = { department = it },
                            label = { Text("Department / Council") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = appointmentDate,
                            onValueChange = { appointmentDate = it },
                            label = { Text("Appointment Date") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = displayOrder.toString(),
                            onValueChange = { displayOrder = it.toIntOrNull() ?: 1 },
                            label = { Text("Display Order Index") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = bio,
                            onValueChange = { bio = it },
                            label = { Text("Biography & Role Overview") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        Text("Status: $status", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                onClick = { status = "ACTIVE" },
                                shape = RoundedCornerShape(8.dp),
                                color = if (status == "ACTIVE") SdufNavyPrimary else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "ACTIVE",
                                    fontSize = 11.sp,
                                    color = if (status == "ACTIVE") Color.White else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Surface(
                                onClick = { status = "ARCHIVED" },
                                shape = RoundedCornerShape(8.dp),
                                color = if (status == "ARCHIVED") SdufNavyPrimary else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "ARCHIVED",
                                    fontSize = 11.sp,
                                    color = if (status == "ARCHIVED") Color.White else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}
