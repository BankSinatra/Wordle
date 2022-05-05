package com.example.wordle.game

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.widget.doOnTextChanged
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
        binding.letterGrid.forEach {
            if (it is ViewGroup) {
                for (i in 0 until it.childCount) {
                    val child = it.getChildAt(i)
                    child.setOnClickListener {
                        showKeyBoard(findEditTextByWord(), requireContext())
                    }
                }
            }
        }
        findEditTextByWord().doOnTextChanged { text, start, before, count ->
            val linearLayout = findLinearLayoutByWord()
            linearLayout.forEach {
                if (it is TextView){
                    it.text =""
                }
                if (findEditTextByWord().text.isNotEmpty()){
                    for (x in findEditTextByWord().text.indices){
                        val child =  linearLayout.getChildAt(x)
                        if (child is TextView){
                            child.text = findEditTextByWord().text[x].toString().uppercase()
                        }
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

    private fun findLinearLayoutByWord() : LinearLayout{
        return when (viewModel.currentWord) {
            0 -> binding.word1
            1 -> binding.word2
            2 -> binding.word3
            3 -> binding.word4
            4 -> binding.word5
            else -> binding.word6
        }
    }
}

fun showKeyBoard(editText: EditText, context: Context){
    editText.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)

}