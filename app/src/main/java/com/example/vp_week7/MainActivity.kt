package com.example.vp_week7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vp_week7.Tugas.ui.CampusMainApp
import com.example.vp_week7.ui.theme.VP_Week7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VP_Week7Theme {
                CampusMainApp()
            }
        }
    }
}
