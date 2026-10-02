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
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
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
import com.example.data.local.entities.DocumentEntity
import com.example.data.local.entities.GalleryAlbumEntity
import com.example.ui.components.StatusBadge
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun AdminDocumentsAndGalleryScreen(
    documents: List<DocumentEntity>,
    albums: List<GalleryAlbumEntity>,
    onSaveDocument: (DocumentEntity) -> Unit,
    onDeleteDocument: (DocumentEntity) -> Unit,
    onSaveAlbum: (GalleryAlbumEntity) -> Unit,
    onDeleteAlbum: (GalleryAlbumEntity) -> Unit,
    initialTab: Int = 0,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(initialTab) }
    val tabs = listOf("Document Archive & Versions", "Photo Gallery")

    var editingDoc by remember { mutableStateOf<DocumentEntity?>(null) }
    var editingAlbum by remember { mutableStateOf<GalleryAlbumEntity?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("admin_docs_gallery_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = SdufNavyPrimary)
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "RECORDS & MEDIA MANAGEMENT",
                    color = SdufGoldAccent,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Document Versions & Photo Albums",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (selectedTab == 0) {
                            editingDoc = DocumentEntity(
                                id = 0L,
                                title = "",
                                documentNumber = "SDUF/DOC/2026-${(10..99).random()}",
                                date = "02 Oct 2026",
                                category = "Constitution",
                                description = "",
                                version = "v2.1",
                                isCurrentVersion = true,
                                visibility = "PUBLIC",
                                status = "ACTIVE"
                            )
                        } else {
                            editingAlbum = GalleryAlbumEntity(
                                id = 0L,
                                title = "",
                                date = "02 Oct 2026",
                                description = "",
                                photoCount = 6,
                                tag = "Ceremony"
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SdufGoldAccent),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = SdufNavyPrimary)
                    Spacer(modifier = Modifier.padding(start = 6.dp))
                    Text(
                        text = if (selectedTab == 0) "Upload New Document / Version" else "Create Photo Album",
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
                items(documents) { doc ->
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = doc.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = if (doc.isCurrentVersion) Color(0xFFDCFCE7) else Color(0xFFF1F5F9)
                                    ) {
                                        Text(
                                            text = if (doc.isCurrentVersion) "${doc.version} (Active)" else "${doc.version} (Archived)",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (doc.isCurrentVersion) Color(0xFF15803D) else Color(0xFF64748B),
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                StatusBadge(status = doc.status)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Doc No: ${doc.documentNumber} • Category: ${doc.category} • Date: ${doc.date}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = doc.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { editingDoc = doc }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { onDeleteDocument(doc) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(albums) { album ->
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
                                    text = album.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                                ) {
                                    Text(
                                        text = "${album.photoCount} Photos",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tag: ${album.tag} • Date: ${album.date}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = album.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { editingAlbum = album }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { onDeleteAlbum(album) }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Document Dialog with Version Control
    editingDoc?.let { current ->
        var title by remember { mutableStateOf(current.title) }
        var docNum by remember { mutableStateOf(current.documentNumber) }
        var category by remember { mutableStateOf(current.category) }
        var date by remember { mutableStateOf(current.date) }
        var version by remember { mutableStateOf(current.version) }
        var isCurrent by remember { mutableStateOf(current.isCurrentVersion) }
        var desc by remember { mutableStateOf(current.description) }

        AlertDialog(
            onDismissRequest = { editingDoc = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            title = title.trim(),
                            documentNumber = docNum.trim(),
                            category = category.trim(),
                            date = date.trim(),
                            version = version.trim(),
                            isCurrentVersion = isCurrent,
                            description = desc.trim()
                        )
                        onSaveDocument(updated)
                        editingDoc = null
                    },
                    enabled = title.isNotBlank()
                ) {
                    Text("Save Document & Version")
                }
            },
            dismissButton = { TextButton(onClick = { editingDoc = null }) { Text("Cancel") } },
            title = { Text(if (current.id == 0L) "New Document Version" else "Edit Document") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Document Title *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = version,
                            onValueChange = { version = it },
                            label = { Text("Version (e.g. v2.0)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = category,
                            onValueChange = { category = it },
                            label = { Text("Category") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = docNum,
                        onValueChange = { docNum = it },
                        label = { Text("Document Ref No.") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Description & Bylaws Overview") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isCurrent,
                            onCheckedChange = { isCurrent = it }
                        )
                        Text("Mark as Current Active Version", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        )
    }

    // Gallery Dialog
    editingAlbum?.let { current ->
        var title by remember { mutableStateOf(current.title) }
        var date by remember { mutableStateOf(current.date) }
        var desc by remember { mutableStateOf(current.description) }
        var tag by remember { mutableStateOf(current.tag) }
        var count by remember { mutableIntStateOf(current.photoCount) }

        AlertDialog(
            onDismissRequest = { editingAlbum = null },
            confirmButton = {
                Button(
                    onClick = {
                        val updated = current.copy(
                            title = title.trim(),
                            date = date.trim(),
                            description = desc.trim(),
                            tag = tag.trim(),
                            photoCount = count
                        )
                        onSaveAlbum(updated)
                        editingAlbum = null
                    },
                    enabled = title.isNotBlank()
                ) {
                    Text("Save Album")
                }
            },
            dismissButton = { TextButton(onClick = { editingAlbum = null }) { Text("Cancel") } },
            title = { Text(if (current.id == 0L) "New Album" else "Edit Album") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Album Title *") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = tag,
                            onValueChange = { tag = it },
                            label = { Text("Tag (Ceremony, Rally)") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = count.toString(),
                            onValueChange = { count = it.toIntOrNull() ?: 1 },
                            label = { Text("Photo Count") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Description & Activity Context") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}
