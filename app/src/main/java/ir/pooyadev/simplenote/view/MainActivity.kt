package ir.pooyadev.simplenote.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ir.pooyadev.simplenote.adapter.AdapterRecNote
import ir.pooyadev.simplenote.interfaces.NoteItemClicked
import ir.pooyadev.simplenote.viewmodel.NoteViewModel
import ir.pooyadev.simplenote.database.Note
import pooyadev.mvvmnote.R
import pooyadev.mvvmnote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NoteItemClicked {

    private lateinit var bindingActivity: ActivityMainBinding

    private lateinit var recNote: RecyclerView
    private lateinit var fabAddNote: FloatingActionButton

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()

        initRecNote()

        fabClickListener()

    }

    private fun initView() {

        recNote = bindingActivity.recNote
        fabAddNote = bindingActivity.fabAddNote

    }

    private lateinit var adapterNote: AdapterRecNote

    private fun initRecNote() {

        val layoutManager = LinearLayoutManager(this)

        adapterNote = AdapterRecNote(this, this)
        recNote.layoutManager = layoutManager
        recNote.adapter = adapterNote

        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        noteViewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                adapterNote.updateList(it as ArrayList<Note>)
            }
        })

    }

    private fun fabClickListener() {

        fabAddNote.setOnClickListener {

            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            intent.putExtra("noteType", "Add")
            startActivity(intent)
            this.finish()

        }

    }

    override fun onDeleteItem(note: Note) {

        noteViewModel.deleteNoteFromDatabase(note)

        Toast.makeText(this@MainActivity, "${note.noteTitle} Deleted", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClicked(note: Note) {

        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteId", note.id)
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        startActivity(intent)
        this.finish()

    }
}