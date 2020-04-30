package com.efe.games.ui.sudoku

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.efe.games.R
import com.efe.games.controller.sudoku.SudokuController
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.SudokuGame
import com.efe.games.model.sudoku.Tablero
import com.efe.games.model.sudoku.listeners.OnCeldaSeleccionadaListener
import com.efe.games.model.sudoku.listeners.OnChangeListener
import com.efe.games.model.sudoku.listeners.OnTocarCeldaListener
import kotlin.math.roundToInt

/**
 * VIEW - TableroSudoku
 *
 * Renderiza un tablero de un sudoku
 */
open class TableroSudokuView : View {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */

    private val lineaPaint = Paint() // Linea
    private val lineaSectorPaint = Paint() // Linea sector
    private val valorCeldaPaint = Paint() // Texto
    private val valorCeldaSoloLecturaPaint = Paint() // Texto solo lectura
    private val notaCeldaPaint = Paint() // Texto nota
    private val valorCeldaInvalidoPaint = Paint()
    // Background atts
    private val backgroundColorSecondary = Paint()
    private val backgroundColorLectura = Paint()
    private val backgroundColorPulsado = Paint()
    private val backgroundColorSeleccionado = Paint()

    private var anchoCelda = 0f
    private var altoCelda = 0f

    private var celdaPulsada: Celda? = null
    var celdaSeleccionada: Celda? = null

    private var numeroIzq = 0
    private var numeroArriba = 0
    private var notaArriba = 0f
    private var anchoLineaSector = 0

    var soloLectura = false
    var colorearErroneos = true
    var colorearPulsados = true
    var ocultarPulsados = true

    // Listeners
    var onTocarCeldaListener: OnTocarCeldaListener? = null
    var onCeldaSeleccionadaListener: OnCeldaSeleccionadaListener? = null

    var game: SudokuGame? = null
        set(value) {
            tablero = value!!.tablero
            field = value
        }

    private var tablero: Tablero? = null
        set(value) {
            if (!soloLectura) {
                celdaSeleccionada = value!!.celdas[0][0]
                onCeldaSeleccionada(celdaSeleccionada!!)
            }
            value!!.addOnChangeListener(object : OnChangeListener {
                override fun onChange() {
                    postInvalidate()
                }
            })
            postInvalidate()
            field = value
        }


    /**
     *  ====================================================
     *              CONSTRANTES / CONSTRUCTORES
     *  ====================================================
     */

    companion object{
        const val TABLERO_SIZE_DEFAULT = 100
        const val NO_COLOR = 0
    }

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        valorCeldaPaint.isAntiAlias = true
        valorCeldaSoloLecturaPaint.isAntiAlias = true
        valorCeldaInvalidoPaint.isAntiAlias = true
        notaCeldaPaint.isAntiAlias = true
        valorCeldaInvalidoPaint.color = Color.RED

        val a = context.obtainStyledAttributes(attrs, R.styleable.TableroSudokuView)
        lineaPaint.color = a.getColor(R.styleable.TableroSudokuView_lineColor, Color.BLACK)
        lineaSectorPaint.color = a.getColor(R.styleable.TableroSudokuView_sectorLineColor, Color.BLACK)
        valorCeldaPaint.color = a.getColor(R.styleable.TableroSudokuView_textColor, Color.BLACK)
        valorCeldaSoloLecturaPaint.color = a.getColor(R.styleable.TableroSudokuView_textColorReadOnly, Color.BLACK)
        notaCeldaPaint.color = a.getColor(R.styleable.TableroSudokuView_textColorNote, Color.BLACK)

        this.setBackgroundColor(a.getColor(R.styleable.TableroSudokuView_backgroundColor, Color.WHITE))
        backgroundColorSecondary.color = a.getColor(R.styleable.TableroSudokuView_backgroundColorSecondary, NO_COLOR)
        backgroundColorLectura.color = a.getColor(R.styleable.TableroSudokuView_backgroundColorReadOnly, NO_COLOR)
        backgroundColorSeleccionado.color = a.getColor(R.styleable.TableroSudokuView_backgroundColorSelected, Color.YELLOW)
        backgroundColorPulsado.color =
            a.getColor(R.styleable.TableroSudokuView_backgroundColorTouched, Color.rgb(50, 50, 255))

