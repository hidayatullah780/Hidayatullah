package com.example.data.local

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

object InitialDataProvider {
    fun getInitialSettings(): List<SettingEntity> = listOf(
        SettingEntity("org_name", "Students' Democratic Union Federation"),
        SettingEntity("org_short_name", "SDUF"),
        SettingEntity("slogan", "Democratic participation, student representation and organizational development."),
        SettingEntity("hero_title", "Students' Democratic Union Federation"),
        SettingEntity("hero_subtitle", "Democratic participation, student representation and organizational development."),
        SettingEntity("hero_button_text", "Explore SDUF"),
        SettingEntity("contact_email", "central.secretariat@sduf.org"),
        SettingEntity("contact_phone", "+92 300 1234567"),
        SettingEntity("office_address", "Central Secretariat SDUF, Student Movement Complex, Sindh, Pakistan"),
        SettingEntity("social_facebook", "facebook.com/sdufofficial"),
        SettingEntity("social_twitter", "x.com/sduf_pakistan"),
        SettingEntity("social_instagram", "instagram.com/sduf_official"),
        SettingEntity("social_youtube", "youtube.com/@SDUFOfficial"),
        SettingEntity("about_sduf", "Students' Democratic Union Federation (SDUF) is a premier progressive student movement founded on the tenets of social justice, constitutional democracy, student welfare, and visionary leadership development across universities and colleges."),
        SettingEntity("constitution_preamble", "We, the students and youth belonging to diverse academic disciplines, unite under the Students' Democratic Union Federation (SDUF) to protect academic liberties, dismantle educational inequalities, and champion constitutional student governance.")
    )

    fun getInitialLeadership(): List<LeadershipEntity> = listOf(
        LeadershipEntity(
            id = 1,
            name = "Hidayatullah",
            position = "Founder & Chairman",
            department = "Supreme Council",
            bio = "Visionary founder of SDUF leading democratic mobilization, constitutional student rights, and youth empowerment across educational institutions.",
            appointmentDate = "15 Jan 2024",
            displayOrder = 1,
            status = "ACTIVE",
            avatarTag = "leader_founder"
        ),
        LeadershipEntity(
            id = 2,
            name = "Basheer Ahmed",
            position = "Vice President",
            department = "Executive Council",
            bio = "Key executive lead overseeing provincial federations, student advocacy campaigns, and policy implementation.",
            appointmentDate = "10 Feb 2024",
            displayOrder = 2,
            status = "ACTIVE",
            avatarTag = "leader_vp"
        ),
        LeadershipEntity(
            id = 3,
            name = "Muhammad Saqib",
            position = "General Secretary",
            department = "Central Secretariat",
            bio = "Chief administrative officer orchestrating national student membership records, correspondence, and institutional coordination.",
            appointmentDate = "15 Mar 2024",
            displayOrder = 3,
            status = "ACTIVE",
            avatarTag = "leader_gs"
        ),
        LeadershipEntity(
            id = 4,
            name = "Fawad Ali",
            position = "Organizing Secretary",
            department = "Organization Department",
            bio = "Directs grassroots organizing, regional rallies, campus unit formations, and student union elections.",
            appointmentDate = "20 Mar 2024",
            displayOrder = 4,
            status = "ACTIVE",
            avatarTag = "leader_org"
        ),
        LeadershipEntity(
            id = 5,
            name = "Waqar Ali",
            position = "Deputy Organizing Secretary",
            department = "Organization Department",
            bio = "Assists in unit expansion, member onboarding, and student liaison at institutional levels.",
            appointmentDate = "01 Apr 2024",
            displayOrder = 5,
            status = "ACTIVE",
            avatarTag = "leader_deputy"
        ),
        LeadershipEntity(
            id = 6,
            name = "Shahzad Ali",
            position = "Information Secretary",
            department = "Information & Media Department",
            bio = "Manages media communications, press briefings, official publications, and public relations.",
            appointmentDate = "10 May 2024",
            displayOrder = 6,
            status = "ACTIVE",
            avatarTag = "leader_info"
        ),
        LeadershipEntity(
            id = 7,
            name = "Amresh Kumar",
            position = "Party Spokesman",
            department = "Public Affairs & Media",
            bio = "Official voice of SDUF addressing media forums, television panels, and democratic youth dialogues.",
            appointmentDate = "15 May 2024",
            displayOrder = 7,
            status = "ACTIVE",
            avatarTag = "leader_spokesman"
        ),
        LeadershipEntity(
            id = 8,
            name = "Abdul Hammad",
            position = "Finance Secretary",
            department = "Finance Department",
            bio = "Oversees transparent fiscal management, member dues, audit reports, and campaign fund allocations.",
            appointmentDate = "01 Jun 2024",
            displayOrder = 8,
            status = "ACTIVE",
            avatarTag = "leader_finance"
        )
    )

    fun getInitialMembers(): List<MemberEntity> = listOf(
        MemberEntity(
            id = 1,
            memberId = "SDUF-2024-000001",
            username = "chairman",
            email = "chairman@sduf.org",
            password = "password123",
            fullName = "Hidayatullah",
            fatherName = "Ghulam Qadir",
            dob = "1998-05-12",
            gender = "Male",
            phone = "+92 300 1112233",
            district = "Hyderabad",
            taluka = "City",
            unionCouncil = "UC-01",
            institution = "University of Sindh",
            degreeClass = "M.Phil Political Science",
            address = "Central Secretariat SDUF, Sindh",
            membershipCategory = "Council Delegate",
            emergencyContact = "+92 300 9998877",
            status = "ACTIVE",
            role = "SUPER_ADMIN",
            designation = "Founder & Chairman",
            unitName = "Central Secretariat",
            appointmentDate = "15 Jan 2024"
        ),
        MemberEntity(
            id = 2,
            memberId = "SDUF-2024-000002",
            username = "saqib",
            email = "secretary@sduf.org",
            password = "password123",
            fullName = "Muhammad Saqib",
            fatherName = "Abdul Rahim",
            dob = "1999-08-20",
            gender = "Male",
            phone = "+92 301 2223344",
            district = "Tando Allahyar",
            taluka = "Chamber",
            unionCouncil = "Chamber UC-02",
            institution = "Mehran UET",
            degreeClass = "B.E. Civil Engineering",
            address = "Main Bazar Chamber, Tando Allahyar",
            membershipCategory = "Council Delegate",
            emergencyContact = "+92 301 8887766",
            status = "ACTIVE",
            role = "GENERAL_SECRETARY",
            designation = "General Secretary",
            unitName = "Central Secretariat",
            appointmentDate = "15 Mar 2024"
        ),
        MemberEntity(
            id = 3,
            memberId = "SDUF-2024-000003",
            username = "basheer",
            email = "vp@sduf.org",
            password = "password123",
            fullName = "Basheer Ahmed",
            fatherName = "Muhammad Hashim",
            dob = "1997-11-14",
            gender = "Male",
            phone = "+92 302 3334455",
            district = "Sukkur",
            taluka = "City",
            unionCouncil = "UC-04",
            institution = "IBA Sukkur University",
            degreeClass = "MBA Executive",
            address = "Military Road, Sukkur",
            membershipCategory = "Council Delegate",
            emergencyContact = "+92 302 7776655",
            status = "ACTIVE",
            role = "PRESIDENT",
            designation = "Vice President",
            unitName = "Central Executive",
            appointmentDate = "10 Feb 2024"
        ),
        MemberEntity(
            id = 4,
            memberId = "SDUF-2026-000123",
            username = "tariq_member",
            email = "member@sduf.org",
            password = "password123",
            fullName = "Tariq Mahmood",
            fatherName = "Sultan Mahmood",
            dob = "2003-04-18",
            gender = "Male",
            phone = "+92 305 5556677",
            district = "Tando Allahyar",
            taluka = "Chamber",
            unionCouncil = "Chamber UC-01",
            institution = "Government Degree College Chamber",
            degreeClass = "BS Computer Science",
            address = "Ward 3, Chamber",
            membershipCategory = "Student",
            emergencyContact = "+92 305 1112244",
            status = "ACTIVE",
            role = "MEMBER",
            designation = "Student Member",
            unitName = "Chamber Taluka Unit",
            appointmentDate = "01 Jan 2026"
        ),
        MemberEntity(
            id = 5,
            memberId = "SDUF-2026-000124",
            username = "ayesha_pending",
            email = "ayesha@student.org",
            password = "password123",
            fullName = "Ayesha Noor",
            fatherName = "Noor Muhammad",
            dob = "2004-09-02",
            gender = "Female",
            phone = "+92 307 7778899",
            district = "Hyderabad",
            taluka = "Latifabad",
            unionCouncil = "UC-07",
            institution = "LUMHS Jamshoro",
            degreeClass = "MBBS 3rd Year",
            address = "Latifabad Unit 6, Hyderabad",
            membershipCategory = "Student",
            emergencyContact = "+92 307 2223311",
            status = "PENDING",
            role = "MEMBER",
            designation = "Applicant",
            unitName = "Hyderabad District",
            appointmentDate = "Pending Approval"
        )
    )

    fun getInitialDepartments(): List<DepartmentEntity> = listOf(
        DepartmentEntity(
            id = 1,
            name = "Information Department",
            headName = "Shahzad Ali",
            deputyName = "Farhan Zafar",
            description = "Disseminates official statements, coordinates media briefings, maintains digital archives, and delivers transparency across union initiatives.",
            objectives = "Promote accurate facts, issue timely press statements, and uphold digital student news bulletins.",
            memberCount = 14
        ),
        DepartmentEntity(
            id = 2,
            name = "Finance Department",
            headName = "Abdul Hammad",
            deputyName = "Noman Abbasi",
            description = "Maintains constitutional financial audits, oversees membership dues, and guarantees fiscal integrity.",
            objectives = "Audit annual budget, manage campaign resources, and provide transparent receipts to council.",
            memberCount = 9
        ),
        DepartmentEntity(
            id = 3,
            name = "Organization Department",
            headName = "Fawad Ali",
            deputyName = "Waqar Ali",
            description = "Drives grassroots organizing, builds new district & campus units, conducts peaceful rallies, and oversees internal elections.",
            objectives = "Expand organizational footprint to all major universities and foster democratic participation.",
            memberCount = 38
        ),
        DepartmentEntity(
            id = 4,
            name = "Student Affairs Department",
            headName = "Zubair Memon",
            deputyName = "Bilal Khattak",
            description = "Resolves campus grievances, hostel accommodation issues, fee hike protests, and library resources for students.",
            objectives = "Protect student welfare and represent students before university administrations.",
            memberCount = 27
        ),
        DepartmentEntity(
            id = 5,
            name = "Media & Publications Department",
            headName = "Amresh Kumar",
            deputyName = "Naveed Baloch",
            description = "Publishes monthly newsletters, student rights manifestos, digital podcasts, and video coverage of democratic rallies.",
            objectives = "Broaden student political consciousness and articulate public policy positions.",
            memberCount = 16
        ),
        DepartmentEntity(
            id = 6,
            name = "Research & Policy Department",
            headName = "Dr. Asif Jamil",
            deputyName = "Maria Khan",
            description = "Drafts white papers on educational reforms, higher education funding, scholarship distributions, and student union laws.",
            objectives = "Produce evidence-based research papers for legislative reforms.",
            memberCount = 11
        )
    )

    fun getInitialUnits(): List<OrganizationalUnitEntity> = listOf(
        OrganizationalUnitEntity(
            id = 1,
            name = "Central Secretariat",
            type = "CENTRAL",
            parentUnitName = "National Council",
            headName = "Muhammad Saqib",
            deputyName = "Fawad Ali",
            contactPhone = "+92 300 1234567",
            description = "Supreme administrative headquarters of Students' Democratic Union Federation."
        ),
        OrganizationalUnitEntity(
            id = 2,
            name = "Sindh Provincial Federation",
            type = "PROVINCE",
            parentUnitName = "Central Secretariat",
            headName = "Basheer Ahmed",
            deputyName = "Amresh Kumar",
            contactPhone = "+92 301 9876543",
            description = "Provincial coordination chapter across all districts of Sindh."
        ),
        OrganizationalUnitEntity(
            id = 3,
            name = "Tando Allahyar District Unit",
            type = "DISTRICT",
            parentUnitName = "Sindh Provincial Federation",
            headName = "Rashid Ali",
            deputyName = "Kamran Soomro",
            contactPhone = "+92 302 4455667",
            description = "District committee governing student chapters across Tando Allahyar."
        ),
        OrganizationalUnitEntity(
            id = 4,
            name = "Chamber Taluka Unit",
            type = "TALUKA",
            parentUnitName = "Tando Allahyar District Unit",
            headName = "Tariq Mahmood",
            deputyName = "Imtiaz Jamali",
            contactPhone = "+92 305 5556677",
            description = "Local unit administering colleges and youth representation in Chamber."
        ),
        OrganizationalUnitEntity(
            id = 5,
            name = "Hyderabad District Unit",
            type = "DISTRICT",
            parentUnitName = "Sindh Provincial Federation",
            headName = "Sarfaraz Lashari",
            deputyName = "Azeem Solangi",
            contactPhone = "+92 300 7788990",
            description = "Coordinates major public university campuses and higher secondary colleges in Hyderabad."
        )
    )

    fun getInitialAppointments(): List<AppointmentEntity> = listOf(
        AppointmentEntity(
            id = 1,
            personName = "Muhammad Saqib",
            memberId = "SDUF-2024-000002",
            designation = "General Secretary",
            appointmentDate = "15 Mar 2024",
            letterNumber = "SDUF/APP/2024-08",
            authority = "Founder & Chairman Hidayatullah",
            term = "2024 - 2026",
            status = "ACTIVE",
            documentRef = "SDUF-DOC-APP-08.pdf"
        ),
        AppointmentEntity(
            id = 2,
            personName = "Basheer Ahmed",
            memberId = "SDUF-2024-000003",
            designation = "Vice President",
            appointmentDate = "10 Feb 2024",
            letterNumber = "SDUF/APP/2024-04",
            authority = "Supreme Council of SDUF",
            term = "2024 - 2026",
            status = "ACTIVE",
            documentRef = "SDUF-DOC-APP-04.pdf"
        ),
        AppointmentEntity(
            id = 3,
            personName = "Fawad Ali",
            memberId = "SDUF-2024-000004",
            designation = "Organizing Secretary",
            appointmentDate = "20 Mar 2024",
            letterNumber = "SDUF/APP/2024-11",
            authority = "Founder & Chairman Hidayatullah",
            term = "2024 - 2026",
            status = "ACTIVE",
            documentRef = "SDUF-DOC-APP-11.pdf"
        )
    )

    fun getInitialNotifications(): List<NotificationEntity> = listOf(
        NotificationEntity(
            id = 1,
            title = "Core Leadership Appointment & Ratification",
            message = "The Supreme Council of SDUF has formally confirmed the appointments of Central Executive office-bearers for the term 2024-2026. All institutional units must update regional registers accordingly.",
            date = "01 Oct 2026",
            category = "Official Notification",
            attachmentName = "Notification_Core_Appointments_2026.pdf",
            targetAudience = "All Members",
            status = "PUBLISHED"
        ),
        NotificationEntity(
            id = 2,
            title = "Annual Membership Verification & Card Issuance",
            message = "All active members are instructed to review their digital profile and membership cards via the mobile portal. New QR validation security features have been activated across all university chapters.",
            date = "28 Sep 2026",
            category = "Circular",
            attachmentName = "Circular_Digital_ID_Verification.pdf",
            targetAudience = "All Members",
            status = "PUBLISHED"
        ),
        NotificationEntity(
            id = 3,
            title = "Standing Committee on Campus Welfare Grants",
            message = "Proposals for student emergency grants, scholarship allocations, and textbook lending libraries are now open for submission to the Student Affairs Department.",
            date = "20 Sep 2026",
            category = "Directive",
            attachmentName = "Campus_Welfare_Grants_Guidelines.pdf",
            targetAudience = "Unit Admins",
            status = "PUBLISHED"
        )
    )

    fun getInitialAnnouncements(): List<AnnouncementEntity> = listOf(
        AnnouncementEntity(
            id = 1,
            title = "Oath of Office Ceremony",
            content = "The official Oath Taking Ceremony for newly appointed core leadership will be held on 5 October 2026 at the Central Auditorium. All council delegates and unit leaders are requested to attend in formal attire.",
            date = "05 Oct 2026",
            badge = "Major Event",
            isPinned = true,
            status = "ACTIVE"
        ),
        AnnouncementEntity(
            id = 2,
            title = "Digital Membership Drive 2026",
            content = "The federation has opened nationwide online membership registration. Students can now submit digital applications directly through the official mobile app.",
            date = "02 Oct 2026",
            badge = "Registration Open",
            isPinned = true,
            status = "ACTIVE"
        ),
        AnnouncementEntity(
            id = 3,
            title = "Student Rights & Educational Reform Convention",
            content = "Join SDUF delegates as we assemble for the Provincial Student Parliament dialogue addressing affordable education and campus healthcare access.",
            date = "15 Oct 2026",
            badge = "Convention",
            isPinned = false,
            status = "ACTIVE"
        )
    )

    fun getInitialEvents(): List<EventEntity> = listOf(
        EventEntity(
            id = 1,
            title = "Oath of Office Ceremony",
            date = "05 Oct 2026",
            time = "10:30 AM PST",
            venue = "Central Secretariat Hall, Sindh",
            description = "The prestigious oath ceremony for newly appointed core leadership, administered by Founder & Chairman Hidayatullah.",
            status = "UPCOMING",
            category = "Ceremony"
        ),
        EventEntity(
            id = 2,
            title = "Provincial Student Convention 2026",
            date = "22 Oct 2026",
            time = "02:00 PM PST",
            venue = "Hyderabad Civic Center, Hyderabad",
            description = "Gathering of over 1,500 delegates from universities across Sindh to formulate the 2027 Student Rights Charter.",
            status = "UPCOMING",
            category = "Convention"
        ),
        EventEntity(
            id = 3,
            title = "Grassroots Leadership Workshop",
            date = "15 Sep 2026",
            time = "11:00 AM PST",
            venue = "Chamber Degree College Auditorium",
            description = "Capacity building seminar on parliamentary procedures, youth organizing, and non-violent civic advocacy.",
            status = "COMPLETED",
            category = "Workshop"
        )
    )

    fun getInitialDocuments(): List<DocumentEntity> = listOf(
        DocumentEntity(
            id = 1,
            title = "Constitution of SDUF",
            documentNumber = "SDUF/CONST/2026-V2",
            date = "15 Jan 2026",
            category = "Constitution",
            description = "Official ratified constitution governing the federation, establishing democratic elections, organizational councils, and ethical codes of conduct.",
            version = "v2.0",
            isCurrentVersion = true,
            visibility = "PUBLIC",
            status = "ACTIVE"
        ),
        DocumentEntity(
            id = 2,
            title = "Constitution of SDUF (Founding Draft)",
            documentNumber = "SDUF/CONST/2024-V1",
            date = "15 Jan 2024",
            category = "Constitution",
            description = "Founding constitutional draft adopted during the 1st National Student Congress.",
            version = "v1.0",
            isCurrentVersion = false,
            visibility = "PUBLIC",
            status = "ARCHIVED"
        ),
        DocumentEntity(
            id = 3,
            title = "Official Oath of Office Document",
            documentNumber = "SDUF/OATH/2026",
            date = "01 Oct 2026",
            category = "Oaths",
            description = "Standard pledge undertaken by all central, provincial, and local unit office-bearers upon appointment.",
            version = "v1.0",
            isCurrentVersion = true,
            visibility = "PUBLIC",
            status = "ACTIVE"
        ),
        DocumentEntity(
            id = 4,
            title = "Code of Organizational Conduct & Discipline",
            documentNumber = "SDUF/POL/2025-01",
            date = "10 Aug 2025",
            category = "Policies",
            description = "Guidelines defining member accountability, anti-harassment regulations, and dispute resolution mechanisms.",
            version = "v1.2",
            isCurrentVersion = true,
            visibility = "PUBLIC",
            status = "ACTIVE"
        ),
        DocumentEntity(
            id = 5,
            title = "Membership Application & Verification Rules",
            documentNumber = "SDUF/FORM/2026-M1",
            date = "01 Jan 2026",
            category = "Forms",
            description = "Procedures and eligibility criteria governing student enrollment, unit affiliation, and digital card generation.",
            version = "v1.0",
            isCurrentVersion = true,
            visibility = "PUBLIC",
            status = "ACTIVE"
        )
    )

    fun getInitialAlbums(): List<GalleryAlbumEntity> = listOf(
        GalleryAlbumEntity(
            id = 1,
            title = "Oath of Office Ceremony Preparations",
            date = "02 Oct 2026",
            description = "Arrival of student union delegates at Central Secretariat preparing for the historic oath-taking.",
            photoCount = 8,
            tag = "Ceremony"
        ),
        GalleryAlbumEntity(
            id = 2,
            title = "Student Rights Mega Rally",
            date = "18 Sep 2026",
            description = "Thousands of university students march peacefully demanding fee reductions and modern campus transit.",
            photoCount = 14,
            tag = "Advocacy"
        ),
        GalleryAlbumEntity(
            id = 3,
            title = "National Leadership Summit",
            date = "05 Aug 2026",
            description = "Deliberations of the Central Executive Committee and provincial delegates.",
            photoCount = 10,
            tag = "Conference"
        )
    )

    fun getInitialAuditLogs(): List<AuditLogEntity> = listOf(
        AuditLogEntity(
            id = 1,
            adminName = "Hidayatullah",
            action = "Updated Leadership Record",
            recordAffected = "Basheer Ahmed (ID: 2)",
            oldValue = "Designation: Vice President",
            newValue = "Designation: Senior Vice President & Executive Lead",
            timestamp = System.currentTimeMillis() - 86400000L,
            formattedDate = "01 Oct 2026 — 21:30"
        ),
        AuditLogEntity(
            id = 2,
            adminName = "Muhammad Saqib",
            action = "Approved Member Application",
            recordAffected = "Tariq Mahmood (Member ID: SDUF-2026-000123)",
            oldValue = "Status: PENDING",
            newValue = "Status: ACTIVE (ID Generated)",
            timestamp = System.currentTimeMillis() - 43200000L,
            formattedDate = "02 Oct 2026 — 09:15"
        )
    )
}
