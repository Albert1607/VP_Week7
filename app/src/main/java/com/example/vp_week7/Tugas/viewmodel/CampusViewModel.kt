package com.example.vp_week7.Tugas.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vp_week7.Tugas.model.Community
import com.example.vp_week7.Tugas.model.Project
import com.example.vp_week7.Tugas.model.ProjectStatus
import com.example.vp_week7.Tugas.model.StudentProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class CampusViewModel : ViewModel() {

    val studentProfile: StudentProfile = StudentProfile()

    private val _communities = MutableStateFlow<List<Community>>(
        listOf(
            Community(
                id = "gdsc",
                name = "GDSC",
                subtitle = "Google Developer Student Club • 142 Mahasiswa",
                description = "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.",
                memberCount = 142,
                isJoined = true
            ),
            Community(
                id = "cybersecurity",
                name = "CyberSecurity Guild",
                subtitle = "Security & Pentesting • 88 Mahasiswa",
                description = "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.",
                memberCount = 88,
                isJoined = true
            ),
            Community(
                id = "aisociety",
                name = "AI Society",
                subtitle = "Machine Learning & LLM • 115 Mahasiswa",
                description = "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.",
                memberCount = 115,
                isJoined = false
            ),
            Community(
                id = "mobiledev",
                name = "Mobile Dev Community",
                subtitle = "Native Jetpack Compose & iOS • 95 Mahasiswa",
                description = "Pengembangan aplikasi mobile native modern menggunakan Android Compose & iOS Swift UI.",
                memberCount = 95,
                isJoined = true
            ),
            Community(
                id = "webdev",
                name = "Web Dev Club",
                subtitle = "Frontend & Backend Web • 120 Mahasiswa",
                description = "Membangun aplikasi web modern dengan React, Vue, Next.js, Ktor, dan cloud microservices.",
                memberCount = 120,
                isJoined = false
            ),
            Community(
                id = "gamedev",
                name = "Game Dev Studio",
                subtitle = "Unity & Unreal Engine • 76 Mahasiswa",
                description = "Eksplorasi pembuatan game 2D & 3D, shader programming, dan game mechanics design.",
                memberCount = 76,
                isJoined = false
            )
        )
    )
    val communities: StateFlow<List<Community>> = _communities.asStateFlow()

    private val _projects = MutableStateFlow<List<Project>>(
        listOf(
            Project(
                id = "proj-gdsc-01",
                communityId = "gdsc",
                title = "Smart Campus Navigation",
                description = "Sistem navigasi peta gedung kampus interaktif berbasis Jetpack Compose dan Google Maps SDK. Memfasilitasi mahasiswa dan dosen menemukan ruang perkuliahan, laboratorium komputer, perpustakaan, serta fasilitas umum kampus secara akurat disertai rute tercepat dan visualisasi lokasi real-time.",
                techStack = listOf("Kotlin", "Jetpack Compose", "Room", "Google Maps SDK"),
                status = ProjectStatus.IN_PROGRESS
            ),
            Project(
                id = "proj-gdsc-02",
                communityId = "gdsc",
                title = "Student Attendance QR Scanner",
                description = "Pemindai barcode & QR presensi kehadiran workshop berbasis CameraX & Firebase.",
                techStack = listOf("Jetpack Compose", "Firebase", "CameraX"),
                status = ProjectStatus.COMPLETED
            ),
            Project(
                id = "proj-gdsc-03",
                communityId = "gdsc",
                title = "Developer RSVP Portal",
                description = "Portal pendaftaran webinar dan bootcamp teknologi sinkronisasi Ktor API server.",
                techStack = listOf("Kotlin", "Ktor", "Compose"),
                status = ProjectStatus.IN_PROGRESS
            ),
            Project(
                id = "proj-sec-01",
                communityId = "cybersecurity",
                title = "Campus CyberDefense Lab",
                description = "Platform analisis kerentanan jaringan WiFi kampus dan simulasi serangan CTF.",
                techStack = listOf("Python", "Ktor"),
                status = ProjectStatus.IN_PROGRESS
            ),
            Project(
                id = "proj-mob-01",
                communityId = "mobiledev",
                title = "Campus Event Tracker",
                description = "Aplikasi pemantau kegiatan dan event organisasi kampus berbasis Jetpack Compose.",
                techStack = listOf("Kotlin", "Jetpack Compose"),
                status = ProjectStatus.IN_PROGRESS
            )
        )
    )
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun toggleJoinCommunity(communityId: String) {
        _communities.update { list ->
            list.map { comm ->
                if (comm.id == communityId) {
                    comm.copy(isJoined = !comm.isJoined)
                } else {
                    comm
                }
            }
        }
    }

    fun addProject(communityId: String, title: String, description: String, techStack: List<String>) {
        val newProj = Project(
            id = "proj-$communityId-${UUID.randomUUID().toString().take(6)}",
            communityId = communityId,
            title = title,
            description = description,
            techStack = techStack,
            status = ProjectStatus.IN_PROGRESS
        )
        _projects.update { it + newProj }
    }

    fun deleteProject(projectId: String) {
        _projects.update { list -> list.filterNot { it.id == projectId } }
    }

    fun toggleProjectStatus(projectId: String) {
        _projects.update { list ->
            list.map { proj ->
                if (proj.id == projectId) {
                    val newStatus = if (proj.status == ProjectStatus.IN_PROGRESS) {
                        ProjectStatus.COMPLETED
                    } else {
                        ProjectStatus.IN_PROGRESS
                    }
                    proj.copy(status = newStatus)
                } else {
                    proj
                }
            }
        }
    }

    fun getCommunity(communityId: String): Community? {
        return _communities.value.find { it.id == communityId }
    }

    fun getProject(projectId: String): Project? {
        return _projects.value.find { it.id == projectId }
    }
}
