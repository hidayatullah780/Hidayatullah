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
import com.example.data.local.entities.AnnouncementEntity
import com.example.data.local.entities.EventEntity
import com.example.data.local.entities.NotificationEntity
import com.example.ui.components.StatusBadge
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AdminCommunicationsScreen(
    notifications: List<NotificationEntity>,
    announcements: List<AnnouncementEntity>,
    events: List<EventEntity>,
    onSaveNotification: (NotificationEntity) -> Unit,
    onDeleteNotification: (NotificationEntity) -> Unit,
    onSaveAnnouncement: (AnnouncementEntity) -> Unit,
    onDeleteAnnouncement: (AnnouncementEntity) -> Unit,
    onSaveEvent: (EventEntity) -> Unit,
    onDeleteEvent: (EventEntity) -> Unit,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabs = listOf("Notifications", "Announcements", "Events")

    var editingNotif by remember { mutableStateOf<NotificationEntity?>(null) }
    var editingAnnounce by remember { mutableStateOf<AnnouncementEntity?>(null) }
    var editingEvent by remember { mutableStateOf<EventEntity?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_communications_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SdufNavyPrimary)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "OFFICIAL COMMUNICATIONS & DISPATCH",
                    color = SdufGoldAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Notices, Circulars & Event Assemblies",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        when (selectedTab) {
                            0 -> editingNotif = NotificationEntity(
                                id = 0L,
                                title = "",
                                message = "",
                                date = "02 Oct 2026",
                                category = "Official Notification",
                                attachmentName = "Notification_Order_2026.pdf",
                                targetAudience = "All Members",
                                status = "PUBLISHED"
                            )
                            1 -> editingAnnounce = AnnouncementEntity(
                                id = 0L,
                                title = "",
                                content = "",
                                date = "02 Oct 2026",
                                badge = "Important",
                                isPinned = false
                            )
                            2 -> editingEvent = EventEntity(
                                id = 0L,
                                title = "",
                                date = "05 Oct 2026",
                                time = "10:30 AM PST",
                                venue = "Central Auditorium",
                                description = "",
                                status = "UPCOMING",
                                category = "Ceremony"
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SdufGoldAccent),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = SdufNavyPrimary)
                    Spacer(modifier = Modifier.padding(start = 6.dp))
                    Text(
                        text = when (selectedTab) {
                            0 -> "Create Notification"
                            1 -> "New Announcement"
                            else -> "Schedule Event"
                        },
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

        when (selectedTab) {
            0 -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(notifications) { notif ->
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
                                        text = notif.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    StatusBadge(status = notif.status)
                                }
                                Text(
                                    text = "${notif.category} • Date: ${notif.date} • Audience: ${notif.targetAudience}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = notif.message,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(onClick = { editingNotif = notif }) {
                                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                                    }
                                    IconButton(onClick = { onDeleteNotification(notif) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(announcements) { ann ->
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
                                        text = ann.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    StatusBadge(status = ann.badge)
                                }
                                Text(
                                    text = "Date: ${ann.date} • Pinned: ${ann.isPinned}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = ann.content,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(onClick = { editingAnnounce = ann }) {
                                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                                    }
                                    IconButton(onClick = { onDeleteAnnouncement(ann) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            2 -> {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(events) { evt ->
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
                                        text = evt.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    StatusBadge(status = evt.status)
                                }
                                Text(
                                    text = "📅 ${evt.date} • ⏰ ${evt.time} • 📍 ${evt.venue}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = evt.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    IconButton(onClick = { editingEvent = evt }) {
                                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                                    }
                                    IconButton(onClick = { onDeleteEvent(evt) }) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Notification Dialog
    editingNotif?.let { current ->
        var title by remember { mutableStateOf(current.title) }
        var message by remember { mutableStateOf(current.message) }
        var date by remember { mutableStateOf(current.date) }
        var category by remember { mutableStateOf(current.category) }
        var attachment by remember { mutableStateOf(current.attachmentName) }
        var status by remember { mutableStateOf(current.status) }

        AlertDialog(
            onDismissRequest = { editingNotif = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            title = title.trim(),
                            message = message.trim(),
                            date = date.trim(),
                            category = category.trim(),
                            attachmentName = attachment.trim(),
                            status = status
                        )
                        onSaveNotification(updated)
                        editingNotif = null
                    },
                    enabled = title.isNotBlank() && message.isNotBlank()
                ) {
                    Text("Save Notification")
                }
            },
            dismissButton = { TextButton(onClick = { editingNotif = null }) { Text("Cancel") } },
            title = { Text(if (current.id == 0L) "New Notification" else "Edit Notification") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category (Official, Circular, Directive)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = attachment,
                        onValueChange = { attachment = it },
                        label = { Text("Attachment Name (e.g. Directive_01.pdf)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Notification Message") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        StatusBtn("PUBLISHED", status == "PUBLISHED") { status = "PUBLISHED" }
                        StatusBtn("DRAFT", status == "DRAFT") { status = "DRAFT" }
                        StatusBtn("ARCHIVED", status == "ARCHIVED") { status = "ARCHIVED" }
                    }
                }
            }
        )
    }

    // Announcement Dialog
    editingAnnounce?.let { current ->
        var title by remember { mutableStateOf(current.title) }
        var content by remember { mutableStateOf(current.content) }
        var date by remember { mutableStateOf(current.date) }
        var badge by remember { mutableStateOf(current.badge) }

        AlertDialog(
            onDismissRequest = { editingAnnounce = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            title = title.trim(),
                            content = content.trim(),
                            date = date.trim(),
                            badge = badge.trim()
                        )
                        onSaveAnnouncement(updated)
                        editingAnnounce = null
                    },
                    enabled = title.isNotBlank() && content.isNotBlank()
                ) {
                    Text("Save Announcement")
                }
            },
            dismissButton = { TextButton(onClick = { editingAnnounce = null }) { Text("Cancel") } },
            title = { Text(if (current.id == 0L) "New Announcement" else "Edit Announcement") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Title *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = badge,
                        onValueChange = { badge = it },
                        label = { Text("Badge (e.g. Major Event, Urgent)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Announcement Details") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }

    // Event Dialog
    editingEvent?.let { current ->
        var title by remember { mutableStateOf(current.title) }
        var date by remember { mutableStateOf(current.date) }
        var time by remember { mutableStateOf(current.time) }
        var venue by remember { mutableStateOf(current.venue) }
        var desc by remember { mutableStateOf(current.description) }
        var status by remember { mutableStateOf(current.status) }

        AlertDialog(
            onDismissRequest = { editingEvent = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            title = title.trim(),
                            date = date.trim(),
                            time = time.trim(),
                            venue = venue.trim(),
                            description = desc.trim(),
                            status = status
                        )
                        onSaveEvent(updated)
                        editingEvent = null
                    },
                    enabled = title.isNotBlank() && venue.isNotBlank()
                ) {
                    Text("Save Event")
                }
            },
            dismissButton = { TextButton(onClick = { editingEvent = null }) { Text("Cancel") } },
            title = { Text(if (current.id == 0L) "Schedule Event" else "Edit Event") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Event Title *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = date,
                            onValueChange = { date = it },
                            label = { Text("Date") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = time,
                            onValueChange = { time = it },
                            label = { Text("Time") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = venue,
                        onValueChange = { venue = it },
                        label = { Text("Venue / Location *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        StatusBtn("UPCOMING", status == "UPCOMING") { status = "UPCOMING" }
                        StatusBtn("COMPLETED", status == "COMPLETED") { status = "COMPLETED" }
                        StatusBtn("CANCELLED", status == "CANCELLED") { status = "CANCELLED" }
                    }
                }
            }
        )
    }
}

@Composable
private fun StatusBtn(label: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        color = if (selected) SdufNavyPrimary else MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
