package services

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.uia.ikt205prosjektoppgave2.R
import org.json.JSONObject
import com.google.gson.Gson
import data.Game
import data.GameState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class GameService (private val context: Context, private val apiEndpoints: APIEndpoints) {

    private val requestQue:RequestQueue = Volley.newRequestQueue(context)

    suspend fun createGame(playerId:String, state:GameState) {
        val url = apiEndpoints.CreateGameUrl()
        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state",state)

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    println("game created")
                    // Success game created.
                    val game = Gson().fromJson(it.toString(0), Game::class.java)
                },
            {
                println("error creating game")
            // Error creating new game.

        } ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }

        requestQue.add(request)
    }

    suspend fun joinGame(playerId:String, gameId:String){

    }

    suspend fun updateGame(gameId: String, gameState:GameState){

    }

    suspend fun pollGame(gameId: String){

    }
    }
