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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.MemberEntity
import com.example.ui.components.OfficialMembershipCard
import com.example.ui.components.StatusBadge
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AdminMembersScreen(
    members: List<MemberEntity>,
    onApproveMember: (MemberEntity) -> Unit,
    onUpdateStatus: (MemberEntity, String) -> Unit,
    onUpdateRole: (MemberEntity, String, String) -> Unit,
    onDeleteMember: (MemberEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedMember by remember { mutableStateOf<MemberEntity?>(null) }
    var editingMember by remember { mutableStateOf<MemberEntity?>(null) }

    val filterOptions = listOf("All", "Pending", "Active", "Suspended", "Inactive")

    val filteredList = members.filter { mem ->
        val matchesFilter = when (selectedFilter) {
            "All" -> true
            "Pending" -> mem.status == "PENDING"
            "Active" -> mem.status == "ACTIVE"
            "Suspended" -> mem.status == "SUSPENDED"
            "Inactive" -> mem.status == "INACTIVE"
            else -> true
        }
        val q = searchQuery.trim().lowercase()
        val matchesSearch = q.isBlank() ||
                mem.fullName.lowercase().contains(q) ||
                mem.memberId.lowercase().contains(q) ||
                mem.unitName.lowercase().contains(q) ||
                mem.designation.lowercase().contains(q) ||
                mem.email.lowercase().contains(q)

        matchesFilter && matchesSearch
    }

    val total = members.size
    val active = members.count { it.status == "ACTIVE" }
    val pending = members.count { it.status == "PENDING" }
    val suspended = members.count { it.status == "SUSPENDED" }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_members_lazy_column"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Summary Counts Bar
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    CountPill("Total", total, MaterialTheme.colorScheme.onSurface)
                    CountPill("Active", active, Color(0xFF15803D))
                    CountPill("Pending", pending, Color(0xFFB45309))
                    CountPill("Suspended", suspended, Color(0xFFB91C1C))
                }
            }
        }

        // Search Field
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search by Name, Member ID, Unit or Designation...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().testTag("admin_search_member_input"),
                singleLine = true
            )
        }

        // Filter Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filterOptions) { opt ->
                    FilterChip(
                        selected = selectedFilter == opt,
                        onClick = { selectedFilter = opt },
                        label = { Text(opt, fontSize = 12.sp) }
                    )
                }
            }
        }

        items(filteredList) { member ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_member_card_${member.id}"),
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(SdufNavyPrimary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = member.fullName.take(2).uppercase(),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Column {
                                Text(
                                    text = member.fullName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = member.memberId,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                        StatusBadge(status = member.status)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Designation: ${member.designation} • Unit: ${member.unitName}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Institution: ${member.institution} • Phone: ${member.phone}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Administrative Actions Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { selectedMember = member }) {
                            Text("View Details", fontSize = 12.sp)
                        }

                        if (member.status == "PENDING") {
                            Button(
                                onClick = { onApproveMember(member) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF15803D)),
                                modifier = Modifier.padding(start = 6.dp).testTag("approve_btn_${member.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Approve & Issue ID", fontSize = 12.sp)
                            }
                        } else {
                            OutlinedButton(
                                onClick = { editingMember = member },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.padding(start = 6.dp)
                            ) {
                                Text("Manage Status/Role", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }

    // View Member Details Dialog
    selectedMember?.let { member ->
        AlertDialog(
            onDismissRequest = { selectedMember = null },
            confirmButton = {
                TextButton(onClick = { selectedMember = null }) {
                    Text("Close")
                }
            },
            title = {
                Text(
                    text = "${member.fullName} — Record",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OfficialMembershipCard(member = member, onVerifyClick = null)

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Father Name: ${member.fatherName}", fontSize = 12.sp)
                    Text("DOB: ${member.dob} • Gender: ${member.gender}", fontSize = 12.sp)
                    Text("Phone: ${member.phone} • Email: ${member.email}", fontSize = 12.sp)
                    Text("District: ${member.district} • Taluka: ${member.taluka}", fontSize = 12.sp)
                    Text("Union Council: ${member.unionCouncil}", fontSize = 12.sp)
                    Text("Class / Degree: ${member.degreeClass}", fontSize = 12.sp)
                    Text("Permanent Address: ${member.address}", fontSize = 12.sp)
                    Text("Emergency Contact: ${member.emergencyContact.ifBlank { "N/A" }}", fontSize = 12.sp)
                    Text("Role: ${member.role}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    // Manage Status / Role Dialog
    editingMember?.let { member ->
        var newStatus by remember { mutableStateOf(member.status) }
        var newRole by remember { mutableStateOf(member.role) }
        var newDesignation by remember { mutableStateOf(member.designation) }

        AlertDialog(
            onDismissRequest = { editingMember = null },
            confirmButton = {
                Button(
                    onClick = {
                        if (newStatus != member.status) {
                            onUpdateStatus(member, newStatus)
                        }
                        if (newRole != member.role || newDesignation != member.designation) {
                            onUpdateRole(member, newRole, newDesignation)
                        }
                        editingMember = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SdufNavyPrimary)
                ) {
                    Text("Save Administrative Changes")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingMember = null }) {
                    Text("Cancel")
                }
            },
            title = { Text("Administrative Actions: ${member.fullName}") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Set Member Status:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        StatusChipButton("ACTIVE", newStatus == "ACTIVE") { newStatus = "ACTIVE" }
                        StatusChipButton("SUSPENDED", newStatus == "SUSPENDED") { newStatus = "SUSPENDED" }
                        StatusChipButton("INACTIVE", newStatus == "INACTIVE") { newStatus = "INACTIVE" }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Designation & Role Assignment:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    OutlinedTextField(
                        value = newDesignation,
                        onValueChange = { newDesignation = it },
                        label = { Text("Designation") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newRole,
                        onValueChange = { newRole = it },
                        label = { Text("Role (SUPER_ADMIN, SECRETARY, MEMBER)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedButton(
                        onClick = {
                            onDeleteMember(member)
                            editingMember = null
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Soft Delete / Archive Member")
                    }
                }
            }
        )
    }
}

@Composable
private fun CountPill(label: String, count: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count.toString(), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = color)
        Text(text = label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun StatusChipButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) SdufNavyPrimary else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
