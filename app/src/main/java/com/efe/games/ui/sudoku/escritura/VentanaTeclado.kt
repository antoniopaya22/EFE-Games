package com.efe.games.ui.sudoku.escritura

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface.OnDismissListener
import android.view.LayoutInflater
import android.view.View
import com.efe.games.R
import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.business.sudoku.listeners.OnNotaEditadaListener
import com.efe.games.business.sudoku.listeners.OnNumeroEditadoListener
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.Tablero

/**
 * Dialog donde se muestra un teclado con los numeros a escribir
 */
class VentanaTeclado : MetodoEscritura(){

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    override val nameResID: Int
        get() = R.string.dialog
    override val abbrName: String?
        get() = context!!.getString(R.string.dialog_abbr)

    private var ventanaTecladoDialog: VentanaTecladoDialog? = null
    private var celdaSeleccionada: Celda? = null

    /**
     *  ====================================================
     *                PROPIEDADES - LISTENERS
     *  ====================================================
     */
    private val onNumeroEditadoListener: OnNumeroEditadoListener =
        object : OnNumeroEditadoListener {
            override fun onNumeroEditado(numero: Int): Boolean {
                if (numero != -1 && celdaSeleccionada != null) {
                    SudokuManager.setValorCelda(celdaSeleccionada!!, numero)
                }
                return true
            }
        }

    private val onNotaEditadaListener: OnNotaEditadaListener = object : OnNotaEditadaListener {
        override fun onNoteEdit(numeros: Array<Int>): Boolean {
            if (celdaSeleccionada != null) {
                numeros.forEach { x -> SudokuManager
                    .setNotaCelda(celdaSeleccionada!!, celdaSeleccionada!!.nota.anotarNumero(x)) }
            }
            return true
        }
    }

    private val onPopupDismissedListener = OnDismissListener { tableroView!!.ocultarCeldaTocada() }


    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    @SuppressLint("InflateParams")
    override fun createTecladoView(): View? {
        val inflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(R.layout.ventana_teclado_dialog, null)
    }

    private fun setTecladoDialog() {
        if(ventanaTecladoDialog == null){
            ventanaTecladoDialog = VentanaTecladoDialog(context!!)
            ventanaTecladoDialog!!.show()
            ventanaTecladoDialog!!.onNumeroEditadoListener = onNumeroEditadoListener
            ventanaTecladoDialog!!.onNotaEditadaListener = onNotaEditadaListener
            ventanaTecladoDialog!!.setOnDismissListener(onPopupDismissedListener)
        }
    }

    /**
     *  ====================================================
     *                      LISTENERS
     *  ====================================================
     */

    override fun onCellTapped(cell: Celda?) {
        celdaSeleccionada = cell
        if(cell!!.editable){
            setTecladoDialog()
            ventanaTecladoDialog!!.resetButtons()
            ventanaTecladoDialog!!.updateNumber(cell.value)
            ventanaTecladoDialog!!.updateNota(cell.nota.numeros)

            val contadorValoresUsados =
                SudokuManager.game.tablero.getValoresUsados()
            for ((key, value) in contadorValoresUsados!!) {
                if (value >= Tablero.SUDOKU_SIZE) {
                    ventanaTecladoDialog!!.colorearNumero(key)
                }
            }
            ventanaTecladoDialog!!.show()

        } else {
            tableroView!!.ocultarCeldaTocada()
        }
    }

}