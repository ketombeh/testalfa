package com.aries.testalfa.view

interface BaseView {
    fun showProgressBar()
    fun hideProgressBar()
    fun isConnected(): Boolean
}