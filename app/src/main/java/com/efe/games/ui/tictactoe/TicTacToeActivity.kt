package com.efe.games.ui.tictactoe

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.withContext


class TicTacToeActivity : AppCompatActivity() {
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var txtStatus:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.setTheme(R.style.Theme_Default)
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
        /*
         val myCanvasView = MyCanvasView(this)
        myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(myCanvasView)
         */
    }

    fun onMove(cell:ImageButton, move:Int) {
        var status = TicTacToeController.getGameStatus()
        if (status == ECodesTicTacToe.PLAYING_CODE) {
            txtStatus.text = "Pensando..."
            var moveAI: Int = -1
            var move = TicTacToeController.makeMoveUser(move)
            if (move >= 0) {
                cell.setImageResource(R.drawable.ic_cross)
                uiScope.launch {
                    moveAI = TicTacToeController.makeMoveAI()
                    if (moveAI >= 0) {
                        val id = resources.getIdentifier("board$moveAI", "id", packageName)
                        var btn: ImageButton = findViewById(id) as ImageButton
                        btn.setImageResource(R.drawable.ic_circle)
                    }
                    status = TicTacToeController.getGameStatus()
                    this@TicTacToeActivity.setStatus(status)
                }
            }


        }
    }
    private fun setStatus(status: ECodesTicTacToe) {
        if (status == ECodesTicTacToe.PLAYING_CODE) {
            txtStatus.text = "Tu turno"
        } else if (status == ECodesTicTacToe.P1_CODE) {
            txtStatus.text = "Has ganado"
        } else if (status == ECodesTicTacToe.P2_CODE) {
            txtStatus.text = "Has perdido"
        } else {
            txtStatus.text = "Empate"
        }
    }

    fun onRestartGame(){
        TicTacToeController.restartGame()
        for (i in 0..8) {
            val id = resources.getIdentifier("board$i", "id", packageName)
            var btn: ImageButton = findViewById(id) as ImageButton
            btn.setImageResource(R.drawable.ic_clean)
        }
        txtStatus.text = "Tu turno"
    }
}
