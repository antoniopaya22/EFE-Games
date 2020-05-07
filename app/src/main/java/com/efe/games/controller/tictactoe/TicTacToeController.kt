package com.efe.games.controller.tictactoe

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.business.tictactoe.TicTacToe

object TicTacToeController {
    var play1v1: Boolean = false
    var playMode:Int = 3
    val tictactoe: TicTacToe = TicTacToe()
    var full:Boolean = false


    fun makeMoveUser(move:Int): Int {
        return this.tictactoe.makeMoveUser(move)
    }

    suspend fun makeMoveAPI(): Int {
        return tictactoe.makeMoveAPI(ECodesTicTacToe.P2_CODE)
    }

    fun getGameStatus(): ECodesTicTacToe {
        return this.tictactoe.board.getGameStatus()
    }

    fun restartGame() {
        this.full = false
       this.tictactoe.restartGame()
    }

    fun changeMode() {
        this.play1v1 = !this.play1v1
    }

    fun getTurn(): ECodesTicTacToe {
        return this.tictactoe.getTurn()
    }

    fun getNumberOfPieces(): Int {
        return this.tictactoe.getNumberOfPieces()
    }

    fun unmakeMove(move: Int) {
        this.tictactoe.unmakeMove(move)
    }

    fun unmakeMoveAI(): Int {
        return this.tictactoe.unmakeMoveAI()
    }

    fun validInfiniteMove(currentMove: Int): Boolean {
        return this.tictactoe.validInfiniteMove(currentMove)
    }

    fun filledBoard(): Boolean {
        if(!this.full) {
            if(this.tictactoe.getNumberOfPieces() == 6){
                this.full = true
            }
        }
        return this.full
    }


}