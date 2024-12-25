package com.yoniy.tic_tac_toe

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var gridSpotsViews: List<ImageView>
    private lateinit var playButton: Button
    private lateinit var playerTurnDisplay: TextView
    private val gameNameTitle: TextView by lazy { findViewById(R.id.gameNameView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        gridSpotsViews = getGridSpotViewRefs()
        initGridSpotStyles()

        playerTurnDisplay = findViewById(R.id.playerTurnDisplay)
        playButton = findViewById(R.id.playButton)

        playButton.setOnClickListener {
            playButton.isVisible = false
            playGame()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun getGridSpotViewRefs(): List<ImageView> {
        return listOf(
            findViewById(R.id.gridSpot0),
            findViewById(R.id.gridSpot1),
            findViewById(R.id.gridSpot2),
            findViewById(R.id.gridSpot3),
            findViewById(R.id.gridSpot4),
            findViewById(R.id.gridSpot5),
            findViewById(R.id.gridSpot6),
            findViewById(R.id.gridSpot7),
            findViewById(R.id.gridSpot8)
        )
    }

    private fun getResourcesColor(rawColor: Int): Int {
        return ResourcesCompat.getColor(resources, rawColor, null)
    }

    private fun initGridSpotStyles() {
        val isNightMode =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        val gridSpotColor =
            if (isNightMode) getResourcesColor(R.color.dark_theme_grey) else getResourcesColor(R.color.light_theme_grey)


        gridSpotsViews.forEach { gridSpot ->
            gridSpot.setBackgroundColor(gridSpotColor)
        }
    }

    private fun getTurnPlayer(players: List<Player>, numMoves: Int): Player {
        return players[numMoves % 2]
    }

    private fun playGame() {
        gridSpotsViews.forEach { gridSpot ->
            gridSpot.setImageDrawable(null)
        }

        val players = listOf(
            Player(1, R.drawable.red_x, getResourcesColor(R.color.red), "Player One"),
            Player(2, R.drawable.blue_o, getResourcesColor(R.color.blue), "Player Two")
        )

        val boardData = BoardData(
            List(gridSpotsViews.size) { spotPlace ->
                BoardSpot(spotPlace)
            }.toList()
        )

        var numMoves = 0
        var turnPlayer = getTurnPlayer(players, numMoves)
        displayTurnPlayerText(turnPlayer)

        for (spotPlace in gridSpotsViews.indices) {
            val gridSpotView = gridSpotsViews[spotPlace]

            gridSpotView.setOnClickListener {
                gridSpotView.setImageResource(turnPlayer.icon)
                gridSpotView.isClickable = false
                boardData.markSpot(spotPlace, turnPlayer)

                val gameState = getGameState(numMoves, turnPlayer, boardData)

                when (gameState) {
                    GameState.IN_PROGRESS -> {
                        numMoves++
                        turnPlayer = getTurnPlayer(players, numMoves)
                        displayTurnPlayerText(turnPlayer)
                    }

                    GameState.VICTORY -> handleVictory(turnPlayer)
                    GameState.TIE -> handleTie()
                }
            }
        }
    }

    private fun handleEndGame(playerDisplayText: String, textColor: Int?) {
        playerTurnDisplay.text = playerDisplayText

        textColor?.let {
            playerTurnDisplay.setTextColor(it)
        }

        playButton.text = "PLAY AGAIN?"
        playButton.isVisible = true
        gridSpotsViews.forEach { gridSpot -> gridSpot.isClickable = false }
    }

    private fun handleVictory(turnPlayer: Player) {
        handleEndGame("${turnPlayer.name} Wins!!! 🎉🎉", null)
    }

    private fun handleTie() {
        handleEndGame("Game Ended as Tie 😔", gameNameTitle.currentTextColor)
    }

    private fun displayTurnPlayerText(turnPlayer: Player) {
        playerTurnDisplay.text = "${turnPlayer.name}'s turn"
        playerTurnDisplay.setTextColor(turnPlayer.color)
        playerTurnDisplay.isVisible = true
    }

    private fun getGameState(
        numMoves: Int, turnPlayer: Player, boardData: BoardData
    ): GameState {
        val leastAmountOfMoves = 4

        if (numMoves < leastAmountOfMoves) {
            return GameState.IN_PROGRESS
        }

        val victoryVector = boardData.checkForVictory(turnPlayer)

        return victoryVector?.let { GameState.VICTORY }
            ?: run {
                boardData.filterOutInvalidVictoryVectors()

                if (boardData.isTie()) GameState.TIE else GameState.IN_PROGRESS
            }
    }

}