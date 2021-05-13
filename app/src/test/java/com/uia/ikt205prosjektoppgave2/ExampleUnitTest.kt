package com.uia.ikt205prosjektoppgave2


import data.Game
import org.junit.Test
import org.junit.Assert.*
import services.GameManager
import javax.inject.Inject


class ExampleUnitTest {

    // these test wont work because i cant seem to get an instance of gamemanager

    @Inject lateinit var gameManager:GameManager

    var gameState: Game? = null
    val player1:String = "Gunnar"
    val player2:String = "Nils"
    val startingGameState = listOf(listOf("0","0","0"), listOf("0","0","0"), listOf("0","0","0"))


    @Test
    fun testCreateGame(){
        gameManager.createGame(player1,startingGameState ){ game:Game?, err:Int? ->
            gameState = game
            assertNotNull(game)
            assertNotNull(game?.gameId)
            assertEquals(player1, game?.players?.get(0))
            assertEquals(game?.state, startingGameState)
        }
    }

    @Test
    fun testJoinGame(){
        gameManager.joinGame(player2, gameState!!.gameId) {state:Game?, err:Int? ->
            gameState = state
            assertEquals(player1, state?.players?.get(0))
            assertEquals(player2, state?.players?.get(1))
        }
    }


    @Test
    fun testGameOver(){
        val testHorizontal1 = listOf(listOf("X", "X", "O"), listOf("0", "0", "0"), listOf("O", "O", "O"))
        val testHorizontal2 = listOf(listOf("0", "X", "O"), listOf("X", "X", "X"), listOf("0", "O", "0"))
        val testVertical1 = listOf(listOf("X", "X", "O"), listOf("X", "0", "0"), listOf("X", "O", "O"))
        val testVertical2 = listOf(listOf("X", "X", "O"), listOf("X", "0", "O"), listOf("0", "O", "O"))
        val testDiagonal1 = listOf(listOf("X", "X", "O"), listOf("0", "O", "0"), listOf("O", "0", "X"))
        val testDiagonal2 = listOf(listOf("X", "O", "0"), listOf("O", "X", "0"), listOf("O", "0", "X"))

        assertEquals(gameManager.checkGameOver(testHorizontal1), true)
        assertEquals(gameManager.checkGameOver(testVertical1), true)
        assertEquals(gameManager.checkGameOver(testDiagonal1), true)
        assertEquals(gameManager.checkGameOver(testHorizontal2), true)
        assertEquals(gameManager.checkGameOver(testVertical2), true)
        assertEquals(gameManager.checkGameOver(testDiagonal2), true)
    }
}