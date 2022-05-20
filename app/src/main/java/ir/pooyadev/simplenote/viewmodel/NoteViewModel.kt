package ir.pooyadev.simplenote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ir.pooyadev.simplenote.database.Note
import ir.pooyadev.simplenote.database.NoteDatabase
import ir.pooyadev.simplenote.database.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {

        val dao = NoteDatabase.getDatabase(application).getNotesDAO()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes

    }

    private fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {

        repository.insertNote(note)

    }

    private fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {

        repository.updateNote(note)

    }

    private fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {

        repository.deleteNote(note)

    }

    fun updateNoteInDatabase(noteId: Int, noteTitle: String, noteDescription: String) {

        val updateNote = Note(
            id = noteId,
            noteTitle = noteTitle,
            noteDescription = noteDescription,
            timestamp = getCurrentDate()
        )

        updateNote(updateNote)

    }

    fun insertNoteInDatabase(note: Note) {

        insertNote(note)

    }

    fun deleteNoteFromDatabase(note: Note) {

        deleteNote(note)

    }

    fun getCurrentDate(): String {

        val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm", Locale.ENGLISH)

        return sdf.format(Date())

    }
}