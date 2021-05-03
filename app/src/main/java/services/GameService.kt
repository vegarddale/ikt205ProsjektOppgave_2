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
import org.json.JSONArray
import javax.inject.Singleton

typealias GameServiceCallback = (state:Game?, errorCode:Int? ) -> Unit

@Singleton
class GameService (private val context: Context, private val apiEndpoints: APIEndpoints){

    private val requestQue:RequestQueue = Volley.newRequestQueue(context)

     fun createGame(playerId:String, state:GameState, callback:GameServiceCallback) {
        val url = apiEndpoints.createGameUrl()
        val requestData = JSONObject().apply {
            put("player", playerId)
            put("state", JSONArray(state))
        }

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    println("game created")
                    val game = (Gson().fromJson(it.toString(0), Game::class.java))
                    apiEndpoints.currentGameId = game.gameId
                    callback(game,null)

                }, {

            callback(null, it.networkResponse.statusCode)
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

    fun joinGame(playerId:String, gameId:String, callback: GameServiceCallback){
        val url = apiEndpoints.joinGameUrl()
        val requestData = JSONObject().apply {
            put("player", playerId)
            put("state", gameId)
        }

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    val game = (Gson().fromJson(it.toString(0), Game::class.java))
                    println("joined game with id: ${game.gameId}")
                    callback(game,null)

                }, {

            callback(null, it.networkResponse.statusCode)
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

    fun updateGame(gameId: String, state:GameState, callback: GameServiceCallback){
        val url = apiEndpoints.updateGameUrl()
        val requestData = JSONObject().apply {
            put("gameId", gameId)
            put("state", JSONArray(state))
        }

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    println("game updated")
                    val game = (Gson().fromJson(it.toString(0), Game::class.java))
                    callback(game,null)

                }, {
            callback(null, it.networkResponse.statusCode)
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

    fun pollGame(gameId: String,callback:GameServiceCallback){
        val url = apiEndpoints.pollGameUrl()
        val requestData = JSONObject().apply {
            put("gameId", gameId)
        }

        val request = object : JsonObjectRequest(Request.Method.POST,url, requestData,
                {
                    println("game updated")
                    val game = (Gson().fromJson(it.toString(0), Game::class.java))
                    callback(game,null)

                }, {
            callback(null, it.networkResponse.statusCode)
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
    }
