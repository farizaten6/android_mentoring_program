package com.example.androidmentoringprogram.thirdlesson

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmentoringprogram.R

class RecyclerViewNewsAdapter(private val articles: List<Article>, private val activity: Activity): RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder>() {

    class ViewHolder(item: View, activity: Activity): RecyclerView.ViewHolder(item) {
        lateinit var itemArticle: Article
        val itemAuthor: TextView = item.findViewById(R.id.rcViewNewsItemAuthor)
        val itemTitle: TextView = item.findViewById(R.id.newsItemTitle)
        val itemDate: TextView = item.findViewById(R.id.rcViewNewsItemDate)

        init {
            item.setOnClickListener {
                val fragmentActivityIntent = Intent(item.context, ArticleFragmentActivity::class.java)
                ArticleFragment.apply {
                    fragmentActivityIntent.putExtra(
                        FRAGMENT_TITLE_INTENT,
                        itemArticle.title
                    )
                    fragmentActivityIntent.putExtra(
                        FRAGMENT_SOURCE_INTENT,
                        itemArticle.source.name
                    )
                    fragmentActivityIntent.putExtra(
                        FRAGMENT_DESC_INTENT,
                        itemArticle.description
                    )
                    fragmentActivityIntent.putExtra(
                        FRAGMENT_IMAGE_URL_INTENT,
                        itemArticle.urlToImage
                    )
                }
                item.context.startActivity(
                    fragmentActivityIntent,
                    ActivityOptions.makeSceneTransitionAnimation(activity, itemTitle, "articleTitle").toBundle()
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_news_item, parent, false)
        return ViewHolder(view, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            itemArticle = articles.get(position)
            itemAuthor.text = articles.get(position).author
            itemTitle.text = articles.get(position).title
            itemDate.text = articles.get(position).publishedAt
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

}