package com.efe.games.model.sudoku.command

import com.efe.games.model.sudoku.Tablero
import java.util.*


class CommandStack(private val tablero: Tablero) {
    private val commandStack = Stack<AbstractCommand>()

    fun empty(): Boolean {
        return commandStack.empty()
    }

    fun execute(command: AbstractCommand) {
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

    fun setCheckpoint() {
        if (!commandStack.empty()) {
            val c = commandStack.peek()
            c.isCheckpoint = true
        }
    }

    fun hasCheckpoint(): Boolean {
        for (c in commandStack) if (c.isCheckpoint) return true
        return false
    }

    fun undoToCheckpoint() {
        var c: AbstractCommand
        while (!commandStack.empty()) {
            c = commandStack.pop()
            c.undo()
            if (commandStack.empty() || commandStack.peek().isCheckpoint) {
                break
            }
        }
        validarCeldas()
    }

    fun hasSomethingToUndo(): Boolean = commandStack.size != 0

    private fun push(command: AbstractCommand) {
        if (command is AbstractCeldaCommand) {
            command.setCells(tablero)
        }
        commandStack.push(command)
    }

    private fun pop(): AbstractCommand = commandStack.pop()

    private fun validarCeldas() = tablero.validar()

}
