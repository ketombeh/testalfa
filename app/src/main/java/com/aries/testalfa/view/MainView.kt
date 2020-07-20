package com.aries.testalfa.view

import com.aries.testalfa.network.response.Articles
import com.aries.testalfa.network.response.News

interface MainView {
    interface View: BaseView{
        fun onSuccess(result: News, isAppend: Boolean)
        fun onAppend(result: ArrayList<Articles>)
        fun onError(message: String)
    }

    interface Presenter : BasePresenter{
        fun loadData(catagory: String)
        fun loadDataSearch(catagory: String , newsTitle: String)
    }
}