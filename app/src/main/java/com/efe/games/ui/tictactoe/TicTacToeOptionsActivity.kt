package com.efe.games.ui.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.efe.games.R



class TicTacToeOptionsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe_options)

        val btn: Button = findViewById(R.id.btPlayTicTacToe)
        btn.setOnClickListener {
            play()
        }

    }

    fun play() {
        val intent = Intent(this, TicTacToeActivity::class.java)
    /*
        intent.putExtra("dificultad", dificultad)
        intent.putExtra("tiempo", this.swTiempo.isChecked)
        intent.putExtra("musica", this.swMusica.isChecked)

     */
        startActivity(intent)
    }

}
