package com.efe.games.ui.tictactoe

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.tictactoe.TicTacToeController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TicTacToeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //this.setTheme(R.style.Theme_Default)
        setContentView(R.layout.activity_tictactoe)

        val board0: ImageButton = findViewById(R.id.board0)
        board0.setOnClickListener { onMove(board0, 0) }
        var buttons = mutableListOf(board0)

        for (i in 1..8) {
            val id = resources.getIdentifier("board$i", "id", packageName)
            var btn: ImageButton = findViewById(id) as ImageButton
            btn.setOnClickListener { onMove(btn, i) }
            buttons.add(btn)
        }
        /*
         val myCanvasView = MyCanvasView(this)
        myCanvasView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN
        setContentView(myCanvasView)
         */
    }

    fun onMove(cell:ImageButton, move:Int){
        //if valid move

        println(move)

        var moveAI:Int = -1


            moveAI = TicTacToeController.makeMoveUser(move)
            println("FROM ACITVITY ==============================")
            println(moveAI)

        if(moveAI >= 0){
            cell.setImageResource(R.drawable.ic_cross)
            val id = resources.getIdentifier("board$moveAI", "id", packageName)
            var btn: ImageButton = findViewById(id) as ImageButton
            btn.setImageResource(R.drawable.ic_circle)
        }

/*
        doAsync() {
            moveAI = TicTacToeController.makeMoveUser(move)
            uiThread {
                val id = resources.getIdentifier("board$moveAI", "id", packageName)
                var btn: ImageButton = findViewById(id) as ImageButton
                btn.setImageResource(R.drawable.ic_cross)
            }
        }

 */



    }
}
