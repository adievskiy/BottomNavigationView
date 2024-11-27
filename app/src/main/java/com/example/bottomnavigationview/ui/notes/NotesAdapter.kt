package com.example.bottomnavigationview.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigationview.R

class NotesAdapter(
    private val notes: MutableList<Note>,
    private val onNoteClick: (Int) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteTV: TextView = view.findViewById(R.id.noteTV)
        val completeCHB: CheckBox = view.findViewById(R.id.completeCHB)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_list, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteTV.text = note.text
        holder.completeCHB.isChecked = note.isComplete

        holder.completeCHB.setOnCheckedChangeListener(null)
        holder.completeCHB.setOnCheckedChangeListener { _, isChecked ->
            note.isComplete = isChecked
            notifyItemChanged(position)
        }

        holder.itemView.setOnClickListener {
            onNoteClick(position)
        }
    }

    override fun getItemCount(): Int = notes.size

    fun removeNote(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }
}