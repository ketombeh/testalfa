package com.aries.testalfa

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var cardView: CardView
    var ivIcon: ImageView
    var tvTitle: TextView
    private var listener: AdapterView.OnItemClickListener? = null
    override fun onClick(view: View) {
//        listener?.onItemClick(view, adapterPosition)
    }

    fun setOnItemClickListener(listener: AdapterView.OnItemClickListener?) {
        this.listener = listener
    }

    init {
        cardView = itemView.findViewById(R.id.cardview)
        ivIcon = itemView.findViewById(R.id.iv_menu_icon)
        tvTitle = itemView.findViewById(R.id.tv_menu_title)
        itemView.setOnClickListener(this)
    }
}
