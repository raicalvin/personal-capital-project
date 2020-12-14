package com.example.personalcapitalproject.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.personalcapitalproject.models.Article
import com.example.personalcapitalproject.models.BlogResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL

class BlogRepository {

    private val blogDataString: String = "https://www.personalcapital.com/blog/feed/json"
    private val blogDataUrl = URL(blogDataString)

    suspend fun fetchBlogData() {
        withContext(Dispatchers.IO) {
            downloadData()
        }
    }

    private fun downloadData() {
        val urlConnection: HttpURLConnection = blogDataUrl.openConnection() as HttpURLConnection
        try {
            val inputStream = BufferedInputStream(urlConnection.inputStream)
            val result = readStream(inputStream)
            parseResponse(result)
        } finally {
            urlConnection.disconnect()
        }
    }

    private fun readStream(stream: BufferedInputStream): String? {
        val reader = BufferedReader(InputStreamReader(stream))
        val result = StringBuilder()
        var line: String? = reader.readLine()
        while (line != null) {
            result.append(line)
            line = reader.readLine()
        }
        return result.toString()
    }

    private var _blogResponseLiveData: MutableLiveData<BlogResponse> = MutableLiveData()

    val blogResponseLiveData: LiveData<BlogResponse>
        get() = _blogResponseLiveData

    private fun parseResponse(data: String?) {
        val articles: MutableList<Article> = mutableListOf()
        data?.let {
            val jsonObject = JSONObject(it)
            val jsonArray = jsonObject.getJSONArray("items")
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val article = Article(
                    id = item.getString("id"),
                    url = item.getString("url"),
                    category = item.getString("category"),
                    encoded_title = item.getString("encoded_title"),
                    featured_image = item.getString("featured_image"),
                    summary = item.getString("summary"),
                    insight_summary = item.getString("insight_summary"),
                    content = item.getString("content"),
                    summary_html = item.getString("summary_html"),
                    content_html = item.getString("content_html"),
                    date_published = item.getString("date_published"),
                    date_modified = item.getString("date_modified")
                )
                articles.add(article)
            }
            val blogResponse = BlogResponse(
                version = jsonObject.getString("version"),
                user_comment = jsonObject.getString("user_comment"),
                home_page_url = jsonObject.getString("home_page_url"),
                feed_url = jsonObject.getString("feed_url"),
                title = jsonObject.getString("title"),
                description = jsonObject.getString("description"),
                items = articles
            )
            _blogResponseLiveData.postValue(blogResponse)
        }
    }
}