package com.efe.games.business.tictactoe

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.model.tictactoe.TableroTicTacToe
import kotlinx.coroutines.*

class TicTacToe(
    var board: TableroTicTacToe = TableroTicTacToe(),
    var moveAPI: MoveAPI = MoveAPI(),
    var recommendation: Int = -1
){

    fun makeMoveUser(move:Int, player: ECodesTicTacToe):Int{
        if(this.board.celdas[move] == 0){
            this.board.makeMove(move, player)
            return move
        }
        return -1
    }

    suspend fun makeMoveAPI(player: ECodesTicTacToe): Int {
        val result = doInBack(player)
        if(this.board.celdas[result] == 0){
            this.board.makeMove(result, player)
            return result
        }
        return -1
    }

    private suspend fun doInBack(player: ECodesTicTacToe) = withContext(Dispatchers.Default) {
        async {
            val result = moveAPI.getNextMove(board.getTableroState(), player)
            result
        }
    }.await()

    fun restartGame() {
        this.board = TableroTicTacToe()
    }

}