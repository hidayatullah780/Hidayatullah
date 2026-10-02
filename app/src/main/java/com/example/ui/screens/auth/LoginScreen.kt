package com.example.ui.screens.auth

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyPrimary
import com.example.ui.viewmodel.Screen

@Composable
fun LoginScreen(
    onLogin: (String, String, (Boolean, String) -> Unit) -> Unit,
    onQuickSwitch: (String) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    var identifier by remember { mutableStateOf("chairman") }
    var password by remember { mutableStateOf("password123") }
    var rememberMe by remember { mutableStateOf(true) }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("login_screen_lazy_column"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Logo & Header
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 4.dp,
                modifier = Modifier.size(72.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sduf_logo),
                    contentDescription = "SDUF Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(4.dp).clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "STUDENTS' DEMOCRATIC UNION FEDERATION",
                color = SdufNavyPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Member & Admin Authentication Portal",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        if (errorMessage != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2))
                ) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color(0xFFB91C1C),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Login Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    OutlinedTextField(
                        value = identifier,
                        onValueChange = { identifier = it },
                        label = { Text("Username / Email / Member ID") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth().testTag("login_identifier_input"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth().testTag("login_password_input"),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                modifier = Modifier.testTag("remember_me_checkbox")
                            )
                            Text(
                                text = "Remember me",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        TextButton(onClick = {
                            errorMessage = "Please contact Central Secretariat admin at central.secretariat@sduf.org to reset password."
                        }) {
                            Text(
                                text = "Forgot Password?",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (identifier.isNotBlank() && password.isNotBlank()) {
                                isLoading = true
                                errorMessage = null
                                onLogin(identifier, password) { success, msg ->
                                    isLoading = false
                                    if (!success) {
                                        errorMessage = msg
                                    }
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SdufNavyPrimary),
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("login_submit_button")
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Login,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(text = "Login to Portal", fontWeight = FontWeight.Bold)
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { onNavigate(Screen.MembershipApply) }) {
                            Text(text = "Join / Create Account", fontSize = 12.sp)
                        }
                        TextButton(onClick = { onNavigate(Screen.Contact) }) {
                            Text(text = "Contact Administrator", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Quick Role Demo Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "QUICK EVALUATION SWITCHER",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SdufNavyPrimary,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Instant 1-tap sign-in to test role-based access:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onQuickSwitch("CHAIRMAN") },
                            modifier = Modifier.weight(1f).testTag("quick_login_chairman"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Super Admin\n(Chairman)", fontSize = 11.sp, textAlign = TextAlign.Center)
                        }

                        OutlinedButton(
                            onClick = { onQuickSwitch("SECRETARY") },
                            modifier = Modifier.weight(1f).testTag("quick_login_secretary"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Admin\n(Sec Gen)", fontSize = 11.sp, textAlign = TextAlign.Center)
                        }

                        OutlinedButton(
                            onClick = { onQuickSwitch("MEMBER") },
                            modifier = Modifier.weight(1f).testTag("quick_login_member"),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Member\n(Tariq)", fontSize = 11.sp, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}
