package com.efe.games.business.tictactoe

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.model.tictactoe.TableroTicTacToe
import kotlinx.coroutines.*

class TicTacToe(
    var board: TableroTicTacToe = TableroTicTacToe(),
    var moveAPI: MoveAPI = MoveAPI()
){

    fun makeMoveUser(move:Int):Int{
        if(this.board.celdas[move] == 0){
            makeMove(move)
            return move
        }
        return -1
    }

    suspend fun makeMoveAPI(player: ECodesTicTacToe): Int {
        val result = doInBack(player)
        if(this.board.celdas[result] == 0){
            makeMove(result)
            return result
        }
        return -1
    }
    private fun makeMove(position: Int) {
        this.board.celdas[position] = this.board.playerTurn.ordinal
        this.board.changePlayerTurn()
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

    fun getTurn(): ECodesTicTacToe {
        return this.board.playerTurn
    }

    fun getNumberOfPieces(): Int {
        return this.board.getNumberOfPieces()
    }

    fun unmakeMove(move: Int) {
        this.board.celdas[move] = 0
    }

    fun unmakeMoveAI(): Int {
        val pieces = mutableListOf<Int>()
        for (i in 0..8) {
            if(this.board.celdas[i] == ECodesTicTacToe.P2_CODE.ordinal){
                pieces.add(i)
            }
        }
        val pick = (0 until pieces.size).random()
        val move = pieces[pick]
        unmakeMove(move)
        return move
    }

    fun validInfiniteMove(currentMove: Int): Boolean {
        return this.board.celdas[currentMove] == this.board.playerTurn.ordinal
    }

}