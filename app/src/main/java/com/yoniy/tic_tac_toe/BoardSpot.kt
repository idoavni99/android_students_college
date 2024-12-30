package com.yoniy.tic_tac_toe

class BoardSpot(val spotPlace: Int) {
    var player: Int = FREE_SPOT

    companion object {
        const val FREE_SPOT = 0
    }

    override fun toString(): String {
        return "boardSpot=$player"
    }
}