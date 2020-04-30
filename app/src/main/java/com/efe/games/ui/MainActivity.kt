package com.efe.games.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.UserController
import com.efe.games.ui.sudoku.SudokuActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView: TextView = findViewById(R.id.textViewMain)
        textView.text = UserController.getById(1).username

        // Antonio
        val btn: Button = findViewById(R.id.buttonEnlace)
        btn.setOnClickListener {
            val intent = Intent(this, SudokuActivity::class.java)
            startActivity(intent)
        }

        // Alba
//        val btn2: Button = findViewById(R.id.buttonEnlaceAlba)
//        btn2.setOnClickListener {
//            val intent = Intent(this, TikTakToeActivity::class.java)
//            startActivity(intent)
//        }
    }
}
