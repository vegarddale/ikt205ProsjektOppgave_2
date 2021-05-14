package com.uia.ikt205prosjektoppgave2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.uia.ikt205prosjektoppgave2.databinding.FragmentGameBinding
import dagger.hilt.android.AndroidEntryPoint
import data.Game
import dialogs.GameOverDialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import services.GameManager
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : Fragment(), GameOverDialogFragment.GameOverDialogListener {

    @Inject lateinit var gameManager: GameManager

    private var _binding: FragmentGameBinding? = null
    val binding get() = _binding!!
    private val args : GameFragmentArgs by navArgs()
    lateinit var currentgame:Game

    var lastClickedButton : Button? = null
    
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(layoutInflater)
        currentgame = args.initialGame
        gameManager.currentGame.value = currentgame
        updateGameUi(currentgame)
        setButtonListeners(args.PlayerNr)
        startPolling()

        binding.commit.setOnClickListener{
            if(lastClickedButton == null){
                Toast.makeText(requireActivity(), "Please select a square before comitting", Toast.LENGTH_SHORT).show()
            }
            else{
                currentgame.state = getUpdatedBoard()
                gameManager.updateGame(currentgame)
                lastClickedButton?.isEnabled = false
                lastClickedButton = null
            }
        }

        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameManager.currentGame.observe(viewLifecycleOwner, {
            updateGameUi(it)
            disableButtons()
        })
        gameManager.gameOver.observe(viewLifecycleOwner, {
            if (it == true) {
                val players = gameManager.currentGame.value!!.players
                when(gameManager.gameWinner){
                    1 -> showGameOverDialog(players[0])
                    2 -> showGameOverDialog(players[1])
                }
            }
        })
        gameManager.draw.observe(viewLifecycleOwner, {
            if(it==true){
                showGameOverDialog("Draw")
            }
        })
    }



     private fun getUpdatedBoard():List<List<String>>{
         val board = mutableListOf(mutableListOf("","",""), mutableListOf("","",""), mutableListOf("","",""))
        for(i in 0..2){
            for(j in 0..2){
                val row = binding.gameLayout.getChildAt(i) as LinearLayout
                val col = row.getChildAt(j) as Button
                board[i][j] = col.text.toString()
            }
        }
         return board
    }

    private fun disableButtons(){
        when(binding.commit.isClickable){
            true -> binding.commit.isClickable = false
            false -> binding.commit.isClickable = true
        }
        for(i in 0..2){
            for(j in 0..2){
                val row = binding.gameLayout.getChildAt(i) as LinearLayout
                val btn = row.getChildAt(j) as Button
                when(btn.isClickable){
                    true -> btn.isClickable = false
                    false -> btn.isClickable = true
                }
            }
        }
    }

    private fun enableButtons(){
        for(i in 0..2){
            for(j in 0..2){
                val row = binding.gameLayout.getChildAt(i) as LinearLayout
                val btn = row.getChildAt(j) as Button
                btn.isClickable = true
            }
        }
    }
    private fun updateGameUi(currentGame: Game){
        for(i in 0..2){
            for(j in 0..2){
                val row = binding.gameLayout.getChildAt(i) as LinearLayout
                val col = row.getChildAt(j) as Button
                col.text = currentGame.state[i][j]
            }
        }
        binding.gameId.text = currentGame.gameId
        binding.player1.text = currentGame.players[0]
        if (currentGame.players.size > 1){
            binding.player2.text = currentGame.players[1]
        }
    }

    private fun startPolling(){
        GlobalScope.launch {
            while(true){
                gameManager.schedulePolling()
            }
        }
    }

    private fun setButtonListeners(playerNr:Int){
        var previousClickedButton:Button? = null
        for(i in 0..2){
            for(j in 0..2){
                val row = binding.gameLayout.getChildAt(i) as LinearLayout
                val btn = row.getChildAt(j) as Button

                when(playerNr){
                    1 -> btn.setOnClickListener{
                        if(btn.text != "O"){
                            if(previousClickedButton?.isEnabled == true) previousClickedButton?.text = "0"
                            btn.text = "X"
                            previousClickedButton = btn
                            lastClickedButton = btn
                        }
                    }
                    2 -> btn.setOnClickListener {
                        if(btn.text != "X"){
                            if(previousClickedButton?.isEnabled == true) previousClickedButton?.text = "0"
                            btn.text = "O"
                            previousClickedButton = btn
                            lastClickedButton = btn
                        }
                    }
                }
            }
        }
    }

    private fun showGameOverDialog(gameWinner:String){
        val dialog = GameOverDialogFragment.newInstance(gameWinner)
        dialog.show(childFragmentManager, "GameOverDialogFragment")
    }

    override fun onDialogGameOver(dialog:GameOverDialogFragment) {
        gameManager.gameOver.value = false
        enableButtons()
        view?.findNavController()?.navigateUp()
    }
}