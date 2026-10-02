package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.ui.theme.SdufNavyPrimary
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdufTopBar(
    currentScreen: Screen,
    currentUser: MemberEntity?,
    onOpenDrawer: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.testTag("sduf_top_app_bar"),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SdufNavyPrimary,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(
                onClick = onOpenDrawer,
                modifier = Modifier.testTag("open_drawer_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Navigation Menu",
                    tint = Color.White
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    modifier = Modifier.size(32.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_sduf_logo),
                        contentDescription = "SDUF Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.padding(1.dp).clip(CircleShape)
                    )
                }
                Column {
                    Text(
                        text = currentScreen.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "SDUF • Student Democratic Union Federation",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 10.sp,
                        color = SdufGoldAccent
                    )
                }
            }
        },
        actions = {
            // Global Search Button
            IconButton(
                onClick = onSearchClick,
                modifier = Modifier.testTag("search_action_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search SDUF Records",
                    tint = Color.White
                )
            }

            // Role / Persona Pill
            Surface(
                onClick = onOpenDrawer,
                shape = RoundedCornerShape(16.dp),
                color = SdufGoldAccent.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, SdufGoldAccent),
                modifier = Modifier.padding(end = 8.dp).testTag("topbar_persona_badge")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SwitchAccount,
                        contentDescription = null,
                        tint = SdufGoldAccent,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = when (currentUser?.role) {
                            "SUPER_ADMIN" -> "Chairman"
                            "GENERAL_SECRETARY" -> "Sec Gen"
                            "PRESIDENT" -> "VP"
                            "SECRETARY" -> "Secretary"
                            "UNIT_ADMIN" -> "Unit Admin"
                            "MEMBER" -> "Member"
                            else -> "Public"
                        },
                        color = SdufGoldAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}
