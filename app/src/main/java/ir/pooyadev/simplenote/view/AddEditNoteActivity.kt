package ir.pooyadev.simplenote.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ir.pooyadev.simplenote.viewmodel.NoteViewModel
import ir.pooyadev.simplenote.database.Note
import pooyadev.mvvmnote.R
import pooyadev.mvvmnote.databinding.ActivityAddEditNoteBinding

class AddEditNoteActivity : AppCompatActivity() {

    private lateinit var bindingActivity: ActivityAddEditNoteBinding

    private lateinit var edTitle: EditText
    private lateinit var edDescription: EditText
    private lateinit var btnAddUpdate: Button

    private lateinit var noteViewModel: NoteViewModel
    var noteId = -1
    private lateinit var noteType: String
    private lateinit var noteTitle: String
    private lateinit var noteDescription: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingActivity = DataBindingUtil.setContentView(this, R.layout.activity_add_edit_note)

        initView()

        initViewModel()

        getNoteTypeFromBundle()

        addEditClick()

    }

    private fun initView() {

        edTitle = bindingActivity.edTitle
        edDescription = bindingActivity.edDescription
        btnAddUpdate = bindingActivity.btnAddUpdate

    }

    private fun initViewModel() {

        noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

    }

    private lateinit var btnAddUpdateText: String

    private fun getNoteTypeFromBundle() {

        noteType = intent.getStringExtra("noteType")!!

        if (noteType == "Edit") {

            getNoteDataFromBundleInEditMode()

            setNoteDataToView()

        } else {

            btnAddUpdateText = "Save Note"

            setBtnAddUpdateText()

        }

    }

    private fun getNoteDataFromBundleInEditMode() {

        noteId = intent.getIntExtra("noteId", -1)
        noteTitle = intent.getStringExtra("noteTitle")!!
        noteDescription = intent.getStringExtra("noteDescription")!!

    }

    private fun setNoteDataToView() {

        btnAddUpdateText = "Update Note"
        edTitle.setText(noteTitle)
        edDescription.setText(noteDescription)

        setBtnAddUpdateText()

    }

    private fun setBtnAddUpdateText() {

        btnAddUpdate.text = btnAddUpdateText

    }

    private fun addEditClick() {

        btnAddUpdate.setOnClickListener {

            noteTitle = edTitle.text.toString()
            noteDescription = edDescription.text.toString()

            if (noteType == "Edit") {

                updateNoteIntoDatabase()

                Toast.makeText(this@AddEditNoteActivity, "Note Updated ...", Toast.LENGTH_SHORT)
                    .show()

            } else {

                insertNoteIntoDatabase()

                Toast.makeText(this@AddEditNoteActivity, "Note Added ...", Toast.LENGTH_SHORT)
                    .show()

            }

            startMainActivity()

        }

    }

    private fun updateNoteIntoDatabase() {

        noteViewModel.updateNoteInDatabase(noteId, noteTitle, noteDescription)

    }

    private fun insertNoteIntoDatabase() {

        noteViewModel.insertNoteInDatabase(
            Note(
                noteTitle = noteTitle,
                noteDescription = noteDescription,
                timestamp = noteViewModel.getCurrentDate()
            )
        )

    }

    private fun startMainActivity() {

        startActivity(Intent(applicationContext, MainActivity::class.java))
        this.finish()

    }

}