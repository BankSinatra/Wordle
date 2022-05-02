package com.example.wordle.game

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
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
        binding.letterGrid.forEach {
            //Log.i("letter!", "word id: ${resources.getResourceName(it.id)}")
            if (it is ViewGroup){
                for (i in 0 until it.childCount) {
                    val child = it.getChildAt(i)
                    child.setOnClickListener{
                        child.isFocusableInTouchMode = true //This will create bugs in the future btw
                        showKeyboard(child,requireContext())
                    }
                    //Log.i("letter!", "word ${i+1} letter id: ${resources.getResourceName(child.id)}")
                }
            }
        }
    }
}

    fun showKeyboard(view: View, context: Context) {
        if (view.requestFocus()) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
