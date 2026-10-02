package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.entities.AnnouncementEntity
import com.example.data.local.entities.AppointmentEntity
import com.example.data.local.entities.AuditLogEntity
import com.example.data.local.entities.DepartmentEntity
import com.example.data.local.entities.DocumentEntity
import com.example.data.local.entities.EventEntity
import com.example.data.local.entities.GalleryAlbumEntity
import com.example.data.local.entities.LeadershipEntity
import com.example.data.local.entities.MemberEntity
import com.example.data.local.entities.NotificationEntity
import com.example.data.local.entities.OrganizationalUnitEntity
import com.example.data.local.entities.SettingEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SdufRepository(private val database: AppDatabase) {

    val settings: Flow<Map<String, String>> = database.settingDao().getAllSettings().map { list ->
        list.associate { it.key to it.value }
    }

    val allMembers: Flow<List<MemberEntity>> = database.memberDao().getAllMembers()
    val pendingMembers: Flow<List<MemberEntity>> = database.memberDao().getMembersByStatus("PENDING")
    val activeMembers: Flow<List<MemberEntity>> = database.memberDao().getMembersByStatus("ACTIVE")
    val totalMembersCount: Flow<Int> = database.memberDao().countTotal()
    val pendingMembersCount: Flow<Int> = database.memberDao().countPending()

    val leadershipList: Flow<List<LeadershipEntity>> = database.leadershipDao().getAllLeadership()
    val appointments: Flow<List<AppointmentEntity>> = database.appointmentDao().getAllAppointments()
    val departments: Flow<List<DepartmentEntity>> = database.departmentDao().getAllDepartments()
    val units: Flow<List<OrganizationalUnitEntity>> = database.organizationalUnitDao().getAllUnits()
    val notifications: Flow<List<NotificationEntity>> = database.notificationDao().getAllNotifications()
    val publishedNotifications: Flow<List<NotificationEntity>> = database.notificationDao().getPublishedNotifications()
    val announcements: Flow<List<AnnouncementEntity>> = database.announcementDao().getAllAnnouncements()
    val events: Flow<List<EventEntity>> = database.eventDao().getAllEvents()
    val documents: Flow<List<DocumentEntity>> = database.documentDao().getAllDocuments()
    val albums: Flow<List<GalleryAlbumEntity>> = database.galleryAlbumDao().getAllAlbums()
    val auditLogs: Flow<List<AuditLogEntity>> = database.auditLogDao().getAllAuditLogs()

    private fun getCurrentFormattedTime(): String {
        return SimpleDateFormat("dd MMM yyyy — HH:mm", Locale.getDefault()).format(Date())
    }

    private suspend fun recordAudit(adminName: String, action: String, recordAffected: String, oldVal: String = "", newVal: String = "") {
        database.auditLogDao().insertLog(
            AuditLogEntity(
                adminName = adminName.ifBlank { "Administrator" },
                action = action,
                recordAffected = recordAffected,
                oldValue = oldVal,
                newValue = newVal,
                formattedDate = getCurrentFormattedTime()
            )
        )
    }

    suspend fun login(identifier: String, pass: String): MemberEntity? {
        val user = database.memberDao().findByIdentifier(identifier.trim()) ?: return null
        return if (user.password == pass) user else null
    }

    suspend fun verifyMemberId(memberId: String): MemberEntity? {
        return database.memberDao().getMemberByMemberId(memberId.trim().uppercase())
    }

    fun observeMember(memberId: String): Flow<MemberEntity?> {
        return database.memberDao().observeMemberByMemberId(memberId)
    }

    suspend fun registerMember(member: MemberEntity): Long {
        return database.memberDao().insertMember(member)
    }

    suspend fun approveMember(member: MemberEntity, adminName: String): String {
        val newIdNumber = (100000..999999).random()
        val generatedMemberId = "SDUF-2026-$newIdNumber"
        val updated = member.copy(
            status = "ACTIVE",
            memberId = generatedMemberId,
            appointmentDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
        )
        database.memberDao().updateMember(updated)
        recordAudit(
            adminName = adminName,
            action = "Approved Member Application",
            recordAffected = "${member.fullName} (${member.email})",
            oldVal = "Status: PENDING",
            newVal = "Status: ACTIVE, Member ID: $generatedMemberId"
        )
        return generatedMemberId
    }

    suspend fun updateMemberStatus(member: MemberEntity, newStatus: String, adminName: String) {
        val oldStatus = member.status
        val updated = member.copy(status = newStatus)
        database.memberDao().updateMember(updated)
        recordAudit(
            adminName = adminName,
            action = "Updated Member Status",
            recordAffected = "${member.fullName} (${member.memberId})",
            oldVal = "Status: $oldStatus",
            newVal = "Status: $newStatus"
        )
    }

    suspend fun updateMemberRole(member: MemberEntity, newRole: String, newDesignation: String, adminName: String) {
        val oldRole = "${member.role} (${member.designation})"
        val updated = member.copy(role = newRole, designation = newDesignation)
        database.memberDao().updateMember(updated)
        recordAudit(
            adminName = adminName,
            action = "Updated Member Role & Designation",
            recordAffected = "${member.fullName} (${member.memberId})",
            oldVal = oldRole,
            newVal = "$newRole ($newDesignation)"
        )
    }

    suspend fun updateMemberProfile(member: MemberEntity) {
        database.memberDao().updateMember(member)
    }

    suspend fun deleteMember(member: MemberEntity, adminName: String) {
        database.memberDao().deleteMember(member)
        recordAudit(
            adminName = adminName,
            action = "Deleted Member Record",
            recordAffected = "${member.fullName} (${member.memberId})"
        )
    }

    // Leadership CRUD
    suspend fun saveLeadership(leader: LeadershipEntity, adminName: String) {
        if (leader.id == 0L) {
            database.leadershipDao().insertLeadership(leader)
            recordAudit(adminName, "Created Leadership Entry", "${leader.name} as ${leader.position}")
        } else {
            database.leadershipDao().updateLeadership(leader)
            recordAudit(adminName, "Updated Leadership Entry", "${leader.name} as ${leader.position}")
        }
    }

    suspend fun deleteLeadership(leader: LeadershipEntity, adminName: String) {
        database.leadershipDao().deleteLeadership(leader)
        recordAudit(adminName, "Deleted Leadership Entry", "${leader.name} (${leader.position})")
    }

    // Appointment CRUD
    suspend fun saveAppointment(appointment: AppointmentEntity, adminName: String) {
        if (appointment.id == 0L) {
            database.appointmentDao().insertAppointment(appointment)
            recordAudit(adminName, "Created Appointment", "${appointment.personName} as ${appointment.designation} (${appointment.letterNumber})")
        } else {
            database.appointmentDao().updateAppointment(appointment)
            recordAudit(adminName, "Updated Appointment", "${appointment.personName} as ${appointment.designation}")
        }
    }

    suspend fun deleteAppointment(appointment: AppointmentEntity, adminName: String) {
        database.appointmentDao().deleteAppointment(appointment)
        recordAudit(adminName, "Deleted Appointment", "${appointment.personName} (${appointment.letterNumber})")
    }

    // Department CRUD
    suspend fun saveDepartment(dept: DepartmentEntity, adminName: String) {
        if (dept.id == 0L) {
            database.departmentDao().insertDepartment(dept)
            recordAudit(adminName, "Created Department", dept.name)
        } else {
            database.departmentDao().updateDepartment(dept)
            recordAudit(adminName, "Updated Department", dept.name)
        }
    }

    suspend fun deleteDepartment(dept: DepartmentEntity, adminName: String) {
        database.departmentDao().deleteDepartment(dept)
        recordAudit(adminName, "Deleted Department", dept.name)
    }

    // Unit CRUD
    suspend fun saveUnit(unit: OrganizationalUnitEntity, adminName: String) {
        if (unit.id == 0L) {
            database.organizationalUnitDao().insertUnit(unit)
            recordAudit(adminName, "Created Organizational Unit", "${unit.name} (${unit.type})")
        } else {
            database.organizationalUnitDao().updateUnit(unit)
            recordAudit(adminName, "Updated Organizational Unit", "${unit.name} (${unit.type})")
        }
    }

    suspend fun deleteUnit(unit: OrganizationalUnitEntity, adminName: String) {
        database.organizationalUnitDao().deleteUnit(unit)
        recordAudit(adminName, "Deleted Organizational Unit", unit.name)
    }

    // Notifications CRUD
    suspend fun saveNotification(notif: NotificationEntity, adminName: String) {
        if (notif.id == 0L) {
            database.notificationDao().insertNotification(notif)
            recordAudit(adminName, "Created Notification", "${notif.title} [${notif.status}]")
        } else {
            database.notificationDao().updateNotification(notif)
            recordAudit(adminName, "Updated Notification", "${notif.title} [${notif.status}]")
        }
    }

    suspend fun deleteNotification(notif: NotificationEntity, adminName: String) {
        database.notificationDao().deleteNotification(notif)
        recordAudit(adminName, "Deleted Notification", notif.title)
    }

    // Announcement CRUD
    suspend fun saveAnnouncement(announcement: AnnouncementEntity, adminName: String) {
        if (announcement.id == 0L) {
            database.announcementDao().insertAnnouncement(announcement)
            recordAudit(adminName, "Created Announcement", announcement.title)
        } else {
            database.announcementDao().updateAnnouncement(announcement)
            recordAudit(adminName, "Updated Announcement", announcement.title)
        }
    }

    suspend fun deleteAnnouncement(announcement: AnnouncementEntity, adminName: String) {
        database.announcementDao().deleteAnnouncement(announcement)
        recordAudit(adminName, "Deleted Announcement", announcement.title)
    }

    // Event CRUD
    suspend fun saveEvent(event: EventEntity, adminName: String) {
        if (event.id == 0L) {
            database.eventDao().insertEvent(event)
            recordAudit(adminName, "Created Event", "${event.title} (${event.date})")
        } else {
            database.eventDao().updateEvent(event)
            recordAudit(adminName, "Updated Event", "${event.title} (${event.date})")
        }
    }

    suspend fun deleteEvent(event: EventEntity, adminName: String) {
        database.eventDao().deleteEvent(event)
        recordAudit(adminName, "Deleted Event", event.title)
    }

    // Document CRUD
    suspend fun saveDocument(doc: DocumentEntity, adminName: String) {
        if (doc.id == 0L) {
            database.documentDao().insertDocument(doc)
            recordAudit(adminName, "Created Document", "${doc.title} ${doc.version}")
        } else {
            database.documentDao().updateDocument(doc)
            recordAudit(adminName, "Updated Document", "${doc.title} ${doc.version}")
        }
    }

    suspend fun deleteDocument(doc: DocumentEntity, adminName: String) {
        database.documentDao().deleteDocument(doc)
        recordAudit(adminName, "Deleted Document", "${doc.title} ${doc.version}")
    }

    // Gallery CRUD
    suspend fun saveAlbum(album: GalleryAlbumEntity, adminName: String) {
        if (album.id == 0L) {
            database.galleryAlbumDao().insertAlbum(album)
            recordAudit(adminName, "Created Gallery Album", album.title)
        } else {
            database.galleryAlbumDao().updateAlbum(album)
            recordAudit(adminName, "Updated Gallery Album", album.title)
        }
    }

    suspend fun deleteAlbum(album: GalleryAlbumEntity, adminName: String) {
        database.galleryAlbumDao().deleteAlbum(album)
        recordAudit(adminName, "Deleted Gallery Album", album.title)
    }

    // Settings / Branding update
    suspend fun updateSettings(entries: Map<String, String>, adminName: String) {
        val list = entries.map { SettingEntity(it.key, it.value) }
        database.settingDao().insertSettings(list)
        recordAudit(adminName, "Updated Organization Branding & Homepage Settings", "${entries.size} settings updated")
    }
}
