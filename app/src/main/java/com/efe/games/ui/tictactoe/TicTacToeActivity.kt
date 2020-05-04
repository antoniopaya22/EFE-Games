package com.efe.games.ui.tictactoe

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.tictactoe.TicTacToeController
import com.efe.games.model.tictactoe.ECodesTicTacToe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TicTacToeActivity : AppCompatActivity() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var txtStatus:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe)
        txtStatus = findViewById(R.id.txtStatus)
        txtStatus.text = "Tu turno"

        for (i in 0..8) {
            val id = resources.getIdentifier("board$i", "id", packageName)
            var btn: ImageButton = findViewById(id) as ImageButton
            btn.setOnClickListener { onMove(btn, i) }
        }

        val btRestart: Button = findViewById(R.id.btRestart)
        btRestart.setOnClickListener { onRestartGame() }
    }

    fun onMove(cell:ImageButton, move:Int) {
        var status = TicTacToeController.getGameStatus()
        if(!TicTacToeController.play1v1){
            if (status == ECodesTicTacToe.PLAYING_CODE) {
                txtStatus.text = "Pensando..."
                var nextMove: Int = -1
                var move = TicTacToeController.makeMoveUser(move)
                if (move >= 0) {

                    cell.setImageResource(R.drawable.ic_cross)
                    uiScope.launch {
                        nextMove = TicTacToeController.makeMoveAPI()
                        if (nextMove >= 0) {
                            val id = resources.getIdentifier("board$nextMove", "id", packageName)
                            var btn: ImageButton = findViewById(id) as ImageButton
                            btn.setImageResource(R.drawable.ic_circle)
                        }
                        status = TicTacToeController.getGameStatus()
                        this@TicTacToeActivity.setStatus(status)
                    }
                }
            }
        }else{
            if (status == ECodesTicTacToe.PLAYING_CODE) {
                var move = TicTacToeController.makeMoveUser(move)
                if (move >= 0) {
                    if (TicTacToeController.getTurn() == ECodesTicTacToe.P2_CODE) {
                        cell.setImageResource(R.drawable.ic_cross)
                    } else {
                        cell.setImageResource(R.drawable.ic_circle)
                    }
                    status = TicTacToeController.getGameStatus()
                    setStatus(status)
                }
            }
        }
    }
    private fun setStatus(status: ECodesTicTacToe) {
        if(!TicTacToeController.play1v1) {
            if (status == ECodesTicTacToe.PLAYING_CODE) {
                txtStatus.text = "Tu turno"
            } else if (status == ECodesTicTacToe.P1_CODE) {
                txtStatus.text = "Has ganado"
            } else if (status == ECodesTicTacToe.P2_CODE) {
                txtStatus.text = "Has perdido"
            } else {
                txtStatus.text = "Empate"
            }
        }else{
            if (status == ECodesTicTacToe.PLAYING_CODE) {
                if(TicTacToeController.getTurn() == ECodesTicTacToe.P1_CODE){
                    txtStatus.text = "Turno del jugador 1"
                }else{
                    txtStatus.text = "Turno del jugador 2"
                }
            } else if (status == ECodesTicTacToe.P1_CODE) {
                txtStatus.text = "Gana el jugador 1"
            } else if (status == ECodesTicTacToe.P2_CODE) {
                txtStatus.text = "Gana el jugador 2"
            } else {
                txtStatus.text = "Empate"
            }
        }
    }

    fun onRestartGame(){
        TicTacToeController.restartGame()
        for (i in 0..8) {
            val id = resources.getIdentifier("board$i", "id", packageName)
            var btn: ImageButton = findViewById(id) as ImageButton
            btn.setImageResource(R.drawable.ic_clean)
        }
        if(!TicTacToeController.play1v1) {
            txtStatus.text = "Tu turno"
        }else{
            txtStatus.text = "Turno del jugador 1"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.tictactoe_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.play1v1 -> {
                TicTacToeController.changeMode()
                onRestartGame()
                true
            }
            R.id.hayMusicaSudoku -> {
                /*
                SudokuController.hayTiempo()
                item.isChecked = !item.isChecked

                 */
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
