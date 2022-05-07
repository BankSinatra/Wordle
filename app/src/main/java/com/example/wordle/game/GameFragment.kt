package com.example.wordle.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wordle.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private val viewModel: GameViewModel by viewModels()
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun showKeyBoard(editText: EditText){
        editText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

    }

    private fun setClickListeners(){
        binding.letterGrid.forEach {
            if (it is ViewGroup) {
                //For each item in the viewGroup (Each row)...
                for (i in 0 until it.childCount) {
                    //..Set a click listener for the item
                    val child = it.getChildAt(i)
                    child.setOnClickListener {
                        showKeyBoard(binding.guessField)
                    }
                }
            }
        }
    }

    //This makes certain square rows un-Clickable
    private fun removeClickListeners(wordRow: ViewGroup){
        wordRow.forEach {
            it.isClickable = false
        }
    }
}

