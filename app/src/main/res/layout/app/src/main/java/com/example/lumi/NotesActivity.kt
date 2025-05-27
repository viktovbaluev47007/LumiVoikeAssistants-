package com.example.lumi

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NotesActivity : AppCompatActivity() {

    private lateinit var notesEditText: EditText
    private lateinit var btnSave: Button

    private val PREFS_NAME = "LumiNotes"
    private val KEY_NOTES = "notes_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        notesEditText = findViewById(R.id.notesEditText)
        btnSave = findViewById(R.id.btnSave)

        // Загружаем сохранённые заметки
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        notesEditText.setText(prefs.getString(KEY_NOTES, ""))

        btnSave.setOnClickListener {
            val text = notesEditText.text.toString()
            prefs.edit().putString(KEY_NOTES, text).apply()
            Toast.makeText(this, "Заметки сохранены", Toast.LENGTH_SHORT).show()
        }
    }
}
