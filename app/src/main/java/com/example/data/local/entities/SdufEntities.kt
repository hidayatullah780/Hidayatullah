package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingEntity(
    @PrimaryKey val key: String,
    val value: String
)

@Entity(tableName = "members")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val memberId: String, // e.g. SDUF-2026-000001
    val username: String,
    val email: String,
    val password: String,
    val fullName: String,
    val fatherName: String,
    val dob: String,
    val gender: String,
    val phone: String,
    val district: String,
    val taluka: String,
    val unionCouncil: String,
    val institution: String,
    val degreeClass: String,
    val address: String,
    val membershipCategory: String, // General, Student, Associate, Council
    val emergencyContact: String = "",
    val status: String, // PENDING, ACTIVE, SUSPENDED, INACTIVE
    val role: String, // SUPER_ADMIN, PRESIDENT, GENERAL_SECRETARY, SECRETARY, UNIT_ADMIN, MEMBER
    val designation: String,
    val unitName: String,
    val appointmentDate: String,
    val photoAvatar: String = "student_avatar_1",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "leadership")
data class LeadershipEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val position: String,
    val department: String,
    val bio: String,
    val appointmentDate: String,
    val displayOrder: Int,
    val status: String = "ACTIVE", // ACTIVE, EMERITUS, ARCHIVED
    val avatarTag: String = "leader_1"
)

@Entity(tableName = "appointments")
data class AppointmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val personName: String,
    val memberId: String,
    val designation: String,
    val appointmentDate: String,
    val letterNumber: String,
    val authority: String,
    val term: String,
    val status: String = "ACTIVE", // ACTIVE, COMPLETED, REVOKED
    val documentRef: String = "Official Letter"
)

@Entity(tableName = "departments")
data class DepartmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val headName: String,
    val deputyName: String,
    val description: String,
    val objectives: String,
    val memberCount: Int = 0,
    val status: String = "ACTIVE"
)

@Entity(tableName = "organizational_units")
data class OrganizationalUnitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: String, // PROVINCE, DISTRICT, TALUKA, LOCAL_UNIT
    val parentUnitName: String = "",
    val headName: String,
    val deputyName: String,
    val contactPhone: String,
    val description: String,
    val status: String = "ACTIVE"
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val message: String,
    val date: String,
    val category: String, // Official Notification, Circular, Directive, Press
    val attachmentName: String = "",
    val targetAudience: String = "All Members",
    val status: String = "PUBLISHED" // DRAFT, SCHEDULED, PUBLISHED, ARCHIVED
)

@Entity(tableName = "announcements")
data class AnnouncementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val date: String,
    val badge: String = "Important",
    val isPinned: Boolean = false,
    val status: String = "ACTIVE"
)

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val venue: String,
    val description: String,
    val status: String = "UPCOMING", // UPCOMING, COMPLETED, CANCELLED
    val category: String = "Official"
)

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val documentNumber: String,
    val date: String,
    val category: String, // Constitution, Notifications, Appointment Letters, Oaths, Circulars, Policies
    val description: String,
    val version: String, // v1.0, v1.1, v2.0
    val isCurrentVersion: Boolean = true,
    val visibility: String = "PUBLIC", // PUBLIC, MEMBERS_ONLY, ADMIN_ONLY
    val status: String = "ACTIVE" // ACTIVE, ARCHIVED
)

@Entity(tableName = "gallery_albums")
data class GalleryAlbumEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val date: String,
    val description: String,
    val photoCount: Int = 1,
    val tag: String = "Event"
)

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val adminName: String,
    val action: String,
    val recordAffected: String,
    val oldValue: String = "",
    val newValue: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val formattedDate: String
)
