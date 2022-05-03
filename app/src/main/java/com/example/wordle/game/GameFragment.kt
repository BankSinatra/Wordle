package com.example.wordle.game

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
                    //TODO: Do something here
                }
            }
        }
        val m = findChildAtIndex(binding.letterGrid[viewModel.currentLetter], viewModel.currentWord)
        Log.i("Word", resources.getResourceName(m.id))


    }
}


fun findChildAtIndex(view: View, index: Int) : View{
    var myView = view
    if (view is ViewGroup) {
        myView = view[index]

    }
    return myView
}