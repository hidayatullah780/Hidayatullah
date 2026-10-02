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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import com.example.data.local.entities.DepartmentEntity
import com.example.data.local.entities.OrganizationalUnitEntity
import com.example.ui.components.StatusBadge
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AdminStructureScreen(
    departments: List<DepartmentEntity>,
    units: List<OrganizationalUnitEntity>,
    onSaveDepartment: (DepartmentEntity) -> Unit,
    onDeleteDepartment: (DepartmentEntity) -> Unit,
    onSaveUnit: (OrganizationalUnitEntity) -> Unit,
    onDeleteUnit: (OrganizationalUnitEntity) -> Unit,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabs = listOf("Departments", "Organizational Units")

    var editingDept by remember { mutableStateOf<DepartmentEntity?>(null) }
    var editingUnit by remember { mutableStateOf<OrganizationalUnitEntity?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_structure_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SdufNavyPrimary)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "ORGANIZATIONAL GOVERNANCE",
                    color = SdufGoldAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Departments & Geographic Units",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (selectedTab == 0) {
                            editingDept = DepartmentEntity(
                                id = 0L,
                                name = "",
                                headName = "",
                                deputyName = "",
                                description = "",
                                objectives = "",
                                memberCount = 5,
                                status = "ACTIVE"
                            )
                        } else {
                            editingUnit = OrganizationalUnitEntity(
                                id = 0L,
                                name = "",
                                type = "DISTRICT",
                                parentUnitName = "Sindh Provincial Federation",
                                headName = "",
                                deputyName = "",
                                contactPhone = "",
                                description = "",
                                status = "ACTIVE"
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SdufGoldAccent),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = SdufNavyPrimary)
                    Spacer(modifier = Modifier.padding(start = 6.dp))
                    Text(
                        text = if (selectedTab == 0) "Create Department" else "Add Organizational Unit",
                        color = SdufNavyPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
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
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(departments) { dept ->
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
                                Text(
                                    text = dept.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                StatusBadge(status = dept.status)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Head: ${dept.headName} • Deputy: ${dept.deputyName} (${dept.memberCount} members)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = dept.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { editingDept = dept }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { onDeleteDepartment(dept) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(units) { unit ->
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
                                Text(
                                    text = "${unit.name} (${unit.type})",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                StatusBadge(status = unit.status)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Parent: ${unit.parentUnitName.ifBlank { "Top Level" }} • Head: ${unit.headName} • Contact: ${unit.contactPhone}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { editingUnit = unit }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { onDeleteUnit(unit) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Department Dialog
    editingDept?.let { current ->
        var name by remember { mutableStateOf(current.name) }
        var headName by remember { mutableStateOf(current.headName) }
        var deputyName by remember { mutableStateOf(current.deputyName) }
        var desc by remember { mutableStateOf(current.description) }
        var objectives by remember { mutableStateOf(current.objectives) }
        var count by remember { mutableIntStateOf(current.memberCount) }

        AlertDialog(
            onDismissRequest = { editingDept = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            name = name.trim(),
                            headName = headName.trim(),
                            deputyName = deputyName.trim(),
                            description = desc.trim(),
                            objectives = objectives.trim(),
                            memberCount = count
                        )
                        onSaveDepartment(updated)
                        editingDept = null
                    },
                    enabled = name.isNotBlank() && headName.isNotBlank()
                ) {
                    Text("Save Department")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingDept = null }) { Text("Cancel") }
            },
            title = { Text(if (current.id == 0L) "Add Department" else "Edit Department") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Department Name *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = headName,
                        onValueChange = { headName = it },
                        label = { Text("Head / Secretary *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = deputyName,
                        onValueChange = { deputyName = it },
                        label = { Text("Deputy Head") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = objectives,
                        onValueChange = { objectives = it },
                        label = { Text("Objectives") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }

    // Unit Dialog
    editingUnit?.let { current ->
        var name by remember { mutableStateOf(current.name) }
        var type by remember { mutableStateOf(current.type) }
        var parentUnitName by remember { mutableStateOf(current.parentUnitName) }
        var headName by remember { mutableStateOf(current.headName) }
        var deputyName by remember { mutableStateOf(current.deputyName) }
        var contactPhone by remember { mutableStateOf(current.contactPhone) }
        var desc by remember { mutableStateOf(current.description) }

        AlertDialog(
            onDismissRequest = { editingUnit = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            name = name.trim(),
                            type = type.trim(),
                            parentUnitName = parentUnitName.trim(),
                            headName = headName.trim(),
                            deputyName = deputyName.trim(),
                            contactPhone = contactPhone.trim(),
                            description = desc.trim()
                        )
                        onSaveUnit(updated)
                        editingUnit = null
                    },
                    enabled = name.isNotBlank() && headName.isNotBlank()
                ) {
                    Text("Save Unit")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingUnit = null }) { Text("Cancel") }
            },
            title = { Text(if (current.id == 0L) "Add Organizational Unit" else "Edit Organizational Unit") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Unit Name *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = type,
                        onValueChange = { type = it },
                        label = { Text("Unit Type (PROVINCE, DISTRICT, TALUKA, LOCAL)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = parentUnitName,
                        onValueChange = { parentUnitName = it },
                        label = { Text("Parent Unit") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = headName,
                        onValueChange = { headName = it },
                        label = { Text("Unit Head / President *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = deputyName,
                        onValueChange = { deputyName = it },
                        label = { Text("Deputy") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = contactPhone,
                        onValueChange = { contactPhone = it },
                        label = { Text("Contact Phone") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}
