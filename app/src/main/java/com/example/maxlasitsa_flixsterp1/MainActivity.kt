package com.example.maxlasitsa_flixsterp1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var rvMovies: RecyclerView
    private val movies = mutableListOf<Movie>()
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovies = findViewById(R.id.rvMovies)
        adapter = MoviesAdapter(movies)
        rvMovies.adapter = adapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        fetchMovies()
    }

    private fun fetchMovies() {
        val client = AsyncHttpClient()
        val moviesUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

        client.get(moviesUrl, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                try {
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    // Assuming you've implemented fromJsonArray correctly in your Movie companion object
                    movies.addAll(Movie.fromJsonArray(movieJsonArray))
                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String, throwable: Throwable?) {
                // Log the error or handle it in your preferred way
                Log.e("MainActivity", "Failed to fetch movies: $response", throwable)
            }
        })
    }
}