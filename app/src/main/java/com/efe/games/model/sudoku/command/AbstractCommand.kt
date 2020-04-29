package com.efe.games.model.sudoku.command


abstract class AbstractCommand {
    /**
     * Ejecuta el commando
     */
    abstract fun execute()

    /**
     * Deshace el comando
     */
    abstract fun undo()

    var isCheckpoint = false

    val commandClass: String
        get() = javaClass.simpleName

    companion object {
        fun nuevaInstancia(commandClass: String): AbstractCommand {
            return when (commandClass) {
                LimpiarNotasCommand::class.java.simpleName -> {
                    LimpiarNotasCommand()
                }
                EditNotaCeldaCommand::class.java.simpleName -> {
                    EditNotaCeldaCommand()
                }
                FillNotasCommand::class.java.simpleName -> {
                    FillNotasCommand()
                }
                SetValorCeldaCommand::class.java.simpleName -> {
                    SetValorCeldaCommand()
                }
                else -> {
                    throw IllegalArgumentException(String.format("Command class desconocido '%s'.", commandClass))
                }
            }
        }
    }
}
