package com.example.wordle.game

//Fragment code. Determines how the xml interacts with the game and also

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.CycleInterpolator
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.wordle.R
import com.example.wordle.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private val viewModel: GameViewModel by activityViewModels()
    private lateinit var wordView: LinearLayout // Keeps track of the linearlayout that is being edited
    lateinit var colorMap: Map<Int, LetterState>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val wordObserver = Observer<Int> { newNumber ->
            //onChanged()
            wordView = findLinearLayoutByNumber(newNumber)
        }
        viewModel.guessNumber.observe(viewLifecycleOwner, wordObserver)

        val gameEndStateObserver = Observer<GameEndState> {
            //onChanged()
            if (it.gameEnded) {
                if (it.gameWon == false) {
                    showStatusAnimation(WordStatus.WORD, binding.textStatus)
                }
            } else if (!it.gameEnded) {
                playAgain()
            }
        }
        viewModel.gameEndState.observe(viewLifecycleOwner, gameEndStateObserver)
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.gameEndState.value?.gameEnded == true){
            showGameEndDialog()
        }
    }

    private fun showKeyBoard(editText: EditText) {
        //Open keyboard manually
        editText.requestFocus()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setClickListeners() {
        binding.letterGrid.forEach {
            if (it is ViewGroup) {
                //For each item in the viewGroup (Each row)...
                for (i in 0 until it.childCount) {
                    //..Set a click listener for the item to open the keyboard
                    val child = it.getChildAt(i) ?: return
                    child.setBackgroundResource(R.drawable.letter_block_unsubmitted)
                    if (child is TextView) {
                        child.text = ""
                    }
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
                    val string = viewModel.guess.value
                    //Check to see if the string is 5 letters long then submit it.
                    if (string?.length == 5) {
                        if (viewModel.wordValidation(string)) {
                            colorMap = viewModel.evaluateWord(string)
                            wordRevealAnimation(wordView)
                            removeClickListeners(wordView) // You can't interact with submitted blocks after submitting
                        } else {
                            invalidationShakeAnimation(wordView)
                            showStatusAnimation(WordStatus.NON_WORD, binding.textStatus)
                        }
                    } else {
                        invalidationShakeAnimation(wordView)
                        showStatusAnimation(WordStatus.TOO_SHORT, binding.textStatus)
                    }
                    true
                }
                else -> false
            }
        }
        val letterFilter = AlphabetFilter()
        val maxLengthFilter = InputFilter.LengthFilter(5)
        binding.guessField.filters = arrayOf(letterFilter, maxLengthFilter)
        binding.guessField.doOnTextChanged { _, _, _, _ ->
            wordView.forEach {
                if (it is TextView) {
                    it.text = ""
                    it.setBackgroundResource(R.drawable.letter_block_unsubmitted)
                }
                if (binding.guessField.text.isNotEmpty()) { //I should be using the viewModel liveData actually but whatever
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

    private fun changeBackground(letterIndex: Int, colorMap: Map<Int, LetterState>): Int {
        return when {
            colorMap[letterIndex] == LetterState.GREEN -> {
                R.drawable.letter_block_green
            }
            colorMap[letterIndex] == LetterState.YELLOW -> {
                R.drawable.letter_block_yellow
            }
            else -> {
                R.drawable.letter_block_wrong
            }
        }
    }

    private fun showStatusAnimation(status: WordStatus, view: TextView) {
        view.visibility = View.VISIBLE
        view.alpha = 1f
        var delay = 0
        when (status) {
            WordStatus.TOO_SHORT -> {
                view.text = resources.getText(R.string.short_word)
                delay = 1800
            }
            WordStatus.NON_WORD -> {
                view.text = resources.getText(R.string.non_word)
                delay = 1800
            }
            WordStatus.WORD -> {
                view.text = viewModel.getWord()
                delay = 2800
            }
        }
        view.postDelayed({
            viewModel.gameEndState.value?.let {
                viewFadeOutAnimation(
                    view,
                    it.gameEnded
                )
            }
        }, delay.toLong())
    }

    private fun invalidationShakeAnimation(view: View) {
        val objInterpolator = CycleInterpolator(3f)
        val translateRight = ObjectAnimator.ofFloat(view, "translationX", 40f)
        translateRight.duration = 75
        val translateLeft = ObjectAnimator.ofFloat(view, "translationX", -80f)
        translateLeft.duration = 75
        AnimatorSet().apply {
            playSequentially(translateRight, translateLeft)
            interpolator = objInterpolator
            if (!this.isRunning) {
                start()
            }
        }
    }

    private fun wordRevealAnimation(linearLayout: ViewGroup) {
        var offsetTime = 0
        for (index in 0 until linearLayout.childCount) {
            val child = linearLayout.getChildAt(index)
            child.rotationX = 0f
            child.animate().apply {
                startDelay = offsetTime.toLong()
                rotationX(90f)
                setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        child.setBackgroundResource(changeBackground(index, colorMap))
                        child.rotationX = 270f
                        child.animate().rotationX(360f).setListener(null)
                        setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                //After the last block has finished animating
                                if (index == 4) {
                                    if (viewModel.guess.value?.let { viewModel.wordCheck(it) } == true) {
                                        correctWordAnimation(linearLayout)
                                    } else {
                                        viewModel.submitWord()
                                    }
                                }
                            }
                        })
                    }
                })
            }
            offsetTime += 150
        }
    }

    private fun correctWordAnimation(linearLayout: ViewGroup){
        var offsetTime = 0
        for (index in 0 until linearLayout.childCount){
            val child = linearLayout.getChildAt(index)
            child.animate().apply {
                startDelay = offsetTime.toLong()
                translationYBy(-80f)
                setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        translationYBy(80f)
                        setListener(object : AnimatorListenerAdapter(){
                            override fun onAnimationEnd(animation: Animator?) {
                                if(index == 4){
                                    showGameEndDialog()
                                }
                            }
                        })
                    }
                })
            }
            offsetTime += 170
        }
    }

    private fun viewFadeOutAnimation(view: View, gameEnd: Boolean) {
        view.animate().apply {
            alpha(0f)
            duration = 200
            setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    if (gameEnd) {
                        showGameEndDialog()
                    }
                }
            })
        }
    }

    private fun showGameEndDialog() {
        val dialog = GameEndDialogFragment()
        dialog.show(requireActivity().supportFragmentManager, "GameEnded")
    }

    private fun playAgain() {
        setClickListeners()
        keyboardSetUp()
    }
}