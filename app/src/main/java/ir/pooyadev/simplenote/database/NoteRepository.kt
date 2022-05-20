package ir.pooyadev.simplenote.database

import androidx.lifecycle.LiveData

class NoteRepository(private val notesDAO: NotesDAO) {

    val allNotes: LiveData<List<Note>> = notesDAO.getAllNotes()

    suspend fun insertNote(note: Note) {

        notesDAO.insertNote(note)

    }

    suspend fun updateNote(note: Note) {

        notesDAO.updateNote(note)

    }

    suspend fun deleteNote(note: Note) {

        notesDAO.deleteNote(note)

    }

}