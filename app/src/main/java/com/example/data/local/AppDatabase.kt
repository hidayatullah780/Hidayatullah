package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.AnnouncementDao
import com.example.data.local.dao.AppointmentDao
import com.example.data.local.dao.AuditLogDao
import com.example.data.local.dao.DepartmentDao
import com.example.data.local.dao.DocumentDao
import com.example.data.local.dao.EventDao
import com.example.data.local.dao.GalleryAlbumDao
import com.example.data.local.dao.LeadershipDao
import com.example.data.local.dao.MemberDao
import com.example.data.local.dao.NotificationDao
import com.example.data.local.dao.OrganizationalUnitDao
import com.example.data.local.dao.SettingDao
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        SettingEntity::class,
        MemberEntity::class,
        LeadershipEntity::class,
        AppointmentEntity::class,
        DepartmentEntity::class,
        OrganizationalUnitEntity::class,
        NotificationEntity::class,
        AnnouncementEntity::class,
        EventEntity::class,
        DocumentEntity::class,
        GalleryAlbumEntity::class,
        AuditLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun settingDao(): SettingDao
    abstract fun memberDao(): MemberDao
    abstract fun leadershipDao(): LeadershipDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun departmentDao(): DepartmentDao
    abstract fun organizationalUnitDao(): OrganizationalUnitDao
    abstract fun notificationDao(): NotificationDao
    abstract fun announcementDao(): AnnouncementDao
    abstract fun eventDao(): EventDao
    abstract fun documentDao(): DocumentDao
    abstract fun galleryAlbumDao(): GalleryAlbumDao
    abstract fun auditLogDao(): AuditLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sduf_database.db"
                ).addCallback(AppDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Safety guarantee: Ensure tables have initial data if somehow empty
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        if (database.settingDao().getSettingByKey("org_name") == null) {
                            populateDatabase(database)
                        }
                    }
                }
            }

            suspend fun populateDatabase(database: AppDatabase) {
                database.settingDao().insertSettings(InitialDataProvider.getInitialSettings())
                database.leadershipDao().insertAll(InitialDataProvider.getInitialLeadership())
                database.memberDao().insertMembers(InitialDataProvider.getInitialMembers())
                database.departmentDao().insertAll(InitialDataProvider.getInitialDepartments())
                database.organizationalUnitDao().insertAll(InitialDataProvider.getInitialUnits())
                database.appointmentDao().insertAll(InitialDataProvider.getInitialAppointments())
                database.notificationDao().insertAll(InitialDataProvider.getInitialNotifications())
                database.announcementDao().insertAll(InitialDataProvider.getInitialAnnouncements())
                database.eventDao().insertAll(InitialDataProvider.getInitialEvents())
                database.documentDao().insertAll(InitialDataProvider.getInitialDocuments())
                database.galleryAlbumDao().insertAll(InitialDataProvider.getInitialAlbums())
                InitialDataProvider.getInitialAuditLogs().forEach {
                    database.auditLogDao().insertLog(it)
                }
            }
        }
    }
}
