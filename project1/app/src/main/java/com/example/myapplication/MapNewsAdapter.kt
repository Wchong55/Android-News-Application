package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MapNewsAdapter(val news: List<MapNews>) : RecyclerView.Adapter<MapNewsAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View) : RecyclerView.ViewHolder(rootLayout) {
        val headline: TextView = rootLayout.findViewById(R.id.headline)
        val contentText: TextView = rootLayout.findViewById(R.id.news_content)
        val brand: TextView = rootLayout.findViewById(R.id.news_title)
        val imageView: ImageView = rootLayout.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val rootLayout: View = layoutInflater.inflate(R.layout.column_news, parent, false)

        val viewHolder = ViewHolder(rootLayout)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNews = news[position]
        holder.headline.setText(currentNews.name)
        holder.contentText.setText(currentNews.description)
        holder.brand.setText(currentNews.title)

        if (currentNews.iconUrl.isNotEmpty()) {
            Picasso.get().setIndicatorsEnabled(true)

            Picasso
                .get()
                .load(currentNews.iconUrl)
                .into(holder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}