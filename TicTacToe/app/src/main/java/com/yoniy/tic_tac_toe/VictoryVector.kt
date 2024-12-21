package com.yoniy.tic_tac_toe

data class VictoryVector(
    val boardSpots: List<BoardSpot>,
    val vectorOrientation: VectorOrientation
) {
    fun getEndPlaces(): Pair<Int, Int> {
        return boardSpots.first().spotPlace to boardSpots.last().spotPlace
    }
}