package com.example.myapplication

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject

class NewsManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
    }

    //Sources Page
    fun retrieveSourcesNews(category: String, apiKey: String): List<SourceNews> {
        //val searchQuery = "Android"
        val category = category

        val request: Request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines/sources?category=$category&apiKey=$apiKey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val news = mutableListOf<SourceNews>()
            val json: JSONObject = JSONObject(responseBody)
            val articles: JSONArray = json.getJSONArray("articles")

            for (i in 0 until articles.length()) {
                val curr: JSONObject = articles.getJSONObject(i)

                val source: JSONObject = curr.getJSONObject("source")
                val name: String = source.getString("name")

                val description: String = curr.getString("description")

                val new = SourceNews(
                    name = name,
                    description = description
                )

                news.add(new)
            }

            return news
        }

        return listOf()
    }

    //Maps Page
    fun retrieveMapNews(country: String, apiKey: String): List<MapNews> {

        val request: Request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=$country&searchIn=title&apiKey=$apiKey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val news = mutableListOf<MapNews>()

            val json: JSONObject = JSONObject(responseBody)
            val articles: JSONArray = json.getJSONArray("articles")

            for (i in 0 until articles.length()) {
                val curr: JSONObject = articles.getJSONObject(i)

                val source: JSONObject = curr.getJSONObject("source")
                val name: String = source.getString("name")

                val title: String = curr.getString("title")
                val description: String = curr.getString("description")

                val new = MapNews(
                    title = title,
                    name = name,
                    description = description
                )

                news.add(new)
            }

            return news
        }

        return listOf()
    }
}