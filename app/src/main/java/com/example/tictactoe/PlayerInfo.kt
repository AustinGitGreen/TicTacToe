package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class PlayerInfo : AppCompatActivity() {
    private lateinit var player1NameTextView: TextView
    private lateinit var player2NameTextView: TextView
    private lateinit var startGameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_info)

        player1NameTextView = findViewById(R.id.player1name)
        player2NameTextView = findViewById(R.id.player2name)
        startGameButton = findViewById(R.id.startGame)

        startGameButton.setOnClickListener {
            val player1Name = player1NameTextView.text?.toString()
            val player2Name = player2NameTextView.text?.toString()

            if (!player1Name.isNullOrEmpty() && !player2Name.isNullOrEmpty()) {
                // Use player1Name and player2Name in your game logic here

                // For example, you could pass the names to another activity using an Intent:
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("player1Name", player1Name)
                intent.putExtra("player2Name", player2Name)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter names for both players", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}