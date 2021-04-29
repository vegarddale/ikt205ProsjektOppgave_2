package services
import android.content.Context
import com.uia.ikt205prosjektoppgave2.R


class APIEndpoints(private val context:Context) { // TODO: 4/29/2021 skal vel helst være singleton

    var currentGameId:String? = null

    fun CreateGameUrl():String{
        return "%1s%2s%3s".format(context.getString(R.string.protocol), context.getString(R.string.domain),context.getString(R.string.base_path))
    }

    fun JoinGameUrl():String{

        if(currentGameId == null){
            throw Exception("Current game id is not set")
        }

        return "%1s%2s%3s%4s".format(context.getString(R.string.protocol), context.getString(R.string.domain),context.getString(R.string.base_path),
            context.getString(R.string.join_game_path).format(currentGameId))
    }
}
