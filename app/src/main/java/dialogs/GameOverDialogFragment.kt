package dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.uia.ikt205prosjektoppgave2.databinding.FragmentGameOverDialogBinding
import java.lang.IllegalStateException

class GameOverDialogFragment : DialogFragment() {
    lateinit var binding:FragmentGameOverDialogBinding
    internal lateinit var listener: GameOverDialogListener
    var winner:String? = null

    interface GameOverDialogListener{
        fun onDialogGameOver(dialog:GameOverDialogFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            winner = it.getString("winner")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as GameOverDialogListener
            binding = FragmentGameOverDialogBinding.inflate(requireParentFragment().layoutInflater)

        }catch (e: ClassCastException){
            throw ClassCastException((context.toString() +
                    " must implement GameDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            binding.gameWinner.text = winner
            val builder = AlertDialog.Builder(it)
                .setPositiveButton("Exit") { dialog, id ->
                    dialog.cancel()
                    listener.onDialogGameOver(this)
                }
                .setView(binding.root)
            builder.create()
        } ?: throw IllegalStateException("Fragment cannot be null")
    }

    companion object {
        @JvmStatic
        fun newInstance(gameWinner: String) =
                GameOverDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString("winner", gameWinner)
                    }
                }
    }
}