        a.recycle()
    }

    /**
     *  ====================================================
     *                  FUNCIONES
     *  ====================================================
     */

    private fun calcularAnchoLineaSector(pxAncho: Int, pxAlto: Int) {
        val pxSize = if (pxAncho < pxAlto) pxAncho else pxAlto
        val escala = context.resources.displayMetrics.density
        val size = pxSize / escala
        var anchoLineaSectorEscala = 2.0f
        if (size > 150) {
            anchoLineaSectorEscala = 3.0f
        }
        anchoLineaSector = (anchoLineaSectorEscala * escala).toInt()
    }

    fun moverCeldaSeleccionadaADerecha() {
        if (!moverCeldaSeleccionada(1, 0)) {
            var selRow = celdaSeleccionada!!.rowIndex
            selRow++
            if (!moverCeldaSeleccionadaA(selRow, 0)) {
                moverCeldaSeleccionadaA(0, 0)
            }
        }
        postInvalidate()
    }


    private fun moverCeldaSeleccionada(vx: Int, vy: Int): Boolean {
        var newRow = 0
        var newCol = 0
        if (celdaSeleccionada != null) {
            newRow = celdaSeleccionada!!.rowIndex + vy
            newCol = celdaSeleccionada!!.colIndex + vx
        }
        return moverCeldaSeleccionadaA(newRow, newCol)
    }

    private fun moverCeldaSeleccionadaA(row: Int, col: Int): Boolean {
        if (col >= 0 && col < Tablero.SUDOKU_SIZE && row >= 0 && row < Tablero.SUDOKU_SIZE) {
            celdaSeleccionada = tablero!!.celdas[row][col]
            onCeldaSeleccionada(celdaSeleccionada!!)
            postInvalidate()
            return true
        }
        return false
    }

    private fun getCeldaSituadaEn(x: Int, y: Int): Celda? {
        val lx = x - paddingLeft
        val ly = y - paddingTop
        val row = (ly / altoCelda).toInt()
        val col = (lx / anchoCelda).toInt()
        return if (col >= 0 && col < Tablero.SUDOKU_SIZE && row >= 0 && row < Tablero.SUDOKU_SIZE
        ) {
            tablero!!.celdas[row][col]
        } else {
            null
        }
    }

    /**
     *  ====================================================
     *                      EVENTOS
     *  ====================================================
     */
    protected open fun onCeldaSeleccionada(celda: Celda) {
        if (onCeldaSeleccionadaListener != null) celda.let {
            onCeldaSeleccionadaListener!!.onCellSelect(celda)
        }
    }

    protected open fun onTocarCelda(celda: Celda?) {
        onTocarCeldaListener?.onCellTapped(celda!!)
    }

    // OVERRIDES - ESTADO
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var ancho: Int
        var alto: Int
        if (widthMode == MeasureSpec.EXACTLY) {
            ancho = widthSize
        } else {
            ancho = TABLERO_SIZE_DEFAULT
            if (widthMode == MeasureSpec.AT_MOST && ancho > widthSize) {
                ancho = widthSize
            }
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            alto = heightSize
        } else {
            alto = TABLERO_SIZE_DEFAULT
            if (heightMode == MeasureSpec.AT_MOST && alto > heightSize) {
                alto = heightSize
            }
        }
        if (widthMode != MeasureSpec.EXACTLY) ancho = alto
        if (heightMode != MeasureSpec.EXACTLY) alto = ancho
        if (widthMode == MeasureSpec.AT_MOST && ancho > widthSize) ancho = widthSize
        if (heightMode == MeasureSpec.AT_MOST && alto > heightSize) alto = heightSize
        anchoCelda = (ancho - paddingLeft - paddingRight) / 9.0f
        altoCelda = (alto - paddingTop - paddingBottom) / 9.0f

        setMeasuredDimension(ancho, alto)

        val cellTextSize = altoCelda * 0.75f
        valorCeldaPaint.textSize = cellTextSize
        valorCeldaSoloLecturaPaint.textSize = cellTextSize
        valorCeldaInvalidoPaint.textSize = cellTextSize
        notaCeldaPaint.textSize = altoCelda / 3.0f
        // calcular los offsets en cada celda para centrar el número renderizado
        numeroIzq = ((anchoCelda - valorCeldaPaint.measureText("9")) / 2).toInt()
        numeroArriba = ((altoCelda - valorCeldaPaint.textSize) / 2).toInt()
        // añadir alguna compensación porque en algunas resoluciones las notas están cortadas en la parte superior
        notaArriba = altoCelda / 50.0f
        calcularAnchoLineaSector(ancho, alto)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val ancho = width - paddingRight
        val alto = height - paddingBottom
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop

        // Dibujar secondary background
        if (backgroundColorSecondary.color != NO_COLOR) {
            canvas.run {
                drawRect(3 * anchoCelda,
                    0F, 6 * anchoCelda, 3 * anchoCelda, backgroundColorSecondary)
                drawRect(0F, 3 * anchoCelda, 3 * anchoCelda, 6 * anchoCelda, backgroundColorSecondary)
                drawRect(6 * anchoCelda, 3 * anchoCelda, 9 * anchoCelda, 6 * anchoCelda, backgroundColorSecondary)
                drawRect(3 * anchoCelda, 6 * anchoCelda, 6 * anchoCelda, 9 * anchoCelda, backgroundColorSecondary)
            }
        }

        // Dibujar celdas
        var celdaLeft: Int
        var celdaTop: Int
        if (tablero != null) {
            val flagColorLecturaBackground =
                backgroundColorLectura.color != NO_COLOR
            val numeroAscent = valorCeldaPaint.ascent()
            val notaAscent = notaCeldaPaint.ascent()
            val anchoNota = anchoCelda / 3f
            for (row in 0..8) {
                for (col in 0..8) {
                    val celda: Celda = tablero!!.celdas[row][col]
                    celdaLeft = (col * anchoCelda + paddingLeft).roundToInt()
                    celdaTop = (row * altoCelda + paddingTop).roundToInt()
                    //Dibujar en modo solo lectura
                    if (!celda.editable && flagColorLecturaBackground) {
                        if (backgroundColorLectura.color != NO_COLOR) {
                            canvas.drawRect(
                                celdaLeft.toFloat(),
                                celdaTop.toFloat(), celdaLeft + anchoCelda, celdaTop + altoCelda,
                                backgroundColorLectura
                            )
                        }
                    }
                    // Dibujar Texto Celda
                    val valorCelda: Int = celda.value
                    if (valorCelda != 0) {
                        var valCeldaPaint =
                            if (celda.editable) valorCeldaPaint else valorCeldaSoloLecturaPaint
                        if (colorearErroneos && !celda.isValido) {
                            valCeldaPaint = valorCeldaInvalidoPaint
                        }
                        canvas.drawText(
                            valorCelda.toString(), (celdaLeft + numeroIzq).toFloat(),
                            celdaTop + numeroArriba - numeroAscent, valCeldaPaint
                        )
                    } else if (!celda.nota.isEmpty()) {
                        val numeros: Collection<Int> =
                            celda.nota.numeros
                        for (num in numeros) {
                            val n = num - 1
                            val c = n % 3
                            val r = n / 3
                            canvas.drawText(
                                num.toString(),
                                celdaLeft + c * anchoNota + 2,
                                celdaTop + notaArriba - notaAscent + r * anchoNota - 1,
                                notaCeldaPaint
                            )
                        }
                    }
                }
            }

            // Colorear celda seleccionada
            if (!soloLectura && celdaSeleccionada != null) {
                celdaLeft =
                    ((celdaSeleccionada!!.colIndex * anchoCelda).roundToInt() + paddingLeft)
                celdaTop = (celdaSeleccionada!!.rowIndex * altoCelda).roundToInt() + paddingTop
                canvas.run {
                    drawRect(
                                celdaLeft.toFloat(),
                                celdaTop.toFloat(),
                                celdaLeft + anchoCelda,
                                celdaTop + altoCelda,
                                backgroundColorSeleccionado
                            )
                }
            }
            // Resaltar visualmente la célula debajo del dedo
            if (colorearPulsados && celdaPulsada != null) {
                celdaLeft =
                    (this.celdaPulsada!!.colIndex * anchoCelda).roundToInt() + paddingLeft
                celdaTop = (this.celdaPulsada!!.rowIndex * altoCelda).roundToInt() + paddingTop
                canvas.run {
                    drawRect(
                                celdaLeft.toFloat(),
                                paddingTop.toFloat(),
                                celdaLeft + anchoCelda,
                                alto.toFloat(),
                                backgroundColorPulsado
                            )
                    drawRect(
                                paddingLeft.toFloat(),
                                celdaTop.toFloat(),
                                ancho.toFloat(),
                                celdaTop + altoCelda,
                                backgroundColorPulsado
                            )
                }
            }
        }

        // Dibujar lineas verticales
        for (c in 0..9) {
            val x = c * anchoCelda + paddingLeft
            canvas.drawLine(x, paddingTop.toFloat(), x, alto.toFloat(), lineaPaint)
        }
        // Dibujar lineas horizontales
        for (r in 0..9) {
            val y = r * altoCelda + paddingTop
            canvas.drawLine(paddingLeft.toFloat(), y, ancho.toFloat(), y, lineaPaint)
        }

        canvas.drawLine(0f,0f,x, y, lineaPaint)
        val anchoLineaSector1 = anchoLineaSector / 2
        val anchoLineaSector2 = anchoLineaSector1 + anchoLineaSector % 2

        // Dibujar sectores
        var c = 0
        while (c <= 9) {
            val x = c * anchoCelda + paddingLeft
            canvas.drawRect(
                x - anchoLineaSector1,
                paddingTop.toFloat(),
                x + anchoLineaSector2,
                alto.toFloat(),
                lineaSectorPaint
            )
            c += 3
        }

        var r = 0
        while (r <= 9) {
            val y = r * altoCelda + paddingTop
            canvas.drawRect(
                paddingLeft.toFloat(),
                y - anchoLineaSector1,
                ancho.toFloat(),
                y + anchoLineaSector2,
                lineaSectorPaint
            )
            r += 3
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!soloLectura) {
            val x = event.x.toInt()
            val y = event.y.toInt()
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> celdaPulsada =
                    getCeldaSituadaEn(x, y)
                MotionEvent.ACTION_UP -> {
                    celdaSeleccionada = getCeldaSituadaEn(x, y)
                    invalidate()
                    if (celdaSeleccionada != null) {
                        onTocarCelda(celdaSeleccionada)
                        onCeldaSeleccionada(celdaSeleccionada!!)
                    }
                    if (ocultarPulsados) {
                        celdaPulsada = null
                    }
                }
                MotionEvent.ACTION_CANCEL -> celdaPulsada = null
            }
            postInvalidate()
        }
        return !soloLectura
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (!soloLectura) {
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_UP -> return moverCeldaSeleccionada(0, -1)
                KeyEvent.KEYCODE_DPAD_RIGHT -> return moverCeldaSeleccionada(1, 0)
                KeyEvent.KEYCODE_DPAD_DOWN -> return moverCeldaSeleccionada(0, 1)
                KeyEvent.KEYCODE_DPAD_LEFT -> return moverCeldaSeleccionada(-1, 0)
                KeyEvent.KEYCODE_0, KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_DEL -> {
                    // Limpiar el valor de la celda seleccionada
                    if (celdaSeleccionada != null) {
                        if (event.isShiftPressed || event.isAltPressed) {
                            SudokuController.setNotaCelda(celdaSeleccionada!!, NotaCelda())
                        } else {
                            SudokuController.setValorCelda(celdaSeleccionada!!, 0)
                            moverCeldaSeleccionadaADerecha()
                        }
                    }
                    return true
                }
                KeyEvent.KEYCODE_DPAD_CENTER -> {
                    if (celdaSeleccionada != null) {
                        onTocarCelda(celdaSeleccionada)
                    }
                    return true
                }
            }
            if (keyCode >= KeyEvent.KEYCODE_1 && keyCode <= KeyEvent.KEYCODE_9) {
                val numSel: Int = keyCode - KeyEvent.KEYCODE_0
                val cell = celdaSeleccionada!!
                if (event.isShiftPressed || event.isAltPressed) {
                    // Añadir o eliminar numeros en las notas de las celdas
                    SudokuController.setNotaCelda(cell, cell.nota.anotarNumero(numSel))
                } else {
                    // Introducir un numero en una celda
                    SudokuController.setValorCelda(cell, numSel)
                    this.moverCeldaSeleccionadaADerecha()
                }
                return true
            }
        }
        return false
    }

}
