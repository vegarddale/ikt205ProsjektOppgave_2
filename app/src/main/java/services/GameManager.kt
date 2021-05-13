package services

import androidx.lifecycle.MutableLiveData
import data.Game
import data.GameState
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GameManager @Inject constructor(private val gameService: GameService) {

    var currentGame: MutableLiveData<Game> = MutableLiveData()
    var gameOver:MutableLiveData<Boolean> = MutableLiveData(false)
    val startingGameState: GameState = listOf(listOf("0","0","0"), listOf("0","0","0"), listOf("0","0","0"))
    var gameWinner = 0

    fun createGame(playerId: String, state: GameState, callback: GameServiceCallback) {
        gameService.createGame(playerId, state, callback)
    }

    fun joinGame(playerId: String, gameId: String, callback: GameServiceCallback) {
        gameService.joinGame(playerId, gameId, callback)
    }

    private fun pollGame() {
        gameService.pollGame { game: Game?, err: Int? ->
            if (err != null) {
                // TODO: 5/8/2021 give feedback
            } else {
                if(game != currentGame.value){
                    currentGame.postValue(game)
                    checkGameOver(game!!.state)
                }
            }
        }
    }

    suspend fun schedulePolling(){
        delay(5000)
        pollGame()
    }

    fun updateGame(updatedGame:Game) {
        gameService.updateGame(updatedGame) { game: Game?, err: Int? ->
            if (err != null) {
                // TODO: 5/8/2021 give feedback
            } else {
                currentGame.value = game
                checkGameOver(game!!.state)
            }
        }
    }

    private fun checkHorizontal(state: GameState):Boolean{
        for (row in 0..2){
            when(state[row][0]){
                "X" -> { if(state[row][0] == state[row][1] && state[row][0] == state[row][2]){
                    gameWinner = 1
                    return true
                }
                }
                "O" -> { if(state[row][0] == state[row][1] && state[row][0] == state[row][2]){
                    gameWinner = 2
                    return true
                }
                }
            }
        }
        return false
    }

    private fun checkVertical(state: GameState):Boolean{
        for (col in 0..2){
            when(state[0][col]){
                "X" -> { if(state[0][col] == state[1][col] && state[0][col] == state[2][col]){
                    gameWinner = 1
                    return true
                }
                }
                "O" -> { if(state[0][col] == state[1][col] && state[0][col] == state[2][col]){
                    gameWinner = 2
                    return true
                }
                }
            }
        }
        return false
    }

    private fun checkDiagonal(state: GameState):Boolean{
        when(state[0][0]){
            "X" -> { if(state[0][0] == state[1][1] && state[0][0] == state[2][2]){
                gameWinner = 1
                return true
            }
            }
            "O" -> { if(state[0][0] == state[1][1] && state[0][0] == state[2][2]){
                gameWinner = 2
                return true
            }
            }
        }

        when(state[0][2]){
            "X" -> {if(state[0][2] == state[1][1] && state[0][2] == state[2][0]){
                gameWinner = 1
                return true
            }
            }
            "O" -> {if(state[0][2] == state[1][1] && state[0][2] == state[2][0]){
                gameWinner = 2
                return true
            }
            }
        }
        return false
    }

    private fun checkGameOver(state: GameState){
        if (checkHorizontal(state)) gameOver.value = true
        if (checkDiagonal(state)) gameOver.value = true
        if (checkVertical(state)) gameOver.value = true
    }
}
