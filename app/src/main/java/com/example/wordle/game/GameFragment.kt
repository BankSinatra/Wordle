package com.example.wordle.game

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.forEach
import androidx.core.view.get
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
        binding.letterGrid.forEach {
            if (it is ViewGroup) {
                for (i in 0 until it.childCount) {
                    val child = it.getChildAt(i)
                    child.setOnClickListener {
                        showKeyBoard(binding.guess1, requireContext())
                    }
                }
            }
        }
    }
    fun findEditTextByWord(): View{
        val x = binding.guess1
        when (viewModel.currentWord){
            1 -> return binding.guess2
            2 -> return binding.guess3
            3 -> return binding.guess4
            4 -> return binding.guess5
            5 -> return binding.guess6
        }
        return x
    }
}



fun showKeyBoard(view: View, context: Context ){
    view.requestFocus()
    if (view.requestFocus()) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}