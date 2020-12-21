package com.newses.newsly

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var adapter: NewsAdapter
    private var articles = mutableListOf<Article>()
    var pageNumber = 1
    var totalResult = -1
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Toast.makeText(this, "Click a Specific Headlines to Read More", Toast.LENGTH_LONG).show()

        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        //newsList.layoutManager = LinearLayoutManager(this@MainActivity)

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                mainActivity.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d(TAG, "First Visible Item - ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG, "Total Count - ${layoutManager.itemCount}")
                if (totalResult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount-5){
                    pageNumber++
                    getNews()
                }
            }
        })
        newsList.layoutManager = layoutManager

        getNews()
    }

    private fun getNews() {
        Log.d(TAG, "Request Sent for $pageNumber")
        val news = NewsService.newsInstance.getHeadlines("in", pageNumber)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
                    totalResult = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("Newsnetic", "Error in fetching News", t)
            }
        })
    }
}