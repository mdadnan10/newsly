package com.newses.newsly

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(val context: Context, val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolders>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolders {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ArticleViewHolders(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolders, position: Int) {
        val article = articles[position]
        holder.newsTitle.text = article.title
        holder.newsDescription.text = article.description
        Glide.with(context).load(article.urlToImage).into(holder.newsImage)
        holder.itemView.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("URL", article.url)
            context.startActivity(intent)
            Toast.makeText(context, article.title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage = itemView.findViewById<ImageView>(R.id.imgNews)
        var newsTitle = itemView.findViewById<TextView>(R.id.tvNewsTitle)
        var newsDescription = itemView.findViewById<TextView>(R.id.tvNewsDescription)
    }
}