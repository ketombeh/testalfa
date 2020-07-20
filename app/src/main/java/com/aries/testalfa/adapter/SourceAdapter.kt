package com.aries.testalfa.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.aries.testalfa.R
import com.aries.testalfa.network.response.Articles
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_news.view.*

class SourceAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: OnItemClickListener? = null
    private var inflater: LayoutInflater? = LayoutInflater.from(context)
    var items: ArrayList<Articles> = ArrayList()
    var isLoading: Boolean = false

    companion object {
        const val TYPE_ITEM: Int = 1
        const val TYPE_FOOTER: Int = 0
    }

    fun setListener(onItemClickListener: OnItemClickListener) {
        this.listener = onItemClickListener
    }

    fun addItems(result: ArrayList<Articles>, isAppend: Boolean) {
        try {
            if (!isAppend) {
                items.clear()
            } else {
                removeFooter()
            }
            items.addAll(result)
            addFooter()
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Articles {
        return items[position]
    }

    override fun getItemViewType(position: Int): Int {
        val item: Articles = getItem(position)
        return if (item.itemType == 1) TYPE_ITEM else TYPE_FOOTER
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                val view: View = inflater!!.inflate(R.layout.item_news, container, false)
                NewsViewHolder(view)
            }
            TYPE_FOOTER -> {
                val view: View = inflater!!.inflate(R.layout.layout_footer_, container, false)
                NewsHeaderViewHodler(view)
            }
            else -> {
                val view: View = inflater!!.inflate(R.layout.item_news, container, false)
                NewsViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        if (position == items.size - 2) {
//            listener!!.onLoadMore()
//        }

        val item: Articles = getItem(position)
        if (holder is NewsViewHolder) {
            val newsHolder: NewsViewHolder = holder
            newsHolder.title.text = item.title
            newsHolder.type.text = item.description
            Glide.with(context).load(item.urlToImage).into(holder.poster)
            val font = Typeface.createFromAsset(context.assets, "fonts/Poppins-SemiBold.ttf")
            newsHolder.title.setTypeface(font)
            val fonts = Typeface.createFromAsset(context.assets, "fonts/Poppins-Regular.ttf")
            newsHolder.type.setTypeface(fonts)
            newsHolder.cardview.setOnClickListener {
                listener!!.onItemClick(it, position)
            }
        }
    }

    // handling for footer view type

    fun addFooter() {
        isLoading = true
        items.add(Articles(itemType = 0))
        notifyItemChanged(items.size - 1)
    }

    fun removeFooter() {
        isLoading = false
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size)
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardview = itemView.cardview
        val poster = itemView.ivPoster
        val title = itemView.tvTitle
        val type = itemView.tvType
        val year = itemView.tvYear
        val detail = itemView.tvDetail
    }

    inner class NewsHeaderViewHodler(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int)
    }
}