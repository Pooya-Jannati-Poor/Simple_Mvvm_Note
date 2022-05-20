package ir.pooyadev.simplenote.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(newNote: Note)

    @Update
    suspend fun updateNote(existedNote: Note)

    @Delete
    suspend fun deleteNote(existedNote: Note)

    @Query("SELECT * FROM notesTable ORDER BY id ASC")
    fun getAllNotes(): LiveData <List<Note>>

}