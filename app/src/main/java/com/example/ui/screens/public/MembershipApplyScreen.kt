package com.example.ui.screens.public

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HowToReg
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufNavyPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MembershipApplyScreen(
    onSubmit: (
        fullName: String,
        fatherName: String,
        dob: String,
        gender: String,
        phone: String,
        email: String,
        district: String,
        taluka: String,
        uc: String,
        institution: String,
        degreeClass: String,
        address: String,
        category: String,
        emergencyContact: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var fullName by remember { mutableStateOf("") }
    var fatherName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("2004-01-15") }
    var gender by remember { mutableStateOf("Male") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("Tando Allahyar") }
    var taluka by remember { mutableStateOf("Chamber") }
    var uc by remember { mutableStateOf("Chamber UC-01") }
    var institution by remember { mutableStateOf("") }
    var degreeClass by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Student") }
    var emergencyContact by remember { mutableStateOf("") }

    var submittedMessage by remember { mutableStateOf<String?>(null) }
    var genderExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val genders = listOf("Male", "Female", "Prefer not to say")
    val categories = listOf("Student", "Youth Wing", "Associate", "Council Delegate")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("membership_apply_lazy_column"),
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
                        text = "STUDENTS' DEMOCRATIC UNION FEDERATION",
                        color = SdufGoldAccent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Official Membership Enrollment",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Complete the official enrollment form below. Upon submission, your record will be registered under 'Pending Approval' for Central Secretariat verification.",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        if (submittedMessage != null) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDCFCE7))
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Success",
                            tint = Color(0xFF15803D),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "APPLICATION SUBMITTED SUCCESSFULLY",
                            color = Color(0xFF15803D),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = submittedMessage ?: "",
                            color = Color(0xFF166534),
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "1. Personal Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SdufNavyPrimary
                    )

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name *") },
                        modifier = Modifier.fillMaxWidth().testTag("input_fullname"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = fatherName,
                        onValueChange = { fatherName = it },
                        label = { Text("Father / Guardian Name *") },
                        modifier = Modifier.fillMaxWidth().testTag("input_fathername"),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = dob,
                            onValueChange = { dob = it },
                            label = { Text("Date of Birth") },
                            modifier = Modifier.weight(1f).testTag("input_dob"),
                            singleLine = true
                        )

                        ExposedDropdownMenuBox(
                            expanded = genderExpanded,
                            onExpandedChange = { genderExpanded = !genderExpanded },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = gender,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Gender") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = genderExpanded,
                                onDismissRequest = { genderExpanded = false }
                            ) {
                                genders.forEach { g ->
                                    DropdownMenuItem(
                                        text = { Text(g) },
                                        onClick = {
                                            gender = g
                                            genderExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            label = { Text("Phone Number *") },
                            modifier = Modifier.weight(1f).testTag("input_phone"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email Address *") },
                            modifier = Modifier.weight(1f).testTag("input_email"),
                            singleLine = true
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Text(
                        text = "2. Academic & Geographic Affiliation",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = SdufNavyPrimary
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = district,
                            onValueChange = { district = it },
                            label = { Text("District *") },
                            modifier = Modifier.weight(1f).testTag("input_district"),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = taluka,
                            onValueChange = { taluka = it },
                            label = { Text("Taluka / Tehsil *") },
                            modifier = Modifier.weight(1f).testTag("input_taluka"),
                            singleLine = true
                        )
                    }

                    OutlinedTextField(
                        value = uc,
                        onValueChange = { uc = it },
                        label = { Text("Union Council / Local Ward") },
                        modifier = Modifier.fillMaxWidth().testTag("input_uc"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = institution,
                        onValueChange = { institution = it },
                        label = { Text("College / University / Institution *") },
                        modifier = Modifier.fillMaxWidth().testTag("input_institution"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = degreeClass,
                        onValueChange = { degreeClass = it },
                        label = { Text("Class / Degree Program *") },
                        modifier = Modifier.fillMaxWidth().testTag("input_degree"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Permanent / Campus Address") },
                        modifier = Modifier.fillMaxWidth().testTag("input_address")
                    )

                    ExposedDropdownMenuBox(
                        expanded = categoryExpanded,
                        onExpandedChange = { categoryExpanded = !categoryExpanded },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Membership Category") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = categoryExpanded,
                            onDismissRequest = { categoryExpanded = false }
                        ) {
                            categories.forEach { c ->
                                DropdownMenuItem(
                                    text = { Text(c) },
                                    onClick = {
                                        category = c
                                        categoryExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = emergencyContact,
                        onValueChange = { emergencyContact = it },
                        label = { Text("Emergency Contact (Optional)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (fullName.isNotBlank() && phone.isNotBlank() && email.isNotBlank()) {
                                onSubmit(
                                    fullName,
                                    fatherName,
                                    dob,
                                    gender,
                                    phone,
                                    email,
                                    district,
                                    taluka,
                                    uc,
                                    institution.ifBlank { "University" },
                                    degreeClass.ifBlank { "Student" },
                                    address,
                                    category,
                                    emergencyContact
                                )
                                submittedMessage = "Your membership application for $fullName has been recorded. Status: Pending Approval. The Secretariat will inspect and grant your official SDUF Member ID."
                            }
                        },
                        enabled = fullName.isNotBlank() && phone.isNotBlank() && email.isNotBlank(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SdufNavyPrimary),
                        modifier = Modifier.fillMaxWidth().height(50.dp).testTag("submit_membership_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.HowToReg,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Submit Application (Pending Approval)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
