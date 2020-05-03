package com.efe.games.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.UserController
import com.efe.games.ui.sudoku.SudokuActivity
import com.efe.games.ui.tictactoe.TicTacToeActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Antonio
        val btn: Button = findViewById(R.id.buttonEnlace)
        btn.setOnClickListener {
            val intent = Intent(this, SudokuActivity::class.java)
            startActivity(intent)
        }

        // Alba
        val btn2: Button = findViewById(R.id.buttonEnlaceAlba)
        btn2.setOnClickListener {
            val intent = Intent(this, TicTacToeActivity::class.java)
            startActivity(intent)
        }
    }
}
