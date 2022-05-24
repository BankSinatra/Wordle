package com.example.wordle.game

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.wordle.R

class GameEndDialogFragment : DialogFragment() {

    private val model: GameViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogView = inflater.inflate(R.layout.modal_end_game, null)
            builder.setView(dialogView)
                .setCancelable(false)
            val againButton: Button = dialogView.findViewById(R.id.play_again_button)
            val shareButton: Button = dialogView.findViewById(R.id.share_button)
            againButton.setOnClickListener {
                model.resetGame()
                dismiss()
            }
            shareButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                val shareBody = model.getMap()
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(intent, "share"))
                dismiss()
            }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}