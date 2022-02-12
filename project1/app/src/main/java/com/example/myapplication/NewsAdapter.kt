package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(val news: List<News>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View) : RecyclerView.ViewHolder(rootLayout) {
        val headline: TextView = rootLayout.findViewById(R.id.headline)
        val contentText: TextView = rootLayout.findViewById(R.id.news_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val rootLayout: View = layoutInflater.inflate(R.layout.row_news, parent, false)

        val viewHolder = ViewHolder(rootLayout)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNews = news[position]
        holder.headline.setText(currentNews.headline)
        holder.contentText.setText(currentNews.content)
    }

    override fun getItemCount(): Int {
        return news.size
    }
}