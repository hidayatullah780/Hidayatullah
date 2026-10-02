package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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
import com.example.data.repository.SdufRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen(val title: String) {
    // Public Portal
    data object Home : Screen("Home")
    data object About : Screen("About SDUF")
    data object Leadership : Screen("Leadership")
    data object Departments : Screen("Departments")
    data object Units : Screen("Organizational Units")
    data object Announcements : Screen("Announcements")
    data object Events : Screen("Events")
    data object Documents : Screen("Document Library")
    data object Gallery : Screen("Photo Gallery")
    data object MembershipApply : Screen("Join SDUF")
    data object VerifyCard : Screen("ID Verification")
    data object Contact : Screen("Contact Us")
    data object GlobalSearch : Screen("Search SDUF")

    // Auth
    data object Login : Screen("Account Login")

    // Member Portal
    data object MemberDashboard : Screen("Member Dashboard")
    data object MemberCard : Screen("Digital ID Card")
    data object MemberProfile : Screen("My Profile")

    // Admin Panel
    data object AdminDashboard : Screen("Admin Dashboard")
    data object AdminMembers : Screen("Manage Members")
    data object AdminLeadership : Screen("Manage Leadership")
    data object AdminAppointments : Screen("Appointments")
    data object AdminDepartments : Screen("Departments")
    data object AdminUnits : Screen("Organizational Units")
    data object AdminNotifications : Screen("Notifications & Notices")
    data object AdminEvents : Screen("Manage Events")
    data object AdminDocuments : Screen("Document Archive & Versions")
    data object AdminGallery : Screen("Gallery Manager")
    data object AdminBranding : Screen("Branding & Homepage Editor")
    data object AdminAuditLogs : Screen("Security Audit Logs")
}

data class GlobalSearchResult(
    val members: List<MemberEntity> = emptyList(),
    val leaders: List<LeadershipEntity> = emptyList(),
    val notices: List<NotificationEntity> = emptyList(),
    val events: List<EventEntity> = emptyList(),
    val docs: List<DocumentEntity> = emptyList()
)

class SdufViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application, viewModelScope)
    val repository = SdufRepository(database)

    // Current Screen
    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    // Current Logged-in User (null means Public Visitor)
    private val _currentUser = MutableStateFlow<MemberEntity?>(null)
    val currentUser: StateFlow<MemberEntity?> = _currentUser.asStateFlow()

    // UI Status / Toast / Banner
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    // Global Search State
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Verification Result State
    private val _verifiedMember = MutableStateFlow<MemberEntity?>(null)
    val verifiedMember: StateFlow<MemberEntity?> = _verifiedMember.asStateFlow()
    private val _isVerifying = MutableStateFlow(false)
    val isVerifying: StateFlow<Boolean> = _isVerifying.asStateFlow()
    private val _verificationMessage = MutableStateFlow<String?>(null)
    val verificationMessage: StateFlow<String?> = _verificationMessage.asStateFlow()

    // Repository flows
    val settings = repository.settings.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())
    val allMembers = repository.allMembers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val pendingMembers = repository.pendingMembers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val activeMembers = repository.activeMembers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val totalMembersCount = repository.totalMembersCount.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)
    val pendingMembersCount = repository.pendingMembersCount.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val leadershipList = repository.leadershipList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val appointments = repository.appointments.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val departments = repository.departments.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val units = repository.units.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val notifications = repository.notifications.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val publishedNotifications = repository.publishedNotifications.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val announcements = repository.announcements.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val events = repository.events.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val documents = repository.documents.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val albums = repository.albums.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val auditLogs = repository.auditLogs.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Search results combined flow
    val searchResults: StateFlow<GlobalSearchResult> = _searchQuery.map { query ->
        val q = query.trim().lowercase()
        if (q.isBlank()) {
            GlobalSearchResult()
        } else {
            GlobalSearchResult(
                members = allMembers.value.filter { it.fullName.lowercase().contains(q) || it.memberId.lowercase().contains(q) || it.institution.lowercase().contains(q) },
                leaders = leadershipList.value.filter { it.name.lowercase().contains(q) || it.position.lowercase().contains(q) || it.department.lowercase().contains(q) },
                notices = notifications.value.filter { it.title.lowercase().contains(q) || it.category.lowercase().contains(q) || it.message.lowercase().contains(q) },
                events = events.value.filter { it.title.lowercase().contains(q) || it.venue.lowercase().contains(q) },
                docs = documents.value.filter { it.title.lowercase().contains(q) || it.category.lowercase().contains(q) || it.documentNumber.lowercase().contains(q) }
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GlobalSearchResult())

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun clearMessage() {
        _userMessage.value = null
    }

    fun showMessage(msg: String) {
        _userMessage.value = msg
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    // Auth actions
    fun login(identifier: String, pass: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = repository.login(identifier, pass)
            if (user != null) {
                if (user.status == "SUSPENDED" || user.status == "INACTIVE") {
                    onResult(false, "Your account is ${user.status.lowercase()}. Please contact central administration.")
                } else {
                    _currentUser.value = user
                    if (isAdmin(user)) {
                        _currentScreen.value = Screen.AdminDashboard
                    } else {
                        _currentScreen.value = Screen.MemberDashboard
                    }
                    onResult(true, "Welcome back, ${user.fullName}!")
                }
            } else {
                onResult(false, "Invalid credentials. Please verify your Member ID / Email and password.")
            }
        }
    }

    fun logout() {
        _currentUser.value = null
        _currentScreen.value = Screen.Home
        _userMessage.value = "You have signed out."
    }

    fun switchPersona(roleTag: String) {
        viewModelScope.launch {
            when (roleTag) {
                "CHAIRMAN" -> {
                    val chairman = repository.login("chairman", "password123")
                    _currentUser.value = chairman
                    _currentScreen.value = Screen.AdminDashboard
                    _userMessage.value = "Switched to: Founder & Chairman Hidayatullah (Super Admin)"
                }
                "SECRETARY" -> {
                    val sec = repository.login("saqib", "password123")
                    _currentUser.value = sec
                    _currentScreen.value = Screen.AdminDashboard
                    _userMessage.value = "Switched to: Muhammad Saqib (General Secretary)"
                }
                "MEMBER" -> {
                    val mem = repository.login("tariq_member", "password123")
                    _currentUser.value = mem
                    _currentScreen.value = Screen.MemberDashboard
                    _userMessage.value = "Switched to: Tariq Mahmood (Active Member)"
                }
                "PUBLIC" -> {
                    _currentUser.value = null
                    _currentScreen.value = Screen.Home
                    _userMessage.value = "Switched to: Public Visitor Mode"
                }
            }
        }
    }

    fun verifyCard(memberId: String) {
        viewModelScope.launch {
            _isVerifying.value = true
            _verifiedMember.value = null
            _verificationMessage.value = null
            val found = repository.verifyMemberId(memberId)
            _isVerifying.value = false
            if (found != null) {
                _verifiedMember.value = found
                _verificationMessage.value = "Verified Official SDUF Member"
            } else {
                _verifiedMember.value = null
                _verificationMessage.value = "No member record found for ID: $memberId"
            }
        }
    }

    // Role verification helpers
    fun isSuperAdmin(): Boolean = _currentUser.value?.role == "SUPER_ADMIN"
    fun isAdmin(member: MemberEntity? = _currentUser.value): Boolean {
        if (member == null) return false
        return member.role in listOf("SUPER_ADMIN", "PRESIDENT", "GENERAL_SECRETARY", "SECRETARY", "UNIT_ADMIN")
    }

    val currentAdminName: String
        get() = _currentUser.value?.fullName ?: "Administrator"

    // Membership Registration
    fun submitMembershipApplication(
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
        emergencyContact: String,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            val username = email.substringBefore("@").replace(".", "_") + (10..99).random()
            val newMember = MemberEntity(
                memberId = "PENDING-${(1000..9999).random()}",
                username = username,
                email = email.trim(),
                password = "password123",
                fullName = fullName.trim(),
                fatherName = fatherName.trim(),
                dob = dob.trim(),
                gender = gender,
                phone = phone.trim(),
                district = district.trim(),
                taluka = taluka.trim(),
                unionCouncil = uc.trim(),
                institution = institution.trim(),
                degreeClass = degreeClass.trim(),
                address = address.trim(),
                membershipCategory = category,
                emergencyContact = emergencyContact.trim(),
                status = "PENDING",
                role = "MEMBER",
                designation = "Applicant",
                unitName = "$district District",
                appointmentDate = "Pending Approval"
            )
            repository.registerMember(newMember)
            onSuccess("Your membership application has been submitted successfully! Status: Pending Approval. Central Secretariat will review and issue your verified Member ID.")
        }
    }

    // Admin Member Actions
    fun approveMember(member: MemberEntity) {
        viewModelScope.launch {
            val generatedId = repository.approveMember(member, currentAdminName)
            _userMessage.value = "Approved ${member.fullName}! Assigned Member ID: $generatedId"
        }
    }

    fun updateMemberStatus(member: MemberEntity, newStatus: String) {
        viewModelScope.launch {
            repository.updateMemberStatus(member, newStatus, currentAdminName)
            _userMessage.value = "Updated ${member.fullName}'s status to $newStatus"
        }
    }

    fun updateMemberRole(member: MemberEntity, newRole: String, newDesignation: String) {
        viewModelScope.launch {
            repository.updateMemberRole(member, newRole, newDesignation, currentAdminName)
            _userMessage.value = "Updated ${member.fullName} to $newRole ($newDesignation)"
        }
    }

    fun deleteMember(member: MemberEntity) {
        viewModelScope.launch {
            repository.deleteMember(member, currentAdminName)
            _userMessage.value = "Removed ${member.fullName} from registry"
        }
    }

    // Leadership Management
    fun saveLeadership(leader: LeadershipEntity) {
        viewModelScope.launch {
            repository.saveLeadership(leader, currentAdminName)
            _userMessage.value = "Saved leadership record: ${leader.name} (${leader.position})"
        }
    }

    fun deleteLeadership(leader: LeadershipEntity) {
        viewModelScope.launch {
            repository.deleteLeadership(leader, currentAdminName)
            _userMessage.value = "Removed leadership record: ${leader.name}"
        }
    }

    // Appointment Management
    fun saveAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            repository.saveAppointment(appointment, currentAdminName)
            _userMessage.value = "Saved appointment: ${appointment.personName} as ${appointment.designation}"
        }
    }

    fun deleteAppointment(appointment: AppointmentEntity) {
        viewModelScope.launch {
            repository.deleteAppointment(appointment, currentAdminName)
            _userMessage.value = "Removed appointment: ${appointment.personName}"
        }
    }

    // Department Management
    fun saveDepartment(dept: DepartmentEntity) {
        viewModelScope.launch {
            repository.saveDepartment(dept, currentAdminName)
            _userMessage.value = "Saved department: ${dept.name}"
        }
    }

    fun deleteDepartment(dept: DepartmentEntity) {
        viewModelScope.launch {
            repository.deleteDepartment(dept, currentAdminName)
            _userMessage.value = "Removed department: ${dept.name}"
        }
    }

    // Unit Management
    fun saveUnit(unit: OrganizationalUnitEntity) {
        viewModelScope.launch {
            repository.saveUnit(unit, currentAdminName)
            _userMessage.value = "Saved unit: ${unit.name} (${unit.type})"
        }
    }

    fun deleteUnit(unit: OrganizationalUnitEntity) {
        viewModelScope.launch {
            repository.deleteUnit(unit, currentAdminName)
            _userMessage.value = "Removed unit: ${unit.name}"
        }
    }

    // Notification Management
    fun saveNotification(notif: NotificationEntity) {
        viewModelScope.launch {
            repository.saveNotification(notif, currentAdminName)
            _userMessage.value = "Saved notification: ${notif.title} [${notif.status}]"
        }
    }

    fun deleteNotification(notif: NotificationEntity) {
        viewModelScope.launch {
            repository.deleteNotification(notif, currentAdminName)
            _userMessage.value = "Deleted notification: ${notif.title}"
        }
    }

    // Announcement Management
    fun saveAnnouncement(announcement: AnnouncementEntity) {
        viewModelScope.launch {
            repository.saveAnnouncement(announcement, currentAdminName)
            _userMessage.value = "Saved announcement: ${announcement.title}"
        }
    }

    fun deleteAnnouncement(announcement: AnnouncementEntity) {
        viewModelScope.launch {
            repository.deleteAnnouncement(announcement, currentAdminName)
            _userMessage.value = "Deleted announcement: ${announcement.title}"
        }
    }

    // Event Management
    fun saveEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.saveEvent(event, currentAdminName)
            _userMessage.value = "Saved event: ${event.title} (${event.status})"
        }
    }

    fun deleteEvent(event: EventEntity) {
        viewModelScope.launch {
            repository.deleteEvent(event, currentAdminName)
            _userMessage.value = "Deleted event: ${event.title}"
        }
    }

    // Document Management with Versioning
    fun saveDocument(doc: DocumentEntity) {
        viewModelScope.launch {
            repository.saveDocument(doc, currentAdminName)
            _userMessage.value = "Saved document: ${doc.title} (${doc.version})"
        }
    }

    fun deleteDocument(doc: DocumentEntity) {
        viewModelScope.launch {
            repository.deleteDocument(doc, currentAdminName)
            _userMessage.value = "Deleted document: ${doc.title}"
        }
    }

    // Gallery Management
    fun saveAlbum(album: GalleryAlbumEntity) {
        viewModelScope.launch {
            repository.saveAlbum(album, currentAdminName)
            _userMessage.value = "Saved album: ${album.title}"
        }
    }

    fun deleteAlbum(album: GalleryAlbumEntity) {
        viewModelScope.launch {
            repository.deleteAlbum(album, currentAdminName)
            _userMessage.value = "Deleted album: ${album.title}"
        }
    }

    // Homepage and Branding editor
    fun updateBrandingAndHomepage(settingsMap: Map<String, String>) {
        viewModelScope.launch {
            repository.updateSettings(settingsMap, currentAdminName)
            _userMessage.value = "Branding and homepage settings updated successfully!"
        }
    }

    fun updateMyProfile(updated: MemberEntity) {
        viewModelScope.launch {
            repository.updateMemberProfile(updated)
            _currentUser.value = updated
            _userMessage.value = "Profile updated successfully!"
        }
    }
}
