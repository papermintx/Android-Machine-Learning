package com.dicoding.asclepius.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.domain.model.ArticlesItem
import com.dicoding.asclepius.R

class ArticlesAdapter(
    private var articles: List<ArticlesItem?> = listOf(),
    private val onArticleClick: (ArticlesItem) -> Unit
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(article: ArticlesItem) {
            titleTextView.text = article.title ?: "No Title"
            descriptionTextView.text = article.description ?: "No Description"
            article.urlToImage?.let { imageUrl ->
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .into(imageView)
            }
            val sourceName = article.source?.name ?: "Unknown Source"
            itemView.findViewById<TextView>(R.id.sourceTextView).text = sourceName

            itemView.setOnClickListener {
                onArticleClick(article)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        articles[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateArticles(newArticles: List<ArticlesItem?>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}
