package com.aries.testalfa.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.aries.testalfa.HomeViewHolder
import com.aries.testalfa.R
import com.aries.testalfa.data.HomeItemModel
import com.aries.testalfa.listener.OnItemClickListener

class MainAdapter : RecyclerView.Adapter<HomeViewHolder>() {
    private val items: ArrayList<HomeItemModel> = ArrayList()
    private var context: Context? = null
    var listener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        context = parent.context
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false)
        val holder = HomeViewHolder(view)
//        holder.setOnItemClickListener(onItemClickListener)
        return holder
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item: HomeItemModel = items!![position]
            holder.ivIcon.setImageDrawable(context!!.applicationContext.getDrawable(item.icon))
            holder.tvTitle.setText(item.title)
            holder.cardView.setOnClickListener{
                listener?.onItemClick(it , items[position])
            }
    }

    override fun getItemCount(): Int {
        return items?.size
    }

    fun addItem(item: ArrayList<HomeItemModel>) {
        val count = item.size
        if (count != 0) {
            items!!.clear()
            items.addAll(item)
            notifyDataSetChanged()
        }
    }
}