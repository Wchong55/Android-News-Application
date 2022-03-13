package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ResultNewsAdapter(val news: List<ResultNews>) : RecyclerView.Adapter<ResultNewsAdapter.ViewHolder>() {

    class ViewHolder(rootLayout:View) : RecyclerView.ViewHolder(rootLayout) {
        val headline: TextView = rootLayout.findViewById(R.id.headline)
        val contentText: TextView = rootLayout.findViewById(R.id.news_content)
        val brand: TextView = rootLayout.findViewById(R.id.news_title)
        val imageView: ImageView = rootLayout.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultNewsAdapter.ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val rootLayout: View = layoutInflater.inflate(R.layout.column_news, parent, false)

        val viewHolder = ResultNewsAdapter.ViewHolder(rootLayout)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ResultNewsAdapter.ViewHolder, position: Int) {
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

        //        holder.card.setOnClickListener{view: View ->
//            override fun onClick(view: View) {
//                val browse: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.url))
//                startActivity(view, browse)
//            }
//        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}