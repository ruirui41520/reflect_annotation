package com.example.testcoroutine.Recyclerview使用

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_fragment.*

class CoinFragment :BaseRecyclerFragment() {
    private var coinItems = ArrayList<BaseRecyclerItemView<*>>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.show()
        for (i in 0..5){
            coinItems.add(CoinRecyclerItem())
        }
        adapter.addAll(coinItems)
        progressBar.hide()
    }
}