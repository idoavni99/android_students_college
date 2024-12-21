package com.example.stundensmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.example.stundensmanager.enums.StudentDetailsMode
import com.example.stundensmanager.fragments.StudentsListFragment

class MainActivity : AppCompatActivity() {
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        NavigationUI.setupWithNavController(
            toolbar,
            navHostFragment.navController,
            AppBarConfiguration.Builder().build()
        )
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                R.id.studentsListFragment -> {
                    toolbar.navigationIcon = null
                }

                R.id.saveStudentFragment -> {
                    (args?.getSerializable("mode") as? StudentDetailsMode)?.let {
                        if (it == StudentDetailsMode.ADD) {
                            toolbar.title = "Add Student"
                        } else {
                            toolbar.title = "Edit Student"
                        }
                    }
                }
            }
        }
    }
}