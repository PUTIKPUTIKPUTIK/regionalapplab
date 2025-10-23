package com.example.regionalapplab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.regionalapplab.ui.screens.EmployeeListScreen
import com.example.regionalapplab.ui.theme.RegionalapplabTheme
import com.example.regionalapplab.ui.viewmodel.EmployeeViewModel

class MainActivity : ComponentActivity() {

    private val employeeViewModel: EmployeeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegionalapplabTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    EmployeeListScreen()
                }
            }
        }
    }
}