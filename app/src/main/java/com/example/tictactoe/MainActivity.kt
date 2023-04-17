package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Array<Button>>
    private var ticTacToe = TicTacToe()
    private val player1 = Player("One")
    private val player2 = Player("Two")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttons = arrayOf(
            arrayOf(findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3)),
            arrayOf(findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6)),
            arrayOf(findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9))
        )


        // Add click listeners to the buttons
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                buttons[i][j].setOnClickListener {
                    if (ticTacToe.makeMove(i, j)) {
                        updateUI()
                        val winner = ticTacToe.checkWin()
                        if (winner != 0) {
                            showWinner(winner)
                        } else if (ticTacToe.isDraw()) {
                            showDraw()
                        } else {
                            ticTacToe.switchPlayer()
                        }
                    }
                }
            }
        }
    }

    // Update the text of the buttons to show X or O based on the game state
    private fun updateUI() {
        for (i in buttons.indices) {
            for (j in buttons[i].indices) {
                val value = ticTacToe.getBoard()[i][j]
                if (value == 1) {
                    buttons[i][j].text = "X"
                } else if (value == 2) {
                    buttons[i][j].text = "O"
                } else {
                    buttons[i][j].text = ""
                }
            }
        }
    }

    // Show a message indicating the winner
    private fun showWinner(winner: Int) {
        val message = when (winner) {
            1 -> {
                player1.addWin()
                "${player1.name} wins! (${player1.wins} wins)"
            }
            -1 -> "Draw"
            else -> {
                player2.addWin()
                "${player2.name} wins! (${player2.wins} wins)"
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        ticTacToe.reset()
        updateUI()
    }

    // Show a message indicating a draw
    private fun showDraw() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
        ticTacToe.reset()
        updateUI()
    }



}
private class TicTacToe {
    private val board = Array(3) { IntArray(3) { 0 } }
    private var currentPlayer = 1
    private val player1 = Player("One")
    private val player2 = Player("Two")

    fun getBoard(): Array<IntArray> {
        return board
    }

    fun getCurrentPlayer(): Int {
        return currentPlayer
    }

    fun switchPlayer() {
        currentPlayer = if (currentPlayer == 1) 2 else 1
    }

    fun makeMove(row: Int, col: Int): Boolean {
        if (board[row][col] == 0) {
            board[row][col] = currentPlayer
            if (currentPlayer == 1) {
                player1.addWin()
            } else {
                player2.addWin()
            }
            return true
        }
        return false
    }

    fun checkWin(): Int {
        // Check rows
        for (i in 0..2) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0]
            }
        }

        // Check columns
        for (j in 0..2) {
            if (board[0][j] != 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j]
            }
        }

        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0]
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2]
        }

        // Check for a draw
        var isDraw = true
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == 0) {
                    isDraw = false
                    break
                }
            }
            if (!isDraw) break
        }
        if (isDraw) {
            return -1
        }

        return 0
    }

    fun reset() {
        board.forEach { row ->
            row.forEachIndexed { index, _ ->
                row[index] = 0
            }
        }
        player1.resetWins()
        player2.resetWins()
        currentPlayer = 1
    }

    fun isDraw(): Boolean {
        return checkWin() == 0 && !isCellAvailable()
    }

    private fun isCellAvailable(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == 0) {
                    return true
                }
            }
        }
        return false
    }
}
class Player(var name: String) {

    var wins: Int = 0
    fun resetWins() {
        wins = 0
    }

    fun addWin() {
        wins++
    }


}






