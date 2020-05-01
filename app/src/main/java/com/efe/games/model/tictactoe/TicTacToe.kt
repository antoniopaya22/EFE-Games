package com.efe.games.model.tictactoe

import kotlinx.coroutines.*

class TicTacToe(
    var board: TableroTicTacToe = TableroTicTacToe(),
    var moveAPI: MoveAPI = MoveAPI()
){
    private val uiScope = CoroutineScope(Dispatchers.Main)
    fun makeMoveUser(move:Int, player: ECodesTicTacToe){
        this.board.makeMove(move, player)
    }

    fun makeMoveAPI(player:ECodesTicTacToe): Int {
        var result:Int = -1
        uiScope.launch {
            /*
            withContext(Dispatchers.Default) {
                async {
                    var result = -1

                    withContext(Dispatchers.Main) {
                        result = moveAPI.getNextMove(board.getTableroState(), player)
                    }

                    result

                }.await()

            }
                */
            result = doInBack(player)
        }


    //result = moveAPI.getNextMove(board.getTableroState(), player)
    println("FROM TICTACTOE ==================================================================")
    println(result)
        return result
        //this.board.makeMove(move, player)
    }



    private suspend fun doInBack(player:ECodesTicTacToe) = withContext(Dispatchers.Default) {
        var result = -1
        async {

            //withContext(Dispatchers.Main) {
                result = moveAPI.getNextMove(board.getTableroState(), player)
            //}

        result
        }
    }.await()



    fun getBoardState(): IntArray {
        return this.board.celdas
    }
}