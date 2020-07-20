package com.aries.testalfa.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.aries.testalfa.R
import com.aries.testalfa.adapter.SourceAdapter
import com.aries.testalfa.interactor.SourcePresenterImpl
import com.aries.testalfa.network.NetworkConnection
import com.aries.testalfa.network.response.Articles
import com.aries.testalfa.network.response.News
import com.aries.testalfa.util.AnimationUtil.circleReveal
import com.aries.testalfa.view.MainView
import kotlinx.android.synthetic.main.activity_source.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.layout_toolbar_search.*

class SourceActivity : AppCompatActivity() , MainView.View, SwipeRefreshLayout.OnRefreshListener{
    private var search_menu: Menu? = null
    private var item_search: MenuItem? = null
    private var presenterImpl: SourcePresenterImpl? = null
    private var adapter: SourceAdapter? = null

    private lateinit var intenString: String
    private lateinit var qTitle: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source)
        presenterImpl = SourcePresenterImpl(this)
        adapter = SourceAdapter(this)
        initView()
        if(intent.extras != null){
            val bundle = intent.extras
            intenString = bundle.getString("data1")
            presenterImpl!!.loadData(intenString)
        }
        setSupportActionBar(toolbar)
        setSearchtollbar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_source, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(this@SourceActivity, R.id.searchtoolbar, 1, true, true)
                else
                    searchtoolbar.visibility = View.VISIBLE
                item_search!!.expandActionView()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        swipeRefresh.setOnRefreshListener(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = this.adapter

        adapter?.setListener(object : SourceAdapter.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                val item = adapter!!.getItem(position)
                var intent = Intent(applicationContext , ArticlesActivity::class.java)
                intent.putExtra("title" , item.title)
                intent.putExtra("desc" , item.description)
                intent.putExtra("author" , item.author)
                intent.putExtra("image" , item.urlToImage)
                intent.putExtra("url" , item.url)
                startActivity(intent)
            }
        })
    }

    override fun onSuccess(result: News, isAppend: Boolean) {
        if (result.articles.size > 0) {
            adapter!!.addItems(result.articles, isAppend)
        }
    }

    override fun onAppend(result: ArrayList<Articles>) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {

    }

    override fun showProgressBar() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressBar() {
        swipeRefresh.isRefreshing = false
    }

    override fun isConnected(): Boolean {
        return NetworkConnection.isNetworkConnection(applicationContext)
    }

    override fun onRefresh() {
        try {
            if (adapter!!.itemCount > 0) {
                hideProgressBar()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getData(catagory: String, q: String) {
        presenterImpl!!.loadDataSearch(catagory, q)
    }

    private fun setSearchtollbar() {
        if (searchtoolbar != null) {
            searchtoolbar!!.inflateMenu(R.menu.menu_search)
            search_menu = searchtoolbar!!.menu

            searchtoolbar!!.setNavigationOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    circleReveal(this@SourceActivity, R.id.searchtoolbar, 1, true, false)
                else
                    searchtoolbar!!.visibility = View.GONE
            }

            item_search = search_menu!!.findItem(R.id.action_filter_search)

            MenuItemCompat.setOnActionExpandListener(item_search!!, object : MenuItemCompat.OnActionExpandListener {
                override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        circleReveal(this@SourceActivity, R.id.searchtoolbar, 1, true, false)
                    } else
                        searchtoolbar.visibility = View.GONE
                    return true
                }

                override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                    // Do something when expanded
                    return true
                }
            })
            initSearchView()
        } else Log.d("toolbar", "setSearchtollbar: NULL")
    }

    private fun initSearchView() {
        val searchView = search_menu!!.findItem(R.id.action_filter_search).actionView as SearchView
        searchView.isSubmitButtonEnabled = false

        val closeButton = searchView.findViewById<View>(R.id.search_close_btn) as ImageView
        closeButton.setImageResource(R.drawable.ic_close)

        val txtSearch = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.apply {
            hint = "Search.."
            setHintTextColor(Color.DKGRAY)
            setTextColor(resources.getColor(R.color.colorTextLightPrimary))
        }

        val searchTextView = searchView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as AutoCompleteTextView
        try {
            val mCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            mCursorDrawableRes.isAccessible = true
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                callSearch(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //callSearch(newText)
                return true
            }

            fun callSearch(query: String) {
                Log.i("query", "" + query)
                qTitle = query
//                getData(query, page++.toString())
                getData(intenString , qTitle)

            }
        })
    }
}