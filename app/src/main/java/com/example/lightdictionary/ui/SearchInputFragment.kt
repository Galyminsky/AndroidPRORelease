package com.example.lightdictionary.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lightdictionary.R
import com.example.lightdictionary.databinding.FragmentSearchInputBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchInputFragment : BottomSheetDialogFragment() {
    private val binding by viewBinding(FragmentSearchInputBinding::bind)

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.searchButton.isEnabled = p0.toString().isNotEmpty()
        }
    }

    companion object {
        fun newInstance() = SearchInputFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButton.isEnabled = false
        binding.searchInputEditText.addTextChangedListener(textWatcher)
        binding.searchButton.setOnClickListener {
            (requireActivity() as Controller).setNewWord(binding.searchInputEditText.text.toString())
            dismiss()
        }
    }

    interface Controller {
        fun setNewWord(word: String)
    }
}