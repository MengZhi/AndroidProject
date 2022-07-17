package com.example.mengzhi.lifecycletesting.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import com.example.mengzhi.lifecycletesting.adapter.GoodsAdapter
import com.example.mengzhi.lifecycletesting.beans.Goods
import com.example.mengzhi.lifecycletesting.databus.LiveDataBus
import com.example.mengzhi.lifecycletesting.presenter.GoodsPresenter
import com.example.mengzhi.myapplication2.R
import com.example.myarouter_annotations.MyARouter
import kotlinx.android.synthetic.main.activity_lifecycle_testing.*
import java.net.Socket
import java.util.ArrayList

@MyARouter(path = "/app/LifecycleTestingActivity")
class LifecycleTestingActivity : IGoodsView, BaseActivity<IGoodsView, GoodsPresenter<IGoodsView>>() {
    private lateinit var listView: ListView

    init {
        val list: List<String> = ArrayList();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lifecycle_testing)
        listView = findViewById(R.id.listView)

        lifecycle.addObserver(presenter as LifecycleObserver)
        presenter?.fetch()

        LiveDataBus.liveDataBus.with("list", ArrayList::class.java)?.observe(this, Observer<ArrayList<*>?> { arrayList ->
                if (arrayList != null) {
                    Log.i("jett", "收到了数据$arrayList")
                }
            })
    }

    override fun showGoodsView(goods: List<Goods>) {
        listView.adapter = GoodsAdapter(this, goods)
    }

    override fun showErrorMessage(msg: String) {
    }

    override fun createPresenter(): GoodsPresenter<IGoodsView> {
        return GoodsPresenter()
    }
}