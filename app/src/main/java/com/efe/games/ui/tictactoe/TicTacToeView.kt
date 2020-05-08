package com.efe.games.ui.tictactoe

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.efe.games.R

class TicTacToeView(context: Context, st: AttributeSet) : View(context, st) {

    private val linePaint = Paint() // Line
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private val backgroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private var cellWidth: Float = 0f

    companion object{
        const val TABLERO_SIZE_DEFAULT = 100
        const val NUMBER_OF_CELLS = 3.0f
        const val LINES_MARGIN = 60.0f
        const val LINES_MARGIN_BT = 180.0f
    }

    private fun init(attrs: AttributeSet?) {
        linePaint.color = Color.WHITE
    }

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

    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        extraBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        //canvas.drawRGB(255, 255, 255)

        linePaint.setARGB(255, 255, 255, 255)
        linePaint.strokeWidth = 20f
        cellWidth = (width / NUMBER_OF_CELLS)
        canvas.drawLine(cellWidth, LINES_MARGIN, cellWidth,cellWidth*3 - LINES_MARGIN_BT, linePaint)
        canvas.drawLine(cellWidth*2, LINES_MARGIN, cellWidth*2,cellWidth*3 - LINES_MARGIN_BT, linePaint)
        canvas.drawLine(LINES_MARGIN, cellWidth - 25f, cellWidth*3 - LINES_MARGIN, cellWidth - 25f, linePaint)
        canvas.drawLine(LINES_MARGIN, cellWidth*2 - 75f, cellWidth*3 - LINES_MARGIN, cellWidth*2 - 75f, linePaint)
    }
}