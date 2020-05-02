package com.efe.games.business.tictactoe

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.model.tictactoe.TableroTicTacToe
import kotlinx.coroutines.*

class TicTacToe(
    var board: TableroTicTacToe = TableroTicTacToe(),
    var moveAPI: MoveAPI = MoveAPI()
){
    private val uiScope = CoroutineScope(Dispatchers.Main)

    fun makeMoveUser(move:Int, player: ECodesTicTacToe):Boolean{
        if(this.board.celdas[move] == 0){
            this.board.makeMove(move, player)
            return true
        }
        return false
    }

    fun makeMoveAPI(player: ECodesTicTacToe): Int {
        var result:Int = -1
        uiScope.launch {
            result = doInBack(player)
        }
        println("FROM TICTACTOE ==================================================================")
        println(result)
        return result
        //this.board.makeMove(move, player)
    }

    private suspend fun doInBack(player: ECodesTicTacToe) = withContext(Dispatchers.Default) {
        var result = -1
        async {

            withContext(Dispatchers.Main) {
                result = moveAPI.getNextMove(board.getTableroState(), player)
            }

        result
        }
    }.await()



    fun getBoardState(): IntArray {
        return this.board.celdas
    }
}