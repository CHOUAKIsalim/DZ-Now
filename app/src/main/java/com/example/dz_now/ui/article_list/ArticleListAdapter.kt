package com.example.dz_now.ui.article_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dz_now.entites.Article
import java.text.SimpleDateFormat
import android.text.format.DateUtils
import com.example.dz_now.R
import java.lang.Exception
import java.util.*


class ArticleListAdapter(
    val context: Context,
    var list: List<Article>,
    val clickListener: (Article) -> Unit
) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArticleViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.article_item, p0, false)
        return ArticleViewHolder(v)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ArticleViewHolder, p1: Int) {

        val article: Article = list[p1]
        p0.titre.text = article.title
        p0.resume.text = article.category
        p0.contenu.text = article.resume
        p0.auteur.text = article.author

        val inputFormat: SimpleDateFormat  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        val inputFormat2: SimpleDateFormat  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            val date = inputFormat.parse(article.date)
            val niceDateStr = DateUtils.getRelativeTimeSpanString(
                date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            )
            p0.date.text = niceDateStr

        }catch (e:Exception) {
            try {
                val date = inputFormat2.parse(article.date)
                val niceDateStr = DateUtils.getRelativeTimeSpanString(
                    date.time,
                    Calendar.getInstance().timeInMillis,
                    DateUtils.MINUTE_IN_MILLIS
                )
                p0.date.text = niceDateStr
            }catch (e:Exception) {

            }
        }
        if(article.image!="") {
            Glide.with(context)
                .load(article.image)
                .centerCrop()
                .into(p0.image)
        }

        p0.linearLayout.setOnClickListener{clickListener(article)}
    }


    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titre = itemView.findViewById(R.id.titre) as TextView
        var resume = itemView.findViewById(R.id.resume) as TextView
        var auteur = itemView.findViewById(R.id.auteur) as TextView
        val image = itemView.findViewById(R.id.image) as ImageView
        val share = itemView.findViewById(R.id.share) as ImageView
        val date = itemView.findViewById(R.id.date) as TextView
        val contenu = itemView.findViewById(R.id.trail_text_card) as TextView
        val linearLayout = itemView.findViewById(R.id.linearLayout) as LinearLayout

    }
}

