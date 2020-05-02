package com.efe.games.model.tictactoe

class TableroTicTacToe (
    var celdas: IntArray= intArrayOf(0,0,0,0,0,0,0,0,0),
    var playerTurn:ECodesTicTacToe = ECodesTicTacToe.P1_CODE
){

    fun makeMove(position: Int, player: ECodesTicTacToe) {
        this.celdas[position] = player.ordinal;
        changePlayerTurn()
    }

    fun changePlayerTurn(){
        if(this.playerTurn == ECodesTicTacToe.P1_CODE) this.playerTurn = ECodesTicTacToe.P2_CODE
        else this.playerTurn = ECodesTicTacToe.P1_CODE
    }

    fun getGameStatus(): ECodesTicTacToe {
        var win: ECodesTicTacToe = ECodesTicTacToe.PLAYING_CODE
        //Check rows
        for (i in 0 until 9 step 3){
            var a = this.celdas[i]
            if(a != ECodesTicTacToe.PLAYING_CODE.ordinal) {
                if (a == this.celdas[i + 1] && a == this.celdas[i + 2]) return convertGameStatus(a); // There is a win
            }
        }
        //Check columns
        for (i in 0 until 3 step 1){
            var a = this.celdas[i]
            println(this.celdas.contentToString() +" "+ i)
            if(a != ECodesTicTacToe.PLAYING_CODE.ordinal) {
                if (a == this.celdas[i + 3] && a == this.celdas[i + 6]) return convertGameStatus(a); // There is a win
            }
        }
        //Check diagonals
        //Diag1
        var cell = this.celdas[0]
        if(cell == this.celdas[4] && cell == this.celdas[8]) return convertGameStatus(cell) // There is a win
        //Diag2
        cell = this.celdas[2]
        if(cell == this.celdas[4] && cell == this.celdas[6]) return convertGameStatus(cell) // There is a win
        if(this.celdas.contains(ECodesTicTacToe.PLAYING_CODE.ordinal)) return ECodesTicTacToe.PLAYING_CODE // Not finished game
        else return ECodesTicTacToe.DRAW_CODE // Draw
    }

    private fun convertGameStatus(num:Int): ECodesTicTacToe{
        if(num == ECodesTicTacToe.PLAYING_CODE.ordinal) return ECodesTicTacToe.PLAYING_CODE
        else if(num == ECodesTicTacToe.P1_CODE.ordinal) return ECodesTicTacToe.P1_CODE
        else if(num == ECodesTicTacToe.P2_CODE.ordinal) return ECodesTicTacToe.P2_CODE
        else return ECodesTicTacToe.DRAW_CODE
    }

    fun getTableroState():String{
        var tableroStr = ""
        for(i in 0 until 9){
            var cell = this.celdas[i]
            if(cell == 0) tableroStr += "-"
            else if(cell == ECodesTicTacToe.P1_CODE.ordinal) tableroStr += "X"
            else tableroStr += "O"
        }
        return tableroStr
    }


}