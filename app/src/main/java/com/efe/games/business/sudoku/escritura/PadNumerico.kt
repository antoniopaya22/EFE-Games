package com.efe.games.business.sudoku.escritura

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.efe.games.R
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.SudokuGame
import com.efe.games.model.sudoku.Tablero
import com.efe.games.model.sudoku.listeners.OnChangeListener
import com.efe.games.ui.sudoku.TableroSudokuView
import com.efe.games.ui.sudoku.TecladoView


class PadNumerico: MetodoEscritura() {

    override val nameResID: Int
        get() = R.string.numpad
    override val abbrName: String?
        get() = context!!.getString(R.string.numpad_abbr)

    private val MODE_EDIT_VALUE = 0
    private val MODE_EDIT_NOTE = 1

    //Atributos
    private var moveCeldaSeleccionadaOnPress = true
    private var colorearValoresCompletados = true
    private var mostrarTotalNumeros = false
    private var celdaSeleccionada: Celda? = null
    private var cambiarDeNumerosANotasButton: ImageButton? = null
    private var botonesNumeros: MutableMap<Int, Button>? = null
    private var modoEditar = MODE_EDIT_VALUE

    //Metodos
    override fun inicializar(
        context: Context?,
        tecladoView: TecladoView?,
        game: SudokuGame?,
        tablero: TableroSudokuView?
    ) {
        super.inicializar(context, tecladoView, game, tablero)
        game!!.tablero.addOnChangeListener(onCeldasChangeListener)
    }

    override fun createTecladoView(): View? {
        val inflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val teclado: View = inflater.inflate(R.layout.pad_numerico, null)
        botonesNumeros = HashMap()
        botonesNumeros!![1] = teclado.findViewById(R.id.button_1) as Button
        botonesNumeros!![2] = teclado.findViewById(R.id.button_2) as Button
        botonesNumeros!![3] = teclado.findViewById(R.id.button_3) as Button
        botonesNumeros!![4] = teclado.findViewById(R.id.button_4) as Button
        botonesNumeros!![5] = teclado.findViewById(R.id.button_5) as Button
        botonesNumeros!![6] = teclado.findViewById(R.id.button_6) as Button
        botonesNumeros!![7] = teclado.findViewById(R.id.button_7) as Button
        botonesNumeros!![8] = teclado.findViewById(R.id.button_8) as Button
        botonesNumeros!![9] = teclado.findViewById(R.id.button_9) as Button
        botonesNumeros!![0] = teclado.findViewById(R.id.button_clear) as Button
        for (num in botonesNumeros!!.keys) {
            val b: Button? = botonesNumeros!![num]
            b!!.tag = num
            b.setOnClickListener(botonNumeroClick)
        }
        cambiarDeNumerosANotasButton =
            teclado.findViewById(R.id.cambiar_num_nota)
        cambiarDeNumerosANotasButton!!.setOnClickListener {
            modoEditar = if (modoEditar == MODE_EDIT_VALUE) MODE_EDIT_NOTE else MODE_EDIT_VALUE
            update()
        }
        return teclado
    }

    override fun onActivated() {
        update()
        celdaSeleccionada = tableroView!!.celdaSeleccionada
    }

    override fun onCellSelected(cell: Celda?) {
        celdaSeleccionada = cell
    }

    private val botonNumeroClick: View.OnClickListener = View.OnClickListener { v ->
        val numeroSeleccionado = v.tag as Int
        val celSel = celdaSeleccionada
        if (celSel != null) {
            when (modoEditar) {
                MODE_EDIT_NOTE -> if (numeroSeleccionado == 0) {
                    game!!.setNotaCelda(celSel, NotaCelda())
                } else if (numeroSeleccionado in 1..9) {
                    game!!.setNotaCelda(celSel, celSel.nota.anotarNumero(numeroSeleccionado))
                }
                MODE_EDIT_VALUE -> if (numeroSeleccionado in 0..9) {
                    game!!.setValorCelda(celSel, numeroSeleccionado)
                    if (moveCeldaSeleccionadaOnPress) {
                        tableroView!!.moverCeldaSeleccionadaADerecha()
                    }
                }
            }
        }
    }

    private val onCeldasChangeListener: OnChangeListener = object : OnChangeListener {
        override fun onChange() {
            if (activo) {
                update()
            }
        }
    }


    private fun update() {
        when (modoEditar) {
            MODE_EDIT_NOTE -> cambiarDeNumerosANotasButton!!.setImageResource(R.drawable.ic_edit_white)
            MODE_EDIT_VALUE -> cambiarDeNumerosANotasButton!!.setImageResource(R.drawable.ic_edit_grey)
        }
        var valoresUsados: Map<Int, Int>? = null
        if (colorearValoresCompletados || mostrarTotalNumeros) valoresUsados =
            game!!.tablero.getValoresUsados()
        if (colorearValoresCompletados) {
            for ((key, value) in valoresUsados!!) {
                val colorearValor = value >= Tablero.SUDOKU_SIZE
                val b: Button? = botonesNumeros!![key]
                if (colorearValor) {
                    b!!.getBackground().setColorFilter(-0xe4a1e0, PorterDuff.Mode.MULTIPLY)
                } else {
                    b!!.getBackground().colorFilter = null
                }
            }
        }
        if (mostrarTotalNumeros) {
            for ((key, value) in valoresUsados!!) {
                val b: Button? = botonesNumeros!![key]
                b!!.text = "$key ($value)"
            }
        }
    }
}