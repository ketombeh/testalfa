package com.aries.testalfa

import android.app.Application
import android.content.Context
import com.aries.testalfa.network.ApiClient
import com.aries.testalfa.network.ApiInterface

class Application : Application(){
    private lateinit var context: Context
    var apiService: ApiInterface= ApiClient.getClientService().create(ApiInterface::class.java)

    companion object{
        var application: com.aries.testalfa.Application? = null
        val instance: com.aries.testalfa.Application
        get(){
            if(application == null){
                application = com.aries.testalfa.Application()
            }
            return application as com.aries.testalfa.Application
        }
    }

    override fun onCreate(){
        super.onCreate()
        context = baseContext
    }
}