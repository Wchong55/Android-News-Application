package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class HomeActivity : AppCompatActivity() {

    private lateinit var search: EditText
    private lateinit var searchBtn: Button
    private lateinit var locBtn: Button
    private lateinit var tophlBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //UI values
        search = findViewById(R.id.Search)
        searchBtn = findViewById(R.id.search_btn)
        locBtn = findViewById(R.id.location_btn)
        tophlBtn = findViewById(R.id.headline_btn)

        searchBtn.setOnClickListener {view: View ->
            Log.d("HomeActivity", "Search clicked!")

            val intent: Intent = Intent(this, SourcesActivity::class.java)
            intent.putExtra("SEARCH", "Android")
            startActivity(intent)
        }
        locBtn.setOnClickListener {view: View ->
            Log.d("HomeActivity", "Location clicked!")

            //val intent: Intent = Intent(this, SourcesActivity::class.java)
            //startActivity(intent)
        }

        tophlBtn.setOnClickListener {view: View ->
            Log.d("HomeActivity", "Top Headlines clicked!")

            //val intent: Intent = Intent(this, SourcesActivity::class.java)
            //startActivity(intent)
        }

        searchBtn.isEnabled = false

        search.addTextChangedListener(textWatcher)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            val inputtedSearch: String = search.text.toString()
            val enableButton: Boolean = inputtedSearch.isNotBlank()

            searchBtn.isEnabled = enableButton
        }

        override fun afterTextChanged(p0: Editable?) {}

    }
}