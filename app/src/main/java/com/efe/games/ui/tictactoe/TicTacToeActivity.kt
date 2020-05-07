package com.efe.games.ui.tictactoe

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.MusicController
import com.efe.games.controller.sudoku.SudokuController
import com.efe.games.controller.tictactoe.TicTacToeController
import com.efe.games.model.tictactoe.ECodesTicTacToe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TicTacToeActivity : AppCompatActivity() {
    private var preferences: SharedPreferences? = null
    private var musica: Boolean = true

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private lateinit var txtStatus:TextView
    private var thinking: Boolean = false
    private var takenPiece: Int = -1
    private var playMode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.playMode = intent.getIntExtra("playMode", 0)
        setContentView(R.layout.activity_tictactoe)
        txtStatus = findViewById(R.id.txtStatus)

        // Musica
        preferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)
        if(preferences!!.getBoolean("Musica", true)) MusicController.startMusic(this)
        musica = MusicController.isPlaying

        initStatus()

        for (i in 0..8) {
            val id = resources.getIdentifier("board$i", "id", packageName)
            val btn = findViewById<ImageButton>(id)
            btn.setOnClickListener { onMove(btn, i) }
        }

        val btRestart: Button = findViewById(R.id.btRestart)
        btRestart.setOnClickListener { onRestartGame() }
    }

    private fun onMove(cell:ImageButton, move:Int) {
        if(!this.thinking) {
            val status = TicTacToeController.getGameStatus()
            if (status == ECodesTicTacToe.PLAYING_CODE) {
                when(this.playMode) {
                    0 -> modeAILimited(move, cell)
                    1 -> modeAIInfinite(move, cell)
                    2 -> mode1v1Limited(move, cell)
                    3 -> mode1v1Infinite(move, cell)
                }
            }
        }
    }

    private fun modeAILimited(move:Int, cell:ImageButton):Int{
        val nextMove = TicTacToeController.makeMoveUser(move)
        if (nextMove >= 0) {
            txtStatus.text = getString(R.string.pensando)
            cell.setImageResource(R.drawable.ic_cross)
            moveFromAPI()
        }
        return move
    }

    private fun modeAIInfinite(currentMove:Int, cell:ImageButton){
        if(!TicTacToeController.filledBoard()){
            modeAILimited(currentMove, cell)
        }else{
            if(this.takenPiece == -1){
                if(TicTacToeController.validInfiniteMove(currentMove)){
                    this.takenPiece = currentMove
                    // Remark pressed piece
                    val id:Int = resources.getIdentifier("board${this.takenPiece}", "id", packageName)
                    val btn = findViewById<ImageButton>(id)
                    btn.setImageResource(R.drawable.ic_cross_marked)
                }
            }else{
                TicTacToeController.unmakeMove(this.takenPiece)

                var id: Int = resources.getIdentifier("board${this.takenPiece}", "id", packageName)
                var btn = findViewById<ImageButton>(id)
                val move:Int = TicTacToeController.makeMoveUser(currentMove)

                if (move >= 0 && TicTacToeController.getGameStatus() == ECodesTicTacToe.PLAYING_CODE) {

                    btn.setImageResource(R.drawable.ic_clean)
                    cell.setImageResource(R.drawable.ic_cross)
                    val unmadeMove:Int = TicTacToeController.unmakeMoveAI()
                    id = resources.getIdentifier("board${unmadeMove}", "id", packageName)
                    btn = findViewById<ImageButton>(id)
                    btn.setImageResource(R.drawable.ic_clean)

                    cell.setImageResource(R.drawable.ic_cross)
                    moveFromAPI()
                    this.takenPiece = -1
                }else if(move >= 0){
                    btn.setImageResource(R.drawable.ic_clean)
                    cell.setImageResource(R.drawable.ic_cross)
                    this@TicTacToeActivity.setStatus()
                }else{
                    this@TicTacToeActivity.setStatus()
                }

            }
        }
    }

    private fun moveFromAPI(){
        uiScope.launch {
            this@TicTacToeActivity.thinking = true
            val nextMove:Int = TicTacToeController.makeMoveAPI()
            if (nextMove >= 0) {
                val id = resources.getIdentifier("board$nextMove", "id", packageName)
                val btn: ImageButton = findViewById<ImageButton>(id)
                btn.setImageResource(R.drawable.ic_circle)
            }
            this@TicTacToeActivity.thinking = false
            this@TicTacToeActivity.setStatus()
        }
    }

    private fun mode1v1Limited(currentMove:Int, cell:ImageButton):Int{
        val move:Int = TicTacToeController.makeMoveUser(currentMove)
        if (move >= 0) {
            if (TicTacToeController.getTurn() == ECodesTicTacToe.P2_CODE) {
                cell.setImageResource(R.drawable.ic_cross)
            } else {
                cell.setImageResource(R.drawable.ic_circle)
            }
            setStatus()
        }
        return move
    }

    private fun mode1v1Infinite(currentMove:Int, cell:ImageButton){
        if(!TicTacToeController.filledBoard()){
            mode1v1Limited(currentMove, cell)
        }else{
            if(this.takenPiece == -1){
                if(TicTacToeController.validInfiniteMove(currentMove)){
                    this.takenPiece = currentMove
                    // Remark pressed piece
                    val id:Int = resources.getIdentifier("board${this.takenPiece}", "id", packageName)
                    val btn = findViewById<ImageButton>(id)
                    if(TicTacToeController.getTurn() == ECodesTicTacToe.P1_CODE) {
                        btn.setImageResource(R.drawable.ic_cross_marked)
                    }else{
                        btn.setImageResource(R.drawable.ic_circle_marked)
                    }
                }
            }else{
                TicTacToeController.unmakeMove(this.takenPiece)

                val id: Int = resources.getIdentifier("board${this.takenPiece}", "id", packageName)
                val btn = findViewById<ImageButton>(id)
                val turn = TicTacToeController.getTurn()
                val move:Int = TicTacToeController.makeMoveUser(currentMove)
                if (move >= 0 && TicTacToeController.getGameStatus() == ECodesTicTacToe.PLAYING_CODE) {
                    btn.setImageResource(R.drawable.ic_clean)
                    if (turn == ECodesTicTacToe.P1_CODE) {
                        cell.setImageResource(R.drawable.ic_cross)
                    } else {
                        cell.setImageResource(R.drawable.ic_circle)
                    }
                    setStatus()
                    this.takenPiece = -1
                }else if(move >= 0){

                    btn.setImageResource(R.drawable.ic_clean)
                    if (turn == ECodesTicTacToe.P1_CODE) {
                        cell.setImageResource(R.drawable.ic_cross)
                    } else {
                        cell.setImageResource(R.drawable.ic_circle)
                    }
                    setStatus()
                }else{
                    setStatus()
                }
            }
        }
    }



    private fun setStatus() {
        val status = TicTacToeController.getGameStatus()
        when(this.playMode) {
            0, 1 -> {
                if (status == ECodesTicTacToe.PLAYING_CODE) {
                    txtStatus.text = getString(R.string.tu_turno)
                } else if (status == ECodesTicTacToe.P1_CODE) {
                    txtStatus.text = getString(R.string.has_ganado)
                } else if (status == ECodesTicTacToe.P2_CODE) {
                    txtStatus.text = getString(R.string.has_perdido)
                } else {
                    txtStatus.text = getString(R.string.empate)
                }
            }
            2, 3 -> {
                if (status == ECodesTicTacToe.PLAYING_CODE) {
                    if(TicTacToeController.getTurn() == ECodesTicTacToe.P1_CODE){
                        txtStatus.text = getString(R.string.turno_j1)
                    }else{
                        txtStatus.text = getString(R.string.turno_j2)
                    }
                } else if (status == ECodesTicTacToe.P1_CODE) {
                    txtStatus.text = getString(R.string.gana_j1)
                } else if (status == ECodesTicTacToe.P2_CODE) {
                    txtStatus.text = getString(R.string.gana_j2)
                } else {
                    txtStatus.text = getString(R.string.empate)
                }
            }
        }
    }

    private fun onRestartGame(){
        if(!this.thinking) {
            TicTacToeController.restartGame()
            this.takenPiece = -1
            for (i in 0..8) {
                val id = resources.getIdentifier("board$i", "id", packageName)
                val btn = findViewById<ImageButton>(id)
                btn.setImageResource(R.drawable.ic_clean)
            }
            initStatus()
        }
    }

    override fun onPause() {
        super.onPause()
        MusicController.stopMusic(this)
    }

    override fun onResume() {
        super.onResume()
        if(musica) MusicController.startMusic(this)
    }

    override fun onBackPressed() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Partida en curso")
            .setTitle("¿Estás seguro de que quieres salir")
        builder.apply {
            setPositiveButton("Si") { _, _ ->
                salir()
            }
            setNegativeButton("No", null)
        }
        builder.create()
        builder.show()
    }

    private fun salir() {
        if(!musica) MusicController.stopMusic(this)
        finish()
    }

    private fun initStatus(){
        when(this.playMode) {
            0, 1 -> {
                txtStatus.text = getString(R.string.tu_turno)
            }
            2, 3 -> {
                txtStatus.text = getString(R.string.turno_j1)
            }
        }
    }

}
