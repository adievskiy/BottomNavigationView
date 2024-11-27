package com.example.bottomnavigationview.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigationview.databinding.FragmentNotesBinding

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesAdapter
    private val notes = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NotesAdapter(notes) { position ->
            adapter.removeNote(position)
        }
        binding.notesRV.adapter = adapter
        binding.notesRV.layoutManager = LinearLayoutManager(context)

        binding.saveNoteBTN.setOnClickListener {
            val textNote = binding.newNoteET.text.toString()
            val newNote = Note(textNote, false)
            notes.add(Note(textNote))
            adapter.notifyItemInserted(notes.size)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}