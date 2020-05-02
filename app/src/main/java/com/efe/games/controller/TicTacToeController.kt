package com.efe.games.controller

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.business.tictactoe.TicTacToe

object TicTacToeController {
    val tictactoe: TicTacToe =
        TicTacToe()

    fun makeMoveUser(move:Int): Int {
        var moveAI = -1
        if(this.tictactoe.makeMoveUser(move, ECodesTicTacToe.P1_CODE)) {
            moveAI = tictactoe.makeMoveAPI(ECodesTicTacToe.P2_CODE)
            println("FROM CONTROLLER ==============================")
            println(moveAI)
        }
        return moveAI
    }

    fun getBoard(): IntArray {
        return this.tictactoe.board.celdas
    }
}