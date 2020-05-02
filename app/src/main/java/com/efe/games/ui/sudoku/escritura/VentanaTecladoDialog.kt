package com.efe.games.ui.sudoku.escritura

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.widget.LinearLayout.LayoutParams
import com.efe.games.R
import com.efe.games.business.sudoku.listeners.OnNotaEditadaListener
import com.efe.games.business.sudoku.listeners.OnNumeroEditadoListener


/**
 * Alert dialog con el teclado
 * Hereda de Dialog
 */
class VentanaTecladoDialog(context: Context) : Dialog(context) {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    private var inflater: LayoutInflater
    private var botonesNumeros: MutableMap<Int, Button> = mutableMapOf()
    private var botonesNotas: MutableMap<Int, ToggleButton> = mutableMapOf()
    private var notasNumerosSeleccionados: MutableSet<Int> = mutableSetOf()
    private var numeroSeleccionado = 0
    private lateinit var tabHost: TabHost

    var onNumeroEditadoListener: OnNumeroEditadoListener? = null
    var onNotaEditadaListener: OnNotaEditadaListener? = null

    /**
     *  ====================================================
     *                PROPIEDADES - LISTENERS
     *  ====================================================
     */

    private val botonNumeroClickListener: View.OnClickListener =
        View.OnClickListener { v ->
            val numero = v.tag as Int
            onNumeroEditadoListener!!.onNumeroEditado(numero)
            dismiss()
        }

    private val botonNotasClickListener: CompoundButton.OnCheckedChangeListener =
        CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val numero = buttonView.tag as Int
            if (isChecked) {
                notasNumerosSeleccionados.add(numero)
            } else {
                notasNumerosSeleccionados.remove(numero)
            }
        }

    private val clearButtonListener =
        View.OnClickListener {
            val tabActual: String? = tabHost.currentTabTag
            if (tabActual == "numero") {
                onNumeroEditadoListener!!.onNumeroEditado(0)
                dismiss()
            } else for (b in botonesNotas.values) {
                b.isChecked = false
                notasNumerosSeleccionados.remove(b.tag)
            }
        }

    private val closeButtonListener =
        View.OnClickListener {
            onNotaEditadaListener!!.onNoteEdit(notasNumerosSeleccionados.toList().toTypedArray())
            dismiss()
        }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    fun colorearNumero(number: Int) {
        botonesNumeros[number]!!.background.colorFilter =
                LightingColorFilter(Color.parseColor("#00695c"), 0)
    }

    fun resetButtons() {
        for ((key, value) in botonesNotas) {
            value.text = "$key"
        }
    }

    fun updateNumber(num: Int) {
        numeroSeleccionado = num
        for ((key, b) in botonesNumeros) {
            if (key == numeroSeleccionado) {
                b.setTextColor(Color.WHITE)
                b.background.colorFilter = LightingColorFilter(0x44FFFFFF, 0)
            } else {
                b.setTextColor(Color.WHITE)
                b.background.colorFilter = null
            }
        }
    }

    fun updateNota(nums: Collection<Int>?) {
        notasNumerosSeleccionados = HashSet()
        if (nums != null) {
            for (numero in nums) {
                notasNumerosSeleccionados.add(numero)
            }
        }
        for (numero in botonesNotas.keys) {
            botonesNotas[numero]!!.isChecked = notasNumerosSeleccionados.contains(numero)
        }
    }

    fun setContadorValores(num: Int, cont: Int) {
        val str = "$num ($cont)"
        botonesNumeros[num]!!.text = str
    }

    /**
     *  ====================================================
     *                      UI | TABS
     *  ====================================================
     */

    @SuppressLint("InflateParams")
    private fun crearValuesView() : View{
        val teclado = inflater.inflate(R.layout.ventana_teclado_dialog_valor, null)
        botonesNumeros = HashMap()
        botonesNumeros[1] = teclado.findViewById(R.id.button_1) as Button
        botonesNumeros[2] = teclado.findViewById(R.id.button_2) as Button
        botonesNumeros[3] = teclado.findViewById(R.id.button_3) as Button
        botonesNumeros[4] = teclado.findViewById(R.id.button_4) as Button
        botonesNumeros[5] = teclado.findViewById(R.id.button_5) as Button
        botonesNumeros[6] = teclado.findViewById(R.id.button_6) as Button
        botonesNumeros[7] = teclado.findViewById(R.id.button_7) as Button
        botonesNumeros[8] = teclado.findViewById(R.id.button_8) as Button
        botonesNumeros[9] = teclado.findViewById(R.id.button_9) as Button

        for (num in botonesNumeros.keys) {
            val b = botonesNumeros[num]
            b!!.tag = num
            b.setOnClickListener(botonNumeroClickListener)
        }

        (teclado.findViewById(R.id.button_close) as Button).setOnClickListener(closeButtonListener)
        (teclado.findViewById(R.id.button_clear) as Button).setOnClickListener(clearButtonListener)
        return teclado
    }

    @SuppressLint("InflateParams")
    private fun crearNotasView(): View {
        val teclado = inflater.inflate(R.layout.ventana_teclado_dialog_notas, null)
        botonesNotas = HashMap()
        botonesNotas[1] = teclado.findViewById(R.id.button_1) as ToggleButton
        botonesNotas[2] = teclado.findViewById(R.id.button_2) as ToggleButton
        botonesNotas[3] = teclado.findViewById(R.id.button_3) as ToggleButton
        botonesNotas[4] = teclado.findViewById(R.id.button_4) as ToggleButton
        botonesNotas[5] = teclado.findViewById(R.id.button_5) as ToggleButton
        botonesNotas[6] = teclado.findViewById(R.id.button_6) as ToggleButton
        botonesNotas[7] = teclado.findViewById(R.id.button_7) as ToggleButton
        botonesNotas[8] = teclado.findViewById(R.id.button_8) as ToggleButton
        botonesNotas[9] = teclado.findViewById(R.id.button_9) as ToggleButton

        for (num in botonesNotas.keys) {
            val b = botonesNotas[num]
            b!!.tag = num
            b.setOnCheckedChangeListener(botonNotasClickListener)
        }

        (teclado.findViewById(R.id.button_close) as Button).setOnClickListener(closeButtonListener)
        (teclado.findViewById(R.id.button_clear) as Button).setOnClickListener(clearButtonListener)
        return teclado
    }

    private fun createTabView(): TabHost? {
        tabHost = TabHost(context, null)
        tabHost.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )

        val linearLayout = LinearLayout(context)
        linearLayout.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        linearLayout.orientation = LinearLayout.VERTICAL

        val tabWidget = TabWidget(context)
        tabWidget.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        tabWidget.id = android.R.id.tabs

        val frameLayout = FrameLayout(context)
        frameLayout.id = android.R.id.tabcontent

        linearLayout.addView(tabWidget)
        linearLayout.addView(frameLayout)
        tabHost.addView(linearLayout)

        tabHost.setup()

        val numerosView: View = crearValuesView()
        val notasView: View = crearNotasView()
        tabHost.addTab(tabHost.newTabSpec("numero")
            .setIndicator("numero")
            .setContent { numerosView }
        )
        tabHost.addTab(tabHost.newTabSpec("nota")
            .setIndicator("nota")
            .setContent { notasView }
        )
        return tabHost
    }

    /**
     *  ====================================================
     *                      INIT
     *  ====================================================
     */
    init {
        context.setTheme(R.style.Theme_AppCompat_Dialog)
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tabs = createTabView()
        setContentView(tabs!!)
    }

}