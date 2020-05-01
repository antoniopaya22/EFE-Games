package com.efe.games.business.sudoku.command

/**
 * Interfaz del Patron Command
 */
interface EFECommand {
    /**
     * Ejecuta el commando
     */
    fun execute()

    /**
     * Deshace el comando
     */
    fun undo()

    var isCheckpoint: Boolean

}
