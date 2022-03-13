package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class ResultsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        recyclerView = findViewById(R.id.recyclerview)

        val search: String? = getIntent().getStringExtra("SEARCH")
        var source: String? = getIntent().getStringExtra("SOURCE")

        if (source == null || source.isEmpty()) {
            source = "All"
        }

        val title: String = getString(R.string.results_title, search, source)

        val newsManager = NewsManager()
        val apiKey = getString(R.string.news_key)

        doAsync {
            val news: List<ResultNews> = try {
                if (source == null || source.isEmpty()) {
                    newsManager.retrieveResults(search.toString(), apiKey)
                }
                else {
                    newsManager.retrieveResultsSource(search.toString(), source, apiKey)
                }
            } catch(exception: Exception) {
                Log.e("ResultsActivity", "Retrieving News failed", exception)
                listOf<ResultNews>()
            }

            runOnUiThread {
                if (news.isNotEmpty()) {
                    val adapter = ResultNewsAdapter(news)
                    recyclerView.setAdapter(adapter)
                    recyclerView.layoutManager = LinearLayoutManager(this@ResultsActivity)
                } else {
                    Toast.makeText(
                        this@ResultsActivity,
                        "Failed to retrieve News!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}