package com.aries.testalfa.listener

import android.view.View
import com.aries.testalfa.data.HomeItemModel

interface OnItemClickListener {
    fun onItemClick(v: View?, model: HomeItemModel)
}