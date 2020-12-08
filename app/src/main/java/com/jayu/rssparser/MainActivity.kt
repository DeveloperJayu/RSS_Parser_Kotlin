package com.jayu.rssparser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import com.prof.rssparser.Parser
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    lateinit var parser : Parser
    private val viewModel = MyViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "https://www.rediff.com/rss/moviesreviewsrss.xml"

        parser = Parser.Builder()
                .context(this)
                .charset(Charset.forName("ISO-8859-7"))
                .cacheExpirationMillis(24L*60L*60L*100L)
                .build()

        viewModel.rssChannel.observe(this, { channel ->
            if (channel != null) {
                val articles = channel.articles
                testing.text = Html.fromHtml(articles[3].description.toString())
                val test = articles[0].title.toString()
                Log.v("Article",test.toString())
            }
        })

        viewModel.snackbar.observe(this, { value ->
            value?.let {
                Snackbar.make(rootLayout, value, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShowed()
            }
        })

        viewModel.fetchForUrlAndParseRawRata(url)
    }
}