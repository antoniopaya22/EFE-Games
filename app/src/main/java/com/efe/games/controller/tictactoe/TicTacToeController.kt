package com.efe.games.controller.tictactoe

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.business.tictactoe.TicTacToe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TicTacToeController {
    val tictactoe: TicTacToe = TicTacToe()

    fun makeMoveUser(move:Int): Int {
        return this.tictactoe.makeMoveUser(move, ECodesTicTacToe.P1_CODE)
    }

    suspend fun makeMoveAI(): Int {
        return tictactoe.makeMoveAPI(ECodesTicTacToe.P2_CODE)
    }

    fun getGameStatus(): ECodesTicTacToe {
        return this.tictactoe.board.getGameStatus()
    }

    fun restartGame() {
       this.tictactoe.restartGame()
    }
}