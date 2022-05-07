package com.example.wordle.game

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.wordle.R
import com.example.wordle.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var wordView: LinearLayout // Keeps track of the linearlayout that is edited

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordObserver = Observer<Int> { newNumber ->
            wordView = findLinearLayoutByNumber(newNumber)
        }
        viewModel.guessNumber.observe(viewLifecycleOwner, wordObserver)

        setClickListeners()
        keyboardSetUp()
    }


    private fun showKeyBoard(editText: EditText) {
        editText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setClickListeners() {
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

    //Filter for words. Alphabet ONLY
    class AlphabetFilter : InputFilter {
        override fun filter(
            source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int
        ): CharSequence {
            val pattern = Regex("^[a-zA-Z]+\$")
            if (source == null || source.isBlank()) {
                return ""
            }
            return if (source.isNotEmpty() && source.matches(pattern)) {
                source
            } else {
                ""
            }
        }
    }

    private fun keyboardSetUp() {
        //When you press the submit button on the keyboard...
        binding.guessField.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    //Check to see if the string length is 5 then submit it.
                    if (viewModel.guess.value?.length == 5) {
                        if(viewModel.wordValidation(viewModel.guess.value!!)){
                            changeBackground(wordView)
                            removeClickListeners(wordView) // You can't interact with submitted blocks after submitting
                            viewModel.submitWord()
                        }else{
                            //TODO: Let the user know that this is not a real word
                            Toast.makeText(context, "Not in word list", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        //TODO: Let the user know that the word is not long enough
                        Toast.makeText(context, "Not enough letters", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
        val letterFilter = AlphabetFilter()
        val maxLength = InputFilter.LengthFilter(5)
        binding.guessField.filters = arrayOf(letterFilter, maxLength)
        binding.guessField.doOnTextChanged { text, start, before, count ->
            wordView.forEach {
                if (it is TextView) {
                    it.text = ""
                    it.setBackgroundResource(R.drawable.letter_block_unsubmitted)
                }
                if (binding.guessField.text.isNotEmpty()) { //I should be using the viewModel liveData actually but whatevs
                    for (letterIndex in binding.guessField.text.indices) {
                        val child = wordView.getChildAt(letterIndex)
                        if (child is TextView) {
                            child.text = binding.guessField.text[letterIndex].toString().uppercase()
                            child.setBackgroundResource(R.drawable.letter_block_unsubmitted_input)
                        }
                    }
                }
            }
        }
    }

    //This makes certain square rows un-Clickable
    private fun removeClickListeners(wordRow: ViewGroup) {
        wordRow.forEach {
            it.isClickable = false
        }
    }

    private fun findLinearLayoutByNumber(number: Int): LinearLayout {
        return when (number) {
            0 -> binding.word1
            1 -> binding.word2
            2 -> binding.word3
            3 -> binding.word4
            4 -> binding.word5
            else -> binding.word6
        }
    }

    private fun changeBackground(view: LinearLayout){
        val colorMap = viewModel.guess.value?.let { viewModel.evaluateWord(it) }
        for (letterIndex in 0 until view.childCount) {
            val child = wordView.getChildAt(letterIndex)
            if (colorMap?.get(letterIndex) == LetterState.GREEN) {
                child.setBackgroundResource(R.drawable.letter_block_green)
            }else if(colorMap?.get(letterIndex) == LetterState.YELLOW){
                child.setBackgroundResource(R.drawable.letter_block_yellow)
            }else{
                child.setBackgroundResource(R.drawable.letter_block_wrong)
            }
        }
    }
}


