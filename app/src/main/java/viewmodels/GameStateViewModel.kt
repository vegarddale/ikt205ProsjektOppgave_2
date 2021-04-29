package viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.GameState
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import services.GameManager
import services.GameService
import javax.inject.Inject

@HiltViewModel
class GameStateViewModel @Inject constructor(private val gameService: GameService):ViewModel() {

     fun test(){
        viewModelScope.launch {
            gameService.createGame("1", listOf(listOf(1,2,3)))
        }
    }
}