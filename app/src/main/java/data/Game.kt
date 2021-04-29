package data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


typealias GameState = List<List<Int>>

@Parcelize
data class Game(val players:MutableList<String>, val gameId:String, val state:GameState ) : Parcelable {
}