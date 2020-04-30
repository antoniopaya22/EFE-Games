package com.efe.games.business.sudoku.escritura

import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.view.View
import android.widget.Button
import com.efe.games.R
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.SudokuGame
import com.efe.games.ui.sudoku.TableroSudokuView
import com.efe.games.ui.sudoku.TecladoView

/**
 * Clase abstracta de la que heredan los diferentes metodos de escritura
 */
abstract class MetodoEscritura {
    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    protected var context: Context? = null
    protected var panel: TecladoView? = null
    protected var game: SudokuGame? = null
    protected var tableroView: TableroSudokuView? = null
    protected var nombreMetodoEscritura: String? = null
    protected var imView: View? = null
    protected var activo = false

    val isMetodoEscrituraViewCreated: Boolean
        get() = imView != null

    val metodoEscrituraView: View
        get() {
            imView = createTecladoView()
            val cambiarModoView: View = imView!!.findViewById(R.id.cambiarModo)
            val cambiarModoBoton: Button = cambiarModoView as Button
            cambiarModoBoton.text = this.abbrName
            cambiarModoBoton.background.colorFilter =
                LightingColorFilter(Color.parseColor("#00695c"), 0)
            onTecladoViewCreated(imView!!)
            return imView!!
        }

    var isEnabled = true
        set(enabled) {
            field = enabled
            if (!enabled) {
                panel!!.activateNextMetodoEscritura()
            }
        }

    /**
     *  ====================================================
     *             CONSTRUCTORES / INICIALIZADORES
     *  ====================================================
     */
    open fun inicializar(
        context: Context?,
        tecladoView: TecladoView?,
        game: SudokuGame?,
        tablero: TableroSudokuView?
    ) {
        this.context = context
        panel = tecladoView
        this.game = game
        tableroView = tablero
        nombreMetodoEscritura = this.javaClass.simpleName
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    fun pause() {
        onPause()
    }

    fun activate() {
        activo = true
        onActivated()
    }

    fun deactivate() {
        activo = false
        onDeactivated()
    }


    /**
     *  ====================================================
     *                     OPEN
     *  ====================================================
     */

    abstract val nameResID: Int
    abstract val abbrName: String?

    protected abstract fun createTecladoView(): View?
    protected open fun onTecladoViewCreated(tecladoView: View) {}
    protected open fun onActivated() {}
    protected open fun onDeactivated() {}
    protected open fun onPause() {}
    open fun onCellSelected(cell: Celda?) {}
    open fun onCellTapped(cell: Celda?) {}
}
