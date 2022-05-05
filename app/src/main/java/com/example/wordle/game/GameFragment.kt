package com.example.wordle.game

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
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
        //showKeyBoard(findEditTextByWord(), requireContext())
        binding.letterGrid.forEach { view ->
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val child = view.getChildAt(i)
                    child.setOnClickListener {
                        val ed = findEditTextByWord()
                        ed.requestFocus()
                        showKeyBoard(ed, requireContext())
                    }
                }
            }
        }
    }
    private fun findEditTextByWord(): EditText {
        return when (viewModel.currentWord){
            0 -> binding.guess1
            1 -> binding.guess2
            2 -> binding.guess3
            3 -> binding.guess4
            4 -> binding.guess5
            else -> binding.guess6
        }
    }
}

fun showKeyBoard(editText: EditText, context: Context){
    editText.requestFocus()

    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

}