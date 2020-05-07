package com.efe.games.ui.sudoku

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.efe.games.R
import com.efe.games.controller.MusicController

class SudokuOptionsActivity : AppCompatActivity() {

    private lateinit var spDificultad: Spinner
    private lateinit var swOpcionesAvanzadas: Switch
    private lateinit var swTiempo: Switch
    private lateinit var btnJugar: Button
    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sudoku_options)

        this.spDificultad = findViewById(R.id.spDificultad)
        this.swOpcionesAvanzadas = findViewById(R.id.switchOpcionesAvanzadas)
        this.swTiempo = findViewById(R.id.switchTiempo)
        this.btnJugar = findViewById(R.id.btnJugar)

        ArrayAdapter.createFromResource(this, R.array.spDificultad,
            android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spDificultad.adapter = adapter
        }

        swOpcionesAvanzadas.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) swTiempo.visibility = View.VISIBLE
            else swTiempo.visibility = View.INVISIBLE
        }

        btnJugar.setOnClickListener {
            if(!this.swTiempo.isChecked){
                val builder: AlertDialog.Builder = this.let {
                    AlertDialog.Builder(it)
                }
                builder
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Si juegas sin tiempo no obtendrás puntos al resolver el sudoku")
                    .setTitle("¿Quieres jugar sin tiempo?")
                builder.apply {
                    setPositiveButton("Ok") { _, _ ->
                        jugar()
                    }
                    setNegativeButton("Cancelar", null)
                }
                builder.create()
                builder.show()
            }else {
                jugar()
            }

        }

    }

    private fun jugar() {
        val intent = Intent(this, SudokuActivity::class.java)
        val dificultad = when (this.spDificultad.selectedItem) {
            "Muy Fácil"-> 0
            "Fácil"-> 1
            "Media"-> 2
            "Difícil"-> 3
            else -> 4
        }
        intent.putExtra("dificultad", dificultad)
        intent.putExtra("tiempo", this.swTiempo.isChecked)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        preferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)
        if(preferences!!.getBoolean("Musica", true)) MusicController.startMusic(this)
    }

    override fun onPause() {
        super.onPause()
        MusicController.stopMusic(this)
    }

}
