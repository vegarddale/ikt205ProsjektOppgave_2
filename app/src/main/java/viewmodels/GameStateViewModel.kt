package viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import data.Game
import services.GameService
import javax.inject.Inject


@HiltViewModel
class GameStateViewModel @Inject constructor(private val gameService: GameService):ViewModel() {


      fun testCreateGame(){
          val state = listOf(listOf(0,0,0), listOf(0,0,0), listOf(0,0,0))
          gameService.createGame("1", state) { game: Game?, err: Int? ->
            if (err != null){
                println("error: $err")
            }
              else {
                gameService.joinGame("2", game!!.gameId
                ) { game: Game?, err: Int? ->
                    if (err != null) {
                        println(err)
                    } else {
                        println(game!!.gameId)
                        println(game.state)
                        println(game.players)
                    }
                }
            }
            }
          }
}
