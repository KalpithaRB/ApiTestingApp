package com.kalpi.apitestapplication.data

import retrofit2.http.GET

/**
 * A Retrofit interface that defines the API endpoints for our application.
 *
 * The methods in this interface will be used by Retrofit to make network calls
 * and convert the JSON responses into our data classes.
 */

interface JsonPlaceholderApi {
    /**
     * Fetches a list of posts from the JSONPlaceholder API.
     *
     * @return A List of `Post` objects, which will be populated by Retrofit
     * based on the JSON response from the "posts" endpoint.
     */
    @GET("posts")
    suspend fun getPosts(): List<Post>
}