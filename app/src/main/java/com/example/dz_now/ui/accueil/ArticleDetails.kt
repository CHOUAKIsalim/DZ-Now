package com.example.dz_now.ui.accueil

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.dz_now.R
import com.example.dz_now.entites.Article
import org.w3c.dom.Text
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ArticleDetails : AppCompatActivity() {


    private var toolbar:Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        toolbar =  findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val source : TextView = findViewById(R.id.tv_news_source)
        source.text = article!!.source

        val title : TextView = findViewById(R.id.tv_news_title)
        title.text = article!!.title

        val desc : TextView = findViewById(R.id.tv_news_desc)
        desc.text = article!!.resume

        val content : TextView = findViewById(R.id.tv_news_content)
        content.text = article!!.content

        val image : ImageView = findViewById(R.id.iv_news_image)
        if(article!!.image!="") {
            Glide.with(this)
                .load(article!!.image)
                .centerCrop()
                .into(image)
        }


        val time :TextView = findViewById<TextView>(R.id.tv_time)
        val inputFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        val inputFormat2: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            val date = inputFormat.parse(article!!.date)
            val niceDateStr = DateUtils.getRelativeTimeSpanString(
                date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            )
            time.text = niceDateStr

        }catch (e: Exception) {
            try {
                val date = inputFormat2.parse(article!!.date)
                val niceDateStr = DateUtils.getRelativeTimeSpanString(
                    date.time,
                    Calendar.getInstance().timeInMillis,
                    DateUtils.MINUTE_IN_MILLIS
                )
                time.text = niceDateStr
            }catch (e: Exception) {

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_article, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId

        if(id == R.id.action_favorite) {
            val intent = Intent(baseContext, ArticleWebView::class.java)
            ArticleWebView.article = article
            startActivityForResult(intent, Activity.RESULT_OK)
        }
        return super.onOptionsItemSelected(item);
    }
    companion object {
        var article : Article? = null
    }

}
