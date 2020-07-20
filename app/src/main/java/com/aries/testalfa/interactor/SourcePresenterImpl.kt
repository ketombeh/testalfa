package com.aries.testalfa.interactor

import com.aries.testalfa.Application
import com.aries.testalfa.network.ApiConstant
import com.aries.testalfa.network.ApiInterface
import com.aries.testalfa.view.MainView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SourcePresenterImpl : MainView.Presenter{
    var mainView: MainView.View
    var service: ApiInterface

    var disposable: Disposable? = null

    constructor(mainView: MainView.View){
        this.mainView = mainView
        this.service = Application.instance.apiService
    }

    override fun loadData(category: String) {
         mainView.showProgressBar()
        if(mainView.isConnected()){
            disposable = service.getNews(category , ApiConstant.APIKEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mainView.hideProgressBar()
                        if(it.isSuccessful){
                            mainView.onSuccess(it.body()!!, false)
                        }else{
                            mainView.onError("Oops.. Something when wrong. \n  Please try again..")
                        }
                    }, {
                        mainView.hideProgressBar()
                        mainView.onError("Oops.. Something when wrong. \n  Please try again..")
                    })
        }
    }

    override fun loadDataSearch(category: String , q: String) {
        mainView.showProgressBar()
        if(mainView.isConnected()){
            disposable = service.getSearchNews(category , q , ApiConstant.APIKEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mainView.hideProgressBar()
                        if(it.isSuccessful){
                            mainView.onSuccess(it.body()!!, false)
                        }else{
                            mainView.onError("Oops.. Something when wrong. \n  Please try again..")
                        }
                    }, {
                        mainView.hideProgressBar()
                        mainView.onError("Oops.. Something when wrong. \n  Please try again..")
                    })
        }
    }

    override fun onDestroy() {
        if(disposable != null){
            disposable!!.dispose()
        }
    }

}