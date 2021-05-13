package com.uia.ikt205prosjektoppgave2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.uia.ikt205prosjektoppgave2.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint
import data.Game
import dialogs.CreateGameDialogFragment
import dialogs.GameDialogListener
import dialogs.JoinGameDialogFragment
import services.GameManager
import javax.inject.Inject

@AndroidEntryPoint
class MenuFragment : Fragment(), GameDialogListener {

    @Inject lateinit var gameManager: GameManager

    private var _binding : FragmentMenuBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuBinding.inflate(layoutInflater)

        binding.CreateGame.setOnClickListener{
            showCreateGameDialog()
        }

        binding.JoinGame.setOnClickListener{
            showJoinGameDialog()
        }

        return binding.root
    }

    private fun showCreateGameDialog(){
        val dialog = CreateGameDialogFragment()
        dialog.show(childFragmentManager, "CreateGameDialogFragment")

    }

    private fun showJoinGameDialog(){
        val dialog = JoinGameDialogFragment()
        dialog.show(childFragmentManager, "JoinGameDialogFragment")
    }

    override fun onDialogCreateGame(player: String) {
         gameManager.createGame(player, gameManager.startingGameState) { game: Game?, err:Int? ->
            if (err != null){
                Log.e("com.uia.ikt205prosjektoppgave2", "failed to create game network response: $err")
            }
            else{
                navigateToGame(1, game!!)
            }
        }
    }

    override fun onDialogJoinGame(player: String, gameId: String) {
        gameManager.joinGame(player, gameId){ game: Game?, err:Int? ->
            if (err != null){
                Log.e("com.uia.ikt205prosjektoppgave2", "failed to join game network response: $err")
            }
            else{
                navigateToGame(2, game!!)
            }
        }
    }

    private fun navigateToGame(playerNr:Int, game:Game) {
        val action = MenuFragmentDirections.actionMenuToGame(playerNr,game)
        view?.findNavController()?.navigate(action)
    }
}