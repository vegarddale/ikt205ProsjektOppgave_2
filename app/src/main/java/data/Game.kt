package data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable



typealias GameState = List<List<String>>

@Parcelize
data class Game(val players:MutableList<String>, val gameId:String, var state:GameState ): Parcelable
