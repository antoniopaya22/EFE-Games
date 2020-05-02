package com.efe.games.business.tictactoe

//import khttp.get

import com.efe.games.model.tictactoe.ECodesTicTacToe
import com.efe.games.model.tictactoe.MoveRecommendation
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class MoveAPI(
    var recommendation: Int = 0

){

    fun getNextMove(state:String, player: ECodesTicTacToe):Int {
        var playerStr = "X"
        if(player == ECodesTicTacToe.P1_CODE) playerStr = "O"

/*
        var response:String =
            URL("https://stujo-tic-tac-toe-stujo-v1.p.rapidapi.com/" + state + "/" + player)
                .openConnection().headerFields
                .bufferedReader()
                .use { it.readText() }
*/
        /*
        val response = get(
            url = "https://stujo-tic-tac-toe-stujo-v1.p.rapidapi.com/" + state + "/" + player,
            headers = mapOf("x-rapidapi-host" to "stujo-tic-tac-toe-stujo-v1.p.rapidapi.com", "x-rapidapi-key" to "d40b614341mshf4a5605eee20775p153a8fjsnd1037e55df87"))
        val obj : JSONObject = response.jsonObject
        var rec:Int = obj["recommendation"] as Int
        */
        /*
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://something.com"))
            .build();
        val response = client.send(request, BodyHandlers.ofString());
        println(response.body())
        //val gson = Gson()
        //val recommendation: MoveRecommendation = gson.fromJson(response, MoveRecommendation::class.java)
        */

        val request = Request.Builder()
            .url("https://stujo-tic-tac-toe-stujo-v1.p.rapidapi.com/" + state + "/" + playerStr)
            .addHeader("x-rapidapi-host", "stujo-tic-tac-toe-stujo-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "d40b614341mshf4a5605eee20775p153a8fjsnd1037e55df87")
            .build()
        /*
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {println("ERROR ======================================================================\n" + e)}
            override fun onResponse(call: Call, response: Response) = println(response.body()?.string())
        })

         */

        val client = OkHttpClient()
        var recommendation:Int = 0
        var rec: MoveRecommendation =
            MoveRecommendation()
        var moveAPI: MoveAPI = this
        /*
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    var json: String? = response.body()?.string()
                    println(json)


                    val gson = Gson()

                    rec = gson.fromJson(json, MoveRecommendation::class.java)
                    saveRecommendation(rec.recommendation)
                }
            }
        })

         */
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code ======================================= $response")


            var json: String? = response.body()?.string()
            println(json)


            val gson = Gson()

            rec = gson.fromJson(json, MoveRecommendation::class.java)
            saveRecommendation(rec.recommendation)
        }

        /*

        */
        println(this.recommendation)
        return this.recommendation
    }

    fun saveRecommendation(rec:Int){
        this.recommendation = rec
    }
}