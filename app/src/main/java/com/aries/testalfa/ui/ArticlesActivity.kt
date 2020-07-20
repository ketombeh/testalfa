package com.aries.testalfa.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.aries.testalfa.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_articles.*
import kotlinx.android.synthetic.main.layout_news_detail.*
import org.jetbrains.anko.indeterminateProgressDialog

class ArticlesActivity : AppCompatActivity() {
    private var titleNews: String? = null
    private var descNews: String? = null
    private var authorNews: String? = null
    private var imageNews: String? = null
    private var urlNews: String? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        if(intent.extras != null){
            val bundle = intent.extras
            titleNews = bundle.getString("title")
            descNews = bundle.getString("desc")
            authorNews = bundle.getString("author")
            imageNews = bundle.getString("image")
            urlNews = bundle.getString("url")
        }

        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item!!.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupView() {
        try {
            tvTitle.text = titleNews
            tvDirector.text = authorNews
            tvWriter.text = descNews
            Glide.with(this).load(imageNews).into(ivPoster)
            tvDetail.setOnClickListener(View.OnClickListener {
                var intent = Intent(applicationContext , WebviewActivity::class.java)
                intent.putExtra("url" , urlNews)
                startActivity(intent)
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}