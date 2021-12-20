package com.example.lightdictionary.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lightdictionary.R
import com.example.lightdictionary.utils.viewById
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SearchInputFragment : BottomSheetDialogFragment() {
    private val searchButton by viewById<MaterialButton>(R.id.search_button)
    private val searchInputEditText by viewById<TextInputEditText>(R.id.search_input_edit_text)

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            searchButton.isEnabled = p0.toString().isNotEmpty()
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
        searchButton.isEnabled = false
        searchInputEditText.addTextChangedListener(textWatcher)
        searchButton.setOnClickListener {
            (requireActivity() as Controller).setNewWord(searchInputEditText.text.toString())
            dismiss()
        }
    }

    interface Controller {
        fun setNewWord(word: String)
    }
}