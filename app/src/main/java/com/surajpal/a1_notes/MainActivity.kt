package com.surajpal.a1_notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.surajpal.a1_notes.database.NoteDatabase
import com.surajpal.a1_notes.repository.NoteRepository
import com.surajpal.a1_notes.viewmodel.NoteViewModel
import com.surajpal.a1_notes.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        val noteRepository = NoteRepository(NoteDatabase.getDatabase(this))
        val noteViewModelFactory = NoteViewModelFactory(application,noteRepository)
        noteViewModel = ViewModelProvider(this,noteViewModelFactory)[NoteViewModel::class.java]

    }
}