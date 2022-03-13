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
            val sources: JSONArray = json.getJSONArray("sources")

            for (i in 0 until sources.length()) {
                val curr: JSONObject = sources.getJSONObject(i)

                val name: String = curr.getString("name")
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
            .url("https://newsapi.org/v2/everything?qInTitle=$country&searchIn=title&language=en&apiKey=$apiKey")
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
                val imageIcon: String = curr.getString("urlToImage")

                val url: String = curr.getString("url")

                val new = MapNews(
                    title = title,
                    name = name,
                    description = description,
                    iconUrl = imageIcon,
                    url = url
                )

                news.add(new)
            }

            return news
        }

        return listOf()
    }

    fun retrieveTopNews(category: String, apiKey: String): List<TopNews>
    {

        val request: Request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?country=us&category=$category&apiKey=$apiKey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val news = mutableListOf<TopNews>()

            val json: JSONObject = JSONObject(responseBody)
            val articles: JSONArray = json.getJSONArray("articles")

            for (i in 0 until articles.length()) {
                val curr: JSONObject = articles.getJSONObject(i)

                val source: JSONObject = curr.getJSONObject("source")
                val name: String = source.getString("name")

                val title: String = curr.getString("title")
                val description: String = curr.getString("description")
                val imageIcon: String = curr.getString("urlToImage")

                val url: String = curr.getString("url")

                val new = TopNews(
                    title = title,
                    name = name,
                    description = description,
                    iconUrl = imageIcon,
                    url = url
                )

                news.add(new)
            }

            return news
        }

        return listOf()
    }

    fun retrieveResultsSource(search: String, source: String, apiKey: String): List<ResultNews> {
        val request: Request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=$search&source=$source&apiKey=$apiKey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val news = mutableListOf<ResultNews>()

            val json: JSONObject = JSONObject(responseBody)
            val articles: JSONArray = json.getJSONArray("articles")

            for (i in 0 until articles.length()) {
                val curr: JSONObject = articles.getJSONObject(i)

                val source: JSONObject = curr.getJSONObject("source")
                val name: String = source.getString("name")

                val title: String = curr.getString("title")
                val description: String = curr.getString("description")
                val imageIcon: String = curr.getString("urlToImage")

                val url: String = curr.getString("url")

                val new = ResultNews(
                    title = title,
                    name = name,
                    description = description,
                    iconUrl = imageIcon,
                    url = url
                )

                news.add(new)
            }

            return news
        }
        return listOf()
    }

    fun retrieveResults(search: String, apiKey: String): List<ResultNews> {
        val request: Request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=$search&apiKey=$apiKey")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val news = mutableListOf<ResultNews>()

            val json: JSONObject = JSONObject(responseBody)
            val articles: JSONArray = json.getJSONArray("articles")

            for (i in 0 until articles.length()) {
                val curr: JSONObject = articles.getJSONObject(i)

                val source: JSONObject = curr.getJSONObject("source")
                val name: String = source.getString("name")

                val title: String = curr.getString("title")
                val description: String = curr.getString("description")
                val imageIcon: String = curr.getString("urlToImage")

                val url: String = curr.getString("url")

                val new = ResultNews(
                    title = title,
                    name = name,
                    description = description,
                    iconUrl = imageIcon,
                    url = url
                )

                news.add(new)
            }

            return news
        }
        return listOf()
    }
}