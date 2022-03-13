package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync
import android.widget.Button


class SourcesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var skip: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)

        val search: String? = getIntent().getStringExtra("SEARCH")

        val title: String = getString(R.string.sources_title, search)
        setTitle(title)

        recyclerView = findViewById(R.id.recyclerview)
        skip = findViewById(R.id.skip)

        //Spinner code from the Spinner documentation page (19 - 30)
        val spinner: Spinner = findViewById(R.id.categories)
        var cat: String = spinner.getSelectedItem().toString()

        val newsManager = NewsManager()
        val apiKey = getString(R.string.news_key)
//        doAsync {
//
//            val news: List<SourceNews> = try {
//                newsManager.retrieveSourcesNews(cat, apiKey)
//            } catch(exception: Exception) {
//                Log.e("NewsActivity", "Retrieving News failed", exception)
//                listOf<SourceNews>()
//            }
//
//            runOnUiThread {
//                if (news.isNotEmpty()) {
//                    val adapter = SourceNewsAdapter(news)
//                    recyclerView.setAdapter(adapter)
//                    recyclerView.layoutManager = LinearLayoutManager(this@SourcesActivity)
//                }
//                else {
//                    Toast.makeText(
//                        this@SourcesActivity,
//                        "Failed to retrieve News!",
//                        Toast.LENGTH_LONG).show()
//                }
//            }
//        }

        // Create an ArrayAdapter using the string array and a default spinner layout
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

                        val news: List<SourceNews> = try {
                            newsManager.retrieveSourcesNews(cat, apiKey)
                        } catch(exception: Exception) {
                            Log.e("NewsActivity", "Retrieving News failed", exception)
                            listOf<SourceNews>()
                        }

                        runOnUiThread {
                            if (news.isNotEmpty()) {
                                val adapter = SourceNewsAdapter(news)
                                recyclerView.setAdapter(adapter)
                                recyclerView.layoutManager = LinearLayoutManager(this@SourcesActivity)
                            }
                            else {
                                Toast.makeText(
                                    this@SourcesActivity,
                                    "Failed to retrieve News!",
                                    Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
        }

        skip.setOnClickListener { view: View ->
            Log.d("SourcesActivity", "Skip clicked!")

            val intent: Intent = Intent(this, ResultsActivity::class.java)
            intent.putExtra("SEARCH", search)

            startActivity(intent)
        }


        //Fake News

//        val news = getFakeNews()
//        val adapter = SourceNewsAdapter(news)
//        recyclerView.setAdapter(adapter)
//
//        recyclerView.layoutManager = LinearLayoutManager(this)


        //private fun generateNews()
        //Real News
//
//    }


//    private fun getFakeNews(): List<SourceNews> {
//        return listOf(
//            SourceNews("Lost Ark is getting more servers to ease player congestion", "A popular MMO is facing congestion issues – have you heard of that one before? While Final Fantasy XIV had its fair share of challenges following Endwalker’s launch, it’s now Lost Ark’s turn to grapple with swelling player counts. As per SteamDB, the new PC game is hitting concurrent player counts of 500,000 on Valve’s storefront daily, and that’s before the free-to-play update."),
//            SourceNews("NFL injury updates, latest league news from Friday, Feb. 10 ", "\n" +
//                    "\n" +
//                    "The Denver Broncos are nearing a deal with one of the most tenured coaches in the NFL.\n" +
//                    "\n" +
//                    "Denver is expected to hire longtime defensive coach Dom Capers as a senior defensive assistant to assist expected defensive coordinator Ejiro Evero, NFL Network Insider Ian Rapoport reported Friday. Capers, 71, worked in the same role with the Lions in 2021.\n"),
//            SourceNews("News brief: COVID vaccines for kids, Russia-Ukraine crisis, Canadian protests", "Pfizer adds data to its request to get the OK for its COVID vaccine for young kids. Biden cautions Americans in Ukraine to leave. Truckers shut down border crossings between Canada and the U.S."),
//            SourceNews("Deforestation in Brazil’s Amazon hits new record in January", "Brazil recorded the most deforestation ever in the Amazon rainforest for the month of January, according to new government data, as the destruction continues to worsen despite the government’s recent pledges to bring it under control.")
//        )
   }
}

