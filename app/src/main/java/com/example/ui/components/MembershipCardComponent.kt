package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.local.entities.MemberEntity
import com.example.ui.theme.SdufGoldAccent
import com.example.ui.theme.SdufGoldDark
import com.example.ui.theme.SdufNavyDark
import com.example.ui.theme.SdufNavyLight
import com.example.ui.theme.SdufNavyPrimary

@Composable
fun OfficialMembershipCard(
    member: MemberEntity,
    onVerifyClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("membership_card_${member.memberId}")
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            SdufNavyDark,
                            SdufNavyPrimary,
                            Color(0xFF1E3A5F)
                        )
                    )
                )
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            SdufGoldAccent,
                            Color(0xFF856404),
                            SdufGoldAccent
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header: Logo & Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            modifier = Modifier.size(46.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_sduf_logo),
                                contentDescription = "SDUF Emblem",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.padding(2.dp).clip(CircleShape)
                            )
                        }
                        Column {
                            Text(
                                text = "STUDENTS' DEMOCRATIC",
                                color = SdufGoldAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "UNION FEDERATION",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    // Card Tag Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = SdufGoldAccent.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SdufGoldAccent)
                    ) {
                        Text(
                            text = "MEMBER PASS",
                            color = SdufGoldAccent,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 14.dp),
                    thickness = 1.dp,
                    color = Color.White.copy(alpha = 0.15f)
                )

                // Middle section: Photo, Member details, QR Matrix
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // Member details
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "NAME",
                            color = Color.White.copy(alpha = 0.6f),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = member.fullName,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column {
                                Text(
                                    text = "MEMBER ID",
                                    color = SdufGoldAccent,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = member.memberId,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Column {
                                Text(
                                    text = "DESIGNATION",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = member.designation,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column {
                                Text(
                                    text = "ORGANIZATIONAL UNIT",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = member.unitName,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            Column {
                                Text(
                                    text = "INSTITUTION",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = member.institution.ifBlank { "University" },
                                    color = Color.White.copy(alpha = 0.9f),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // QR Code matrix for official scanning & verification
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        StylizedQrMatrix(
                            seedString = member.memberId,
                            sizeDp = 88.dp,
                            matrixColor = SdufNavyDark
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "SCAN TO VERIFY",
                            color = SdufGoldAccent,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    thickness = 1.dp,
                    color = Color.White.copy(alpha = 0.15f)
                )

                // Footer: Verification Status, Date & Security seal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Verified Status",
                            tint = if (member.status == "ACTIVE") Color(0xFF10B981) else Color(0xFFF59E0B),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (member.status == "ACTIVE") "AUTHENTICATED ACTIVE" else member.status,
                            color = if (member.status == "ACTIVE") Color(0xFF34D399) else Color(0xFFFBBF24),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Text(
                        text = "Issued: ${member.appointmentDate.ifBlank { "Oct 2026" }}",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 10.sp
                    )

                    if (onVerifyClick != null) {
                        Surface(
                            onClick = onVerifyClick,
                            shape = RoundedCornerShape(6.dp),
                            color = Color.White.copy(alpha = 0.12f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Security,
                                    contentDescription = "Verify",
                                    tint = SdufGoldAccent,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "Verify",
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
