package com.efe.games.model.sudoku

class GrupoCeldas(
    var celdas: MutableList<Celda>
) {
    fun addCelda(celda: Celda): Boolean = celdas.add(celda)
    fun contains(num: Int): Boolean = celdas.find { x -> x.value.equals(num) } == null

    /**
     * Valida los numeros de todas las casillas del grupo y marca
     * las que no sean validas
     */
    fun valido(): Boolean {
        var valorCeldas = HashMap<Int,Celda>()
        var valido = true
        for (x in celdas){
            if(valorCeldas.containsKey(x.value)){
                x.isValido = false
                valorCeldas[x.value]?.isValido = false
                valido = false
            }
            else{
                valorCeldas.put(x.value, x)
            }
        }
        return valido
    }
}