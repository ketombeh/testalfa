package com.aries.testalfa.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.aries.testalfa.R
import com.aries.testalfa.adapter.MainAdapter
import com.aries.testalfa.data.HomeItemModel
import com.aries.testalfa.listener.OnItemClickListener
import com.aries.testalfa.util.ConvertUtil
import com.aries.testalfa.util.GridSpacingItemDecoration
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() , OnItemClickListener {

    private var mAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAdapter = MainAdapter()
        mAdapter?.listener = this
        loadWork()
        initView()

    }

    fun initView(){
        recyclerview.setHasFixedSize(true)
        recyclerview.setLayoutManager(GridLayoutManager(applicationContext, 3))
        recyclerview.addItemDecoration(GridSpacingItemDecoration(3, ConvertUtil.dpToPx(baseContext, 4), true))
        recyclerview.setItemAnimator(DefaultItemAnimator())
        recyclerview.setAdapter(mAdapter)

        //setup carouselview
        val image = intArrayOf(R.drawable.gambar1, R.drawable.gambar2, R.drawable.gambar3)
        carouselView.setPageCount(image.size)
        carouselView.setImageListener(ImageListener { position, imageView -> imageView.setImageResource(image[position]) })
    }

    private fun loadWork() {
        val items: ArrayList<HomeItemModel> = ArrayList<HomeItemModel>()
            items.add(HomeItemModel(R.drawable.graphic, "Business"))
            items.add(HomeItemModel(R.drawable.entertaiment, "Entertainment"))
            items.add(HomeItemModel(R.drawable.general, "General"))
            items.add(HomeItemModel(R.drawable.health, "Health"))
            items.add(HomeItemModel(R.drawable.science, "Science"))
            items.add(HomeItemModel(R.drawable.sports, "Sports"))
            items.add(HomeItemModel(R.drawable.tecnology, "Technology"))

        mAdapter?.addItem(items)
    }

    override fun onItemClick(v: View?, model: HomeItemModel) {
        val intent = Intent(this, SourceActivity::class.java)
        intent.putExtra("data1", model.title)
        startActivity(intent)
    }
}