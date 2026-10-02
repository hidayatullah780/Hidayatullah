package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.SdufDrawerContent
import com.example.ui.components.SdufTopBar
import com.example.ui.screens.admin.AdminAppointmentsScreen
import com.example.ui.screens.admin.AdminBrandingAndAuditScreen
import com.example.ui.screens.admin.AdminCommunicationsScreen
import com.example.ui.screens.admin.AdminDashboardScreen
import com.example.ui.screens.admin.AdminDocumentsAndGalleryScreen
import com.example.ui.screens.admin.AdminLeadershipScreen
import com.example.ui.screens.admin.AdminMembersScreen
import com.example.ui.screens.admin.AdminStructureScreen
import com.example.ui.screens.auth.LoginScreen
import com.example.ui.screens.member.MemberCardScreen
import com.example.ui.screens.member.MemberDashboardScreen
import com.example.ui.screens.member.MemberProfileScreen
import com.example.ui.screens.public.AboutScreen
import com.example.ui.screens.public.AnnouncementsScreen
import com.example.ui.screens.public.ContactScreen
import com.example.ui.screens.public.DepartmentsScreen
import com.example.ui.screens.public.DocumentsScreen
import com.example.ui.screens.public.EventsScreen
import com.example.ui.screens.public.GalleryScreen
import com.example.ui.screens.public.GlobalSearchScreen
import com.example.ui.screens.public.HomeScreen
import com.example.ui.screens.public.LeadershipScreen
import com.example.ui.screens.public.MembershipApplyScreen
import com.example.ui.screens.public.UnitsScreen
import com.example.ui.screens.public.VerifyMemberScreen
import com.example.ui.theme.SdufTheme
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.SdufViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: SdufViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SdufTheme {
                SdufApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SdufApp(viewModel: SdufViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentUser by viewModel.currentUser.collectAsStateWithLifecycle()
    val userMessage by viewModel.userMessage.collectAsStateWithLifecycle()

    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val leadershipList by viewModel.leadershipList.collectAsStateWithLifecycle()
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()
    val departments by viewModel.departments.collectAsStateWithLifecycle()
    val units by viewModel.units.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val announcements by viewModel.announcements.collectAsStateWithLifecycle()
    val events by viewModel.events.collectAsStateWithLifecycle()
    val documents by viewModel.documents.collectAsStateWithLifecycle()
    val albums by viewModel.albums.collectAsStateWithLifecycle()
    val auditLogs by viewModel.auditLogs.collectAsStateWithLifecycle()
    val allMembers by viewModel.allMembers.collectAsStateWithLifecycle()

    val totalMembersCount by viewModel.totalMembersCount.collectAsStateWithLifecycle()
    val pendingMembersCount by viewModel.pendingMembersCount.collectAsStateWithLifecycle()

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()

    val verifiedMember by viewModel.verifiedMember.collectAsStateWithLifecycle()
    val isVerifying by viewModel.isVerifying.collectAsStateWithLifecycle()
    val verificationMessage by viewModel.verificationMessage.collectAsStateWithLifecycle()

    // Handle back button: return to Home when on a subscreen
    BackHandler(enabled = currentScreen !is Screen.Home || drawerState.isOpen) {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            viewModel.navigateTo(Screen.Home)
        }
    }

    // Display user messages / snackbars
    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearMessage()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SdufDrawerContent(
                    currentScreen = currentScreen,
                    currentUser = currentUser,
                    pendingCount = pendingMembersCount,
                    onNavigate = { screen ->
                        viewModel.navigateTo(screen)
                        scope.launch { drawerState.close() }
                    },
                    onSwitchPersona = { roleTag ->
                        viewModel.switchPersona(roleTag)
                        scope.launch { drawerState.close() }
                    },
                    onLogout = {
                        viewModel.logout()
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                SdufTopBar(
                    currentScreen = currentScreen,
                    currentUser = currentUser,
                    onOpenDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    onSearchClick = {
                        viewModel.navigateTo(Screen.GlobalSearch)
                    }
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            val contentModifier = Modifier.padding(innerPadding)

            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        settings = settings,
                        leadership = leadershipList,
                        announcements = announcements,
                        events = events,
                        onNavigate = { viewModel.navigateTo(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.About -> {
                    AboutScreen(
                        settings = settings,
                        modifier = contentModifier
                    )
                }

                is Screen.Leadership -> {
                    LeadershipScreen(
                        leadershipList = leadershipList,
                        modifier = contentModifier
                    )
                }

                is Screen.Departments -> {
                    DepartmentsScreen(
                        departments = departments,
                        modifier = contentModifier
                    )
                }

                is Screen.Units -> {
                    UnitsScreen(
                        units = units,
                        modifier = contentModifier
                    )
                }

                is Screen.Announcements -> {
                    AnnouncementsScreen(
                        announcements = announcements,
                        notifications = notifications,
                        modifier = contentModifier
                    )
                }

                is Screen.Events -> {
                    EventsScreen(
                        events = events,
                        modifier = contentModifier
                    )
                }

                is Screen.Documents -> {
                    DocumentsScreen(
                        documents = documents,
                        modifier = contentModifier
                    )
                }

                is Screen.Gallery -> {
                    GalleryScreen(
                        albums = albums,
                        modifier = contentModifier
                    )
                }

                is Screen.MembershipApply -> {
                    MembershipApplyScreen(
                        onSubmit = { fullName, fatherName, dob, gender, phone, email, district, taluka, uc, institution, degreeClass, address, category, emergencyContact ->
                            viewModel.submitMembershipApplication(
                                fullName, fatherName, dob, gender, phone, email, district, taluka, uc, institution, degreeClass, address, category, emergencyContact
                            ) { confirmation ->
                                viewModel.showMessage(confirmation)
                            }
                        },
                        modifier = contentModifier
                    )
                }

                is Screen.VerifyCard -> {
                    VerifyMemberScreen(
                        verifiedMember = verifiedMember,
                        isVerifying = isVerifying,
                        message = verificationMessage,
                        onVerify = { viewModel.verifyCard(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.Contact -> {
                    ContactScreen(
                        settings = settings,
                        modifier = contentModifier
                    )
                }

                is Screen.GlobalSearch -> {
                    GlobalSearchScreen(
                        query = searchQuery,
                        onQueryChange = { viewModel.setSearchQuery(it) },
                        results = searchResults,
                        modifier = contentModifier
                    )
                }

                is Screen.Login -> {
                    LoginScreen(
                        onLogin = { id, pass, cb -> viewModel.login(id, pass, cb) },
                        onQuickSwitch = { viewModel.switchPersona(it) },
                        onNavigate = { viewModel.navigateTo(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.MemberDashboard -> {
                    currentUser?.let { user ->
                        MemberDashboardScreen(
                            member = user,
                            onNavigate = { viewModel.navigateTo(it) },
                            onLogout = { viewModel.logout() },
                            modifier = contentModifier
                        )
                    } ?: LoginScreen(
                        onLogin = { id, pass, cb -> viewModel.login(id, pass, cb) },
                        onQuickSwitch = { viewModel.switchPersona(it) },
                        onNavigate = { viewModel.navigateTo(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.MemberCard -> {
                    currentUser?.let { user ->
                        MemberCardScreen(
                            member = user,
                            onNavigate = { viewModel.navigateTo(it) },
                            modifier = contentModifier
                        )
                    } ?: LoginScreen(
                        onLogin = { id, pass, cb -> viewModel.login(id, pass, cb) },
                        onQuickSwitch = { viewModel.switchPersona(it) },
                        onNavigate = { viewModel.navigateTo(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.MemberProfile -> {
                    currentUser?.let { user ->
                        MemberProfileScreen(
                            member = user,
                            onSaveProfile = { viewModel.updateMyProfile(it) },
                            modifier = contentModifier
                        )
                    } ?: LoginScreen(
                        onLogin = { id, pass, cb -> viewModel.login(id, pass, cb) },
                        onQuickSwitch = { viewModel.switchPersona(it) },
                        onNavigate = { viewModel.navigateTo(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.AdminDashboard -> {
                    AdminDashboardScreen(
                        totalMembers = totalMembersCount,
                        pendingMembers = pendingMembersCount,
                        activeUnitsCount = units.size,
                        upcomingEventsCount = events.count { it.status == "UPCOMING" },
                        totalDocsCount = documents.size,
                        onNavigate = { viewModel.navigateTo(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.AdminMembers -> {
                    AdminMembersScreen(
                        members = allMembers,
                        onApproveMember = { viewModel.approveMember(it) },
                        onUpdateStatus = { mem, st -> viewModel.updateMemberStatus(mem, st) },
                        onUpdateRole = { mem, role, desig -> viewModel.updateMemberRole(mem, role, desig) },
                        onDeleteMember = { viewModel.deleteMember(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.AdminLeadership -> {
                    AdminLeadershipScreen(
                        leadershipList = leadershipList,
                        onSaveLeader = { viewModel.saveLeadership(it) },
                        onDeleteLeader = { viewModel.deleteLeadership(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.AdminAppointments -> {
                    AdminAppointmentsScreen(
                        appointments = appointments,
                        onSaveAppointment = { viewModel.saveAppointment(it) },
                        onDeleteAppointment = { viewModel.deleteAppointment(it) },
                        modifier = contentModifier
                    )
                }

                is Screen.AdminDepartments -> {
                    AdminStructureScreen(
                        departments = departments,
                        units = units,
                        onSaveDepartment = { viewModel.saveDepartment(it) },
                        onDeleteDepartment = { viewModel.deleteDepartment(it) },
                        onSaveUnit = { viewModel.saveUnit(it) },
                        onDeleteUnit = { viewModel.deleteUnit(it) },
                        initialTab = 0,
                        modifier = contentModifier
                    )
                }

                is Screen.AdminUnits -> {
                    AdminStructureScreen(
                        departments = departments,
                        units = units,
                        onSaveDepartment = { viewModel.saveDepartment(it) },
                        onDeleteDepartment = { viewModel.deleteDepartment(it) },
                        onSaveUnit = { viewModel.saveUnit(it) },
                        onDeleteUnit = { viewModel.deleteUnit(it) },
                        initialTab = 1,
                        modifier = contentModifier
                    )
                }

                is Screen.AdminNotifications -> {
                    AdminCommunicationsScreen(
                        notifications = notifications,
                        announcements = announcements,
                        events = events,
                        onSaveNotification = { viewModel.saveNotification(it) },
                        onDeleteNotification = { viewModel.deleteNotification(it) },
                        onSaveAnnouncement = { viewModel.saveAnnouncement(it) },
                        onDeleteAnnouncement = { viewModel.deleteAnnouncement(it) },
                        onSaveEvent = { viewModel.saveEvent(it) },
                        onDeleteEvent = { viewModel.deleteEvent(it) },
                        initialTab = 0,
                        modifier = contentModifier
                    )
                }

                is Screen.AdminEvents -> {
                    AdminCommunicationsScreen(
                        notifications = notifications,
                        announcements = announcements,
                        events = events,
                        onSaveNotification = { viewModel.saveNotification(it) },
                        onDeleteNotification = { viewModel.deleteNotification(it) },
                        onSaveAnnouncement = { viewModel.saveAnnouncement(it) },
                        onDeleteAnnouncement = { viewModel.deleteAnnouncement(it) },
                        onSaveEvent = { viewModel.saveEvent(it) },
                        onDeleteEvent = { viewModel.deleteEvent(it) },
                        initialTab = 2,
                        modifier = contentModifier
                    )
                }

                is Screen.AdminDocuments -> {
                    AdminDocumentsAndGalleryScreen(
                        documents = documents,
                        albums = albums,
                        onSaveDocument = { viewModel.saveDocument(it) },
                        onDeleteDocument = { viewModel.deleteDocument(it) },
                        onSaveAlbum = { viewModel.saveAlbum(it) },
                        onDeleteAlbum = { viewModel.deleteAlbum(it) },
                        initialTab = 0,
                        modifier = contentModifier
                    )
                }

                is Screen.AdminGallery -> {
                    AdminDocumentsAndGalleryScreen(
                        documents = documents,
                        albums = albums,
                        onSaveDocument = { viewModel.saveDocument(it) },
                        onDeleteDocument = { viewModel.deleteDocument(it) },
                        onSaveAlbum = { viewModel.saveAlbum(it) },
                        onDeleteAlbum = { viewModel.deleteAlbum(it) },
                        initialTab = 1,
                        modifier = contentModifier
                    )
                }

                is Screen.AdminBranding -> {
                    AdminBrandingAndAuditScreen(
                        settings = settings,
                        auditLogs = auditLogs,
                        onUpdateSettings = { viewModel.updateBrandingAndHomepage(it) },
                        initialTab = 0,
                        modifier = contentModifier
                    )
                }

                is Screen.AdminAuditLogs -> {
                    AdminBrandingAndAuditScreen(
                        settings = settings,
                        auditLogs = auditLogs,
                        onUpdateSettings = { viewModel.updateBrandingAndHomepage(it) },
                        initialTab = 1,
                        modifier = contentModifier
                    )
                }
            }
        }
    }
}
