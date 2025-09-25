package com.kalpi.apitestapplication.data

import com.kalpi.apitestapplication.data.Post
import com.kalpi.apitestapplication.data.JsonPlaceholderApi
import java.io.IOException

/**
 * A repository class that acts as a single source of truth for our data.
 * It abstracts the data source (in this case, the network API) from the ViewModel.
 *
 * @param api The Retrofit service interface used to make network calls. This is
 * a form of dependency injection, where we "inject" the API service into the repository.
 */

class PostRepository(private val api: JsonPlaceholderApi) {
    /**
     * Fetches a list of posts from the API.
     *
     * This method contains the core logic for data retrieval, including error handling.
     * It's a suspend function, which means it should be called from a coroutine.
     *
     * @return A list of Post objects, or an empty list if an error occurs.
     */
    suspend fun getPosts(): List<Post> {
        return try {
            // Call the getPosts() method from our Retrofit API interface.
            // Retrofit will handle the network request and JSON-to-object conversion.
            api.getPosts()
        } catch (e: IOException) {
            // Handle network-related exceptions (e.g., no internet connection)
            e.printStackTrace()
            emptyList()
        } catch (e: Exception) {
            // Handle other potential exceptions
            e.printStackTrace()
            emptyList()
        }
    }
}