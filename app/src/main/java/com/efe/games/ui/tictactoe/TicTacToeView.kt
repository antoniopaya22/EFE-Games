package com.efe.games.ui.tictactoe

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
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
        const val NUMBER_OF_CELLS = 3.0f
        const val LINES_MARGIN = 60.0f
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

        linePaint.setARGB(255, 255, 255, 255)
        linePaint.strokeWidth = 20f
        cellWidth = (width / NUMBER_OF_CELLS)
        canvas.drawLine(cellWidth, LINES_MARGIN, cellWidth,cellWidth*3, linePaint)
        canvas.drawLine(cellWidth*2, LINES_MARGIN, cellWidth*2,cellWidth*3, linePaint)
        canvas.drawLine(LINES_MARGIN, cellWidth + LINES_MARGIN/2, cellWidth*3 - LINES_MARGIN, cellWidth + LINES_MARGIN/2, linePaint)
        canvas.drawLine(LINES_MARGIN, cellWidth*2 + LINES_MARGIN/2, cellWidth*3 - LINES_MARGIN, cellWidth*2 + LINES_MARGIN/2, linePaint)
    }
}