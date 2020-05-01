package com.efe.games.controller.tictactoe

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.model.tictactoe.TicTacToe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TicTacToeController {
    val tictactoe: TicTacToe = TicTacToe()

    fun makeMoveUser(move:Int): Int {
        tictactoe.makeMoveUser(move, ECodesTicTacToe.P1_CODE)
        var moveAI = -1

            moveAI = tictactoe.makeMoveAPI(ECodesTicTacToe.P2_CODE)
            println("FROM CONTROLLER ==============================")
            println(moveAI)

        return moveAI
    }

    fun getBoard(): IntArray {
        return tictactoe.board.celdas
    }
}