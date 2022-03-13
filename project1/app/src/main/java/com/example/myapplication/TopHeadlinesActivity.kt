package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class TopHeadlinesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_headlines)

        recyclerView = findViewById(R.id.recyclerview)

        //Spinner code from the Spinner documentation page (19 - 30)
        val spinner: Spinner = findViewById(R.id.categories)
        var cat: String = spinner.getSelectedItem().toString()

        val newsManager = NewsManager()
        val apiKey = getString(R.string.news_key)

        ArrayAdapter.createFromResource(
            this,
            R.array.categories_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val cat: String = spinner.getSelectedItem().toString()

                    doAsync {

                        val news: List<TopNews> = try {
                            newsManager.retrieveTopNews(cat, apiKey)
                        } catch(exception: Exception) {
                            Log.e("NewsActivity", "Retrieving News failed", exception)
                            listOf<TopNews>()
                        }

                        runOnUiThread {
                            if (news.isNotEmpty()) {
                                val adapter = TopNewsAdapter(news)
                                recyclerView.setAdapter(adapter)
                                recyclerView.layoutManager = LinearLayoutManager(this@TopHeadlinesActivity)
                            }
                            else {
                                Toast.makeText(
                                    this@TopHeadlinesActivity,
                                    "Failed to retrieve News!",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }
    }
}
