package com.efe.games.business.sudoku.command

import com.efe.games.business.sudoku.SudokuManager
import java.util.*


class CommandStack {
    private val commandStack = Stack<EFECommand>()

    fun execute(command: EFECommand) {
        push(command)
        command.execute()
    }

    fun undo() {
        if (!commandStack.empty()) {
            val c = pop()
            c.undo()
            validarCeldas()
        }
    }


    private fun push(command: EFECommand) = commandStack.push(command)

    private fun pop(): EFECommand = commandStack.pop()

    private fun validarCeldas() = SudokuManager.validarTablero()

}
