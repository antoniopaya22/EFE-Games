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
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.check(R.id.rdAILimited)

        val btn: Button = findViewById(R.id.btPlayTicTacToe)
        btn.setOnClickListener {
            play()
        }

    }

    fun play() {
        val intent = Intent(this, TicTacToeActivity::class.java)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val id = radioGroup.checkedRadioButtonId
        val checked = findViewById<RadioButton>(id).text
        var playmode = 0
        when(checked){
            getString(R.string.modeAILimited) -> playmode = 0
            getString(R.string.modeAIInfinite) -> playmode = 1
            getString(R.string.mode1v1Limited) -> playmode = 2
            getString(R.string.mode1v1Infinite) -> playmode = 3
        }
        intent.putExtra("playMode", playmode)
        startActivity(intent)
    }

}
