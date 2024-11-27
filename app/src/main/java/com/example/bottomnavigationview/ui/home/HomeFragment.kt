package com.example.bottomnavigationview.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bottomnavigationview.R
import com.example.bottomnavigationview.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isEditable = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editIV.setOnClickListener {
            when (isEditable) {
                false -> {
                    val text = binding.nameTV.text.toString()
                    binding.nameTV.visibility = View.GONE
                    binding.editNameET.setText(text)
                    binding.editNameET.visibility = View.VISIBLE
                    binding.editNameET.requestFocus()
                    binding.editNameET.setSelection(binding.editNameET.getText().length)
                    binding.editIV.setImageResource(R.drawable.ic_done)
                    isEditable = true
                }

                true -> {
                    val text = binding.editNameET.text.toString()
                    binding.editNameET.visibility = View.GONE
                    binding.nameTV.visibility = View.VISIBLE
                    binding.nameTV.text = text
                    binding.editIV.setImageResource(R.drawable.ic_edit)
                    isEditable = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}