package com.efe.games.ui.sudoku.escritura

import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.view.View
import android.widget.Button
import com.efe.games.R
import com.efe.games.model.sudoku.Celda
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
    private var panel: TecladoView? = null
    private var nombreMetodoEscritura: String? = null
    private var imView: View? = null
    protected var tableroView: TableroSudokuView? = null
    protected var context: Context? = null
    protected var activo = false

    val isMetodoEscrituraViewCreated: Boolean
        get() = imView != null

    val metodoEscrituraView: View
        get() {
            if(imView == null) {
                imView = createTecladoView()
                val cambiarModoView: View = imView!!.findViewById(R.id.cambiarModo)
                val cambiarModoBoton: Button = cambiarModoView as Button
                cambiarModoBoton.text = this.abbrName
                cambiarModoBoton.background.colorFilter =
                    LightingColorFilter(Color.parseColor("#FF4081"), 0)
            }
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
        tablero: TableroSudokuView?
    ) {
        this.context = context
        panel = tecladoView
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

    companion object {
        const val modeEditValue = 0
        const val modeEditNote: Int = 1
    }


    /**
     *  ====================================================
     *                     OPEN
     *  ====================================================
     */

    abstract val nameResID: Int
    abstract val abbrName: String?

    protected abstract fun createTecladoView(): View?
    protected open fun onActivated() {}
    protected open fun onDeactivated() {}
    protected open fun onPause() {}
    open fun onCellSelected(cell: Celda?) {}
    open fun onCellTapped(cell: Celda?) {}
}
