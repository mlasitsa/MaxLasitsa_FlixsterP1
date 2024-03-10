package com.example.maxlasitsa_flixsterp1

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String
) {
    companion object {
        fun fromJsonArray(movieJsonArray: org.json.JSONArray): List<Movie> {
            val movies = mutableListOf<Movie>()
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                        movieJson.getString("poster_path")
                    )
                )
            }
            return movies
        }
    }
}