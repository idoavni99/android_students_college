package com.yoniy.tic_tac_toe

class BoardData(private val boardSpots: List<BoardSpot>) {
    private var victoryVectors: List<VictoryVector> = initVictoryVectors()

    private fun initVictoryVectors(): List<VictoryVector> = listOf(
        VictoryVector(
            listOf(boardSpots[0], boardSpots[1], boardSpots[2]),
            VectorOrientation.HORIZONTAL
        ),
        VictoryVector(
            listOf(boardSpots[3], boardSpots[4], boardSpots[5]),
            VectorOrientation.HORIZONTAL
        ),
        VictoryVector(
            listOf(boardSpots[6], boardSpots[7], boardSpots[8]),
            VectorOrientation.HORIZONTAL
        ),
        VictoryVector(
            listOf(boardSpots[0], boardSpots[3], boardSpots[6]),
            VectorOrientation.VERTICAL
        ),
        VictoryVector(
            listOf(boardSpots[1], boardSpots[4], boardSpots[7]),
            VectorOrientation.VERTICAL
        ),
        VictoryVector(
            listOf(boardSpots[2], boardSpots[5], boardSpots[8]),
            VectorOrientation.VERTICAL
        ),
        VictoryVector(
            listOf(boardSpots[0], boardSpots[4], boardSpots[8]),
            VectorOrientation.LEFT_DIAGONAL
        ),
        VictoryVector(
            listOf(boardSpots[2], boardSpots[4], boardSpots[6]),
            VectorOrientation.RIGHT_DIAGONAL
        )
    )

    fun markSpot(place: Int, player: Player) {
        boardSpots[place].player = player.id
    }

    fun checkForVictory(player: Player): VictoryVector? {
        return victoryVectors
            .find { victoryVector ->
                victoryVector.boardSpots
                    .all { boardSpot -> boardSpot.player == player.id }
            }
    }

    fun filterOutInvalidVictoryVectors() {
        val validVictoryVectors: MutableList<VictoryVector> = mutableListOf()

        for (victoryVector in victoryVectors) {
            var vectorPlayer = BoardSpot.FREE_SPOT
            var isValid = true

            for (boardSpot in victoryVector.boardSpots) {
                if (vectorPlayer == BoardSpot.FREE_SPOT) {
                    vectorPlayer = boardSpot.player
                } else if (boardSpot.player != vectorPlayer && boardSpot.player != BoardSpot.FREE_SPOT) {
                    isValid = false
                    break
                }
            }

            if (isValid) validVictoryVectors.add(victoryVector)
        }

        victoryVectors = validVictoryVectors
    }

    fun isTie(): Boolean {
        return victoryVectors.isEmpty()
    }
}