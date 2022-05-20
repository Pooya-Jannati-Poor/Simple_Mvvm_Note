package ir.pooyadev.simplenote.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.pooyadev.simplenote.interfaces.NoteItemClicked
import ir.pooyadev.simplenote.database.Note
import pooyadev.mvvmnote.R
import pooyadev.mvvmnote.databinding.LayoutRecNoteBinding

class AdapterRecNote(
    private val context: Context,
    private val noteItemClicked: NoteItemClicked
) :
    RecyclerView.Adapter<AdapterRecNote.ItemAdapter>() {

    private lateinit var bindingAdapter: LayoutRecNoteBinding

    private val lsNote = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter {
        val inflater = LayoutInflater.from(context)

        bindingAdapter = DataBindingUtil.inflate(inflater, R.layout.layout_rec_note, parent, false)

        return ItemAdapter(bindingAdapter)

    }

    override fun onBindViewHolder(holder: ItemAdapter, position: Int) {

        val model = lsNote[position]
        holder.bind(model)

        holder.itemView.setOnClickListener {
            noteItemClicked.onItemClicked(model)
        }

        holder.imgDeleteNote.setOnClickListener {
            noteItemClicked.onDeleteItem(model)
        }

    }

    override fun getItemCount(): Int = lsNote.size

    inner class ItemAdapter(itemView: LayoutRecNoteBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val imgDeleteNote: ImageView = itemView.imgDeleteNote

        fun bind(note: Note) {

            bindingAdapter.itemNote = note

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newNoteList: ArrayList<Note>) {
        lsNote.clear()
        lsNote.addAll(newNoteList)
        notifyDataSetChanged()
    }

}