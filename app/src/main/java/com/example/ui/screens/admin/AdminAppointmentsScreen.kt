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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.AppointmentEntity
import com.example.ui.components.StatusBadge
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AdminAppointmentsScreen(
    appointments: List<AppointmentEntity>,
    onSaveAppointment: (AppointmentEntity) -> Unit,
    onDeleteAppointment: (AppointmentEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var editingAppt by remember { mutableStateOf<AppointmentEntity?>(null) }
    var isNew by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_appointments_lazy_column"),
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
                        text = "CONSTITUTIONAL APPOINTMENTS",
                        color = SdufGoldAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Appointment Records & Letters",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Generate and track official appointment letters, authorizing bodies, and tenure limits for executives.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            isNew = true
                            editingAppt = AppointmentEntity(
                                id = 0L,
                                personName = "",
                                memberId = "SDUF-2026-000000",
                                designation = "",
                                appointmentDate = "02 Oct 2026",
                                letterNumber = "SDUF/APP/2026-${(10..99).random()}",
                                authority = "Founder & Chairman Hidayatullah",
                                term = "2026 - 2028",
                                status = "ACTIVE"
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = SdufGoldAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("add_appointment_button")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = SdufNavyPrimary)
                        Spacer(modifier = Modifier.padding(start = 6.dp))
                        Text("New Appointment", color = SdufNavyPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        items(appointments) { appt ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("appointment_card_${appt.id}"),
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
                            Text(
                                text = appt.personName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "ID: ${appt.memberId}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                        StatusBadge(status = appt.status)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Designation: ${appt.designation}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Letter No: ${appt.letterNumber} • Authority: ${appt.authority}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Tenure: ${appt.term} • Date: ${appt.appointmentDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            isNew = false
                            editingAppt = appt
                        }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                        IconButton(onClick = { onDeleteAppointment(appt) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }

    editingAppt?.let { current ->
        var personName by remember { mutableStateOf(current.personName) }
        var memberId by remember { mutableStateOf(current.memberId) }
        var designation by remember { mutableStateOf(current.designation) }
        var appointmentDate by remember { mutableStateOf(current.appointmentDate) }
        var letterNumber by remember { mutableStateOf(current.letterNumber) }
        var authority by remember { mutableStateOf(current.authority) }
        var term by remember { mutableStateOf(current.term) }
        var status by remember { mutableStateOf(current.status) }

        AlertDialog(
            onDismissRequest = { editingAppt = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            personName = personName.trim(),
                            memberId = memberId.trim(),
                            designation = designation.trim(),
                            appointmentDate = appointmentDate.trim(),
                            letterNumber = letterNumber.trim(),
                            authority = authority.trim(),
                            term = term.trim(),
                            status = status
                        )
                        onSaveAppointment(updated)
                        editingAppt = null
                    },
                    enabled = personName.isNotBlank() && designation.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = SdufNavyPrimary)
                ) {
                    Text("Save Appointment")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingAppt = null }) { Text("Cancel") }
            },
            title = { Text(if (isNew) "Create Appointment" else "Edit Appointment") },
            text = {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        OutlinedTextField(
                            value = personName,
                            onValueChange = { personName = it },
                            label = { Text("Person Name *") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = memberId,
                            onValueChange = { memberId = it },
                            label = { Text("Member ID") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = designation,
                            onValueChange = { designation = it },
                            label = { Text("Designation *") },
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
                            value = letterNumber,
                            onValueChange = { letterNumber = it },
                            label = { Text("Appointment Letter No.") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = authority,
                            onValueChange = { authority = it },
                            label = { Text("Issuing Authority") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    item {
                        OutlinedTextField(
                            value = term,
                            onValueChange = { term = it },
                            label = { Text("Tenure / Term (e.g. 2026 - 2028)") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        )
    }
}
