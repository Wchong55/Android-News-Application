package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SourcesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)

        val search: String? = getIntent().getStringExtra("SEARCH")

        val title: String = getString(R.string.sources_title, search)
        setTitle(title)

        //Spinner code from the Spinner documentation page (19 - 30)
        val spinner: Spinner = findViewById(R.id.categories)
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
        }

        recyclerView = findViewById(R.id.news_headlines)

        val news = getFakeNews()
        val adapter = NewsAdapter(news)
        recyclerView.setAdapter(adapter)

        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getFakeNews(): List<News> {
        return listOf(
            News("Lost Ark is getting more servers to ease player congestion", "A popular MMO is facing congestion issues – have you heard of that one before? While Final Fantasy XIV had its fair share of challenges following Endwalker’s launch, it’s now Lost Ark’s turn to grapple with swelling player counts. As per SteamDB, the new PC game is hitting concurrent player counts of 500,000 on Valve’s storefront daily, and that’s before the free-to-play update."),
            News("NFL injury updates, latest league news from Friday, Feb. 10 ", "\n" +
                    "\n" +
                    "The Denver Broncos are nearing a deal with one of the most tenured coaches in the NFL.\n" +
                    "\n" +
                    "Denver is expected to hire longtime defensive coach Dom Capers as a senior defensive assistant to assist expected defensive coordinator Ejiro Evero, NFL Network Insider Ian Rapoport reported Friday. Capers, 71, worked in the same role with the Lions in 2021.\n"),
            News("News brief: COVID vaccines for kids, Russia-Ukraine crisis, Canadian protests", "Pfizer adds data to its request to get the OK for its COVID vaccine for young kids. Biden cautions Americans in Ukraine to leave. Truckers shut down border crossings between Canada and the U.S."),
            News("Deforestation in Brazil’s Amazon hits new record in January", "Brazil recorded the most deforestation ever in the Amazon rainforest for the month of January, according to new government data, as the destruction continues to worsen despite the government’s recent pledges to bring it under control.")
        )
    }
}