package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

@Dao
interface SettingDao {
    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<SettingEntity>>

    @Query("SELECT * FROM settings WHERE `key` = :key LIMIT 1")
    suspend fun getSettingByKey(key: String): SettingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SettingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: List<SettingEntity>)
}

@Dao
interface MemberDao {
    @Query("SELECT * FROM members ORDER BY id DESC")
    fun getAllMembers(): Flow<List<MemberEntity>>

    @Query("SELECT * FROM members WHERE status = :status ORDER BY id DESC")
    fun getMembersByStatus(status: String): Flow<List<MemberEntity>>

    @Query("SELECT * FROM members WHERE id = :id LIMIT 1")
    fun getMemberById(id: Long): Flow<MemberEntity?>

    @Query("SELECT * FROM members WHERE memberId = :memberId LIMIT 1")
    suspend fun getMemberByMemberId(memberId: String): MemberEntity?

    @Query("SELECT * FROM members WHERE memberId = :memberId LIMIT 1")
    fun observeMemberByMemberId(memberId: String): Flow<MemberEntity?>

    @Query("SELECT * FROM members WHERE username = :query OR email = :query OR memberId = :query LIMIT 1")
    suspend fun findByIdentifier(query: String): MemberEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMember(member: MemberEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMembers(members: List<MemberEntity>)

    @Update
    suspend fun updateMember(member: MemberEntity)

    @Delete
    suspend fun deleteMember(member: MemberEntity)

    @Query("SELECT COUNT(*) FROM members")
    fun countTotal(): Flow<Int>

    @Query("SELECT COUNT(*) FROM members WHERE status = 'PENDING'")
    fun countPending(): Flow<Int>
}

@Dao
interface LeadershipDao {
    @Query("SELECT * FROM leadership ORDER BY displayOrder ASC, id ASC")
    fun getAllLeadership(): Flow<List<LeadershipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeadership(leader: LeadershipEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(leaders: List<LeadershipEntity>)

    @Update
    suspend fun updateLeadership(leader: LeadershipEntity)

    @Delete
    suspend fun deleteLeadership(leader: LeadershipEntity)
}

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments ORDER BY id DESC")
    fun getAllAppointments(): Flow<List<AppointmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: AppointmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(appointments: List<AppointmentEntity>)

    @Update
    suspend fun updateAppointment(appointment: AppointmentEntity)

    @Delete
    suspend fun deleteAppointment(appointment: AppointmentEntity)
}

@Dao
interface DepartmentDao {
    @Query("SELECT * FROM departments ORDER BY id ASC")
    fun getAllDepartments(): Flow<List<DepartmentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDepartment(dept: DepartmentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(depts: List<DepartmentEntity>)

    @Update
    suspend fun updateDepartment(dept: DepartmentEntity)

    @Delete
    suspend fun deleteDepartment(dept: DepartmentEntity)
}

@Dao
interface OrganizationalUnitDao {
    @Query("SELECT * FROM organizational_units ORDER BY id ASC")
    fun getAllUnits(): Flow<List<OrganizationalUnitEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: OrganizationalUnitEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(units: List<OrganizationalUnitEntity>)

    @Update
    suspend fun updateUnit(unit: OrganizationalUnitEntity)

    @Delete
    suspend fun deleteUnit(unit: OrganizationalUnitEntity)
}

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Query("SELECT * FROM notifications WHERE status = 'PUBLISHED' ORDER BY id DESC")
    fun getPublishedNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<NotificationEntity>)

    @Update
    suspend fun updateNotification(notification: NotificationEntity)

    @Delete
    suspend fun deleteNotification(notification: NotificationEntity)
}

@Dao
interface AnnouncementDao {
    @Query("SELECT * FROM announcements ORDER BY isPinned DESC, id DESC")
    fun getAllAnnouncements(): Flow<List<AnnouncementEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: AnnouncementEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(announcements: List<AnnouncementEntity>)

    @Update
    suspend fun updateAnnouncement(announcement: AnnouncementEntity)

    @Delete
    suspend fun deleteAnnouncement(announcement: AnnouncementEntity)
}

@Dao
interface EventDao {
    @Query("SELECT * FROM events ORDER BY id DESC")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Update
    suspend fun updateEvent(event: EventEntity)

    @Delete
    suspend fun deleteEvent(event: EventEntity)
}

@Dao
interface DocumentDao {
    @Query("SELECT * FROM documents ORDER BY id DESC")
    fun getAllDocuments(): Flow<List<DocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(doc: DocumentEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(docs: List<DocumentEntity>)

    @Update
    suspend fun updateDocument(doc: DocumentEntity)

    @Delete
    suspend fun deleteDocument(doc: DocumentEntity)
}

@Dao
interface GalleryAlbumDao {
    @Query("SELECT * FROM gallery_albums ORDER BY id DESC")
    fun getAllAlbums(): Flow<List<GalleryAlbumEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbum(album: GalleryAlbumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<GalleryAlbumEntity>)

    @Update
    suspend fun updateAlbum(album: GalleryAlbumEntity)

    @Delete
    suspend fun deleteAlbum(album: GalleryAlbumEntity)
}

@Dao
interface AuditLogDao {
    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC")
    fun getAllAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: AuditLogEntity): Long
}
