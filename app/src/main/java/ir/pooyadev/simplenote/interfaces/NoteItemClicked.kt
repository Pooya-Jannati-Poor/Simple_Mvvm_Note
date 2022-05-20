package ir.pooyadev.simplenote.interfaces

import ir.pooyadev.simplenote.database.Note

interface NoteItemClicked {

    fun onDeleteItem(note: Note)

    fun onItemClicked(note: Note)

}