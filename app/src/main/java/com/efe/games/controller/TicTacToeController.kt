package com.efe.games.controller

import com.efe.games.model.User
import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.model.tictactoe.TableroTicTacToe
import com.efe.games.model.tictactoe.TicTacToe
import com.efe.games.repository.Repository
import com.efe.games.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TicTacToeController {
    val tictactoe: TicTacToe = TicTacToe()

    fun makeMoveUser(move:Int): Int {
        this.tictactoe.makeMoveUser(move, ECodesTicTacToe.P1_CODE)
        var moveAI = -1

            moveAI = tictactoe.makeMoveAPI(ECodesTicTacToe.P2_CODE)
            println("FROM CONTROLLER ==============================")
            println(moveAI)

        return moveAI
    }

    fun getBoard(): IntArray {
        return this.tictactoe.board.celdas
    }
}