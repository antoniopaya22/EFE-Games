package com.efe.games.ui.sudoku.escritura

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.efe.games.R
import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.business.sudoku.listeners.OnChangeListener
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.Tablero
import com.efe.games.ui.sudoku.TableroSudokuView
import com.efe.games.ui.sudoku.TecladoView

/**
 * Vista que tiene todos los numeros debajo del tablero
 * (Pad)
 */
class PadNumerico: MetodoEscritura() {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    override val nameResID: Int
        get() = R.string.numpad
    override val abbrName: String?
        get() = context!!.getString(R.string.numpad_abbr)

    private var moveCeldaSeleccionadaOnPress = true
    private var celdaSeleccionada: Celda? = null
    private var cambiarDeNumerosANotasButton: ImageButton? = null
    private var botonesNumeros: MutableMap<Int, Button>? = null
    private var modoEditar = modeEditValue

    /**
     *  ====================================================
     *                      INIT
     *  ====================================================
     */
    override fun inicializar(
        context: Context?,
        tecladoView: TecladoView?,
        tablero: TableroSudokuView?
    ) {
        super.inicializar(context, tecladoView, tablero)
        SudokuManager.addTableroListener(onCeldasChangeListener)
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    @SuppressLint("InflateParams")
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
            modoEditar = if (modoEditar == modeEditValue) modeEditNote else modeEditValue
            update()
        }
        update()
        return teclado
    }

    private fun changeButtonIcon() {
        when (modoEditar) {
            modeEditNote -> cambiarDeNumerosANotasButton!!.setImageResource(R.drawable.ic_edit_value)
            modeEditValue -> cambiarDeNumerosANotasButton!!.setImageResource(R.drawable.ic_edit_note)
        }
    }

    private fun update() {
        changeButtonIcon()
        val valoresUsados: Map<Int, Int>? = SudokuManager.getValoresUsados()
        for ((key, value) in valoresUsados!!) {
            val colorearValor = value >= Tablero.SUDOKU_SIZE
            val b: Button? = botonesNumeros!![key]
            if (colorearValor){
                b!!.background.colorFilter =
                    LightingColorFilter(Color.parseColor("#00695c"), 0)
            }
            else
                b!!.background.colorFilter = null
        }
    }

    /**
     *  ====================================================
     *                      LISTENERS
     *  ====================================================
     */
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
                modeEditNote -> if (numeroSeleccionado == 0) {
                    SudokuManager.setNotaCelda(celSel, NotaCelda())
                } else if (numeroSeleccionado in 1..9) {
                    SudokuManager.setNotaCelda(celSel, celSel.nota.anotarNumero(numeroSeleccionado))
                }
                modeEditValue -> if (numeroSeleccionado in 0..9) {
                    SudokuManager.setValorCelda(celSel, numeroSeleccionado)
                    if (moveCeldaSeleccionadaOnPress) tableroView!!.moverCeldaSeleccionadaADerecha()
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

}