package dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.uia.ikt205prosjektoppgave2.R
import com.uia.ikt205prosjektoppgave2.databinding.FragmentCreateGameDialogBinding
import com.uia.ikt205prosjektoppgave2.databinding.FragmentJoinGameDialogBinding
import java.lang.IllegalStateException

class CreateGameDialogFragment : DialogFragment() {

    lateinit var binding: FragmentCreateGameDialogBinding
    internal lateinit var listener : GameDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as GameDialogListener
            binding = FragmentCreateGameDialogBinding.inflate(requireParentFragment().layoutInflater)
        }catch (e: ClassCastException){
            throw ClassCastException((context.toString() +
                    " must implement GameDialogListener"))
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
                .setTitle("Create Game")
                .setPositiveButton(R.string.Create) { dialog, id ->
                    dialog.cancel()
                    listener.onDialogCreateGame(binding.playerId.text.toString())
                }
                .setNegativeButton(R.string.cancel) { dialog, id ->
                    dialog.cancel()
                }
                .setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Fragment cannot be null")
    }
}