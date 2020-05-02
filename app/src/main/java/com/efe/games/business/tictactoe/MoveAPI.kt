package com.efe.games.business.tictactoe


import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.model.tictactoe.MoveRecommendation
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.net.URL


class MoveAPI(){

    fun getNextMove(state:String, player: ECodesTicTacToe):Int {
        var playerStr = "O"
        if(player == ECodesTicTacToe.P1_CODE) playerStr = "X"
        val request = Request.Builder()
            .url("https://stujo-tic-tac-toe-stujo-v1.p.rapidapi.com/" + state + "/" + playerStr)
            .addHeader("x-rapidapi-host", "stujo-tic-tac-toe-stujo-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "d40b614341mshf4a5605eee20775p153a8fjsnd1037e55df87")
            .build()
        val client = OkHttpClient()
        var rec = MoveRecommendation()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code: $response")
            var json: String? = response.body()?.string()
            val gson = Gson()
            rec = gson.fromJson(json, MoveRecommendation::class.java)
        }

        return rec.recommendation
    }

}