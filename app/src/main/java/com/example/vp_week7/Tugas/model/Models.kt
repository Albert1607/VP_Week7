package com.example.vp_week7.Tugas.model

enum class ProjectStatus(val label: String) {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed")
}

data class Community(
    val id: String,
    val name: String,
    val subtitle: String,
    val description: String,
    val memberCount: Int,
    val isJoined: Boolean = false
)

data class Project(
    val id: String,
    val communityId: String,
    val title: String,
    val description: String,
    val techStack: List<String>,
    val status: ProjectStatus = ProjectStatus.IN_PROGRESS
)

data class StudentProfile(
    val name: String = "Aditya Pratama",
    val department: String = "Teknik Informatika",
    val year: String = "Angkatan 2022",
    val nim: String = "22001045",
    val statusBadge: String = "MAHASISWA AKTIF"
)
