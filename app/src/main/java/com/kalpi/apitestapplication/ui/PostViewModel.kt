package com.kalpi.apitestapplication.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalpi.apitestapplication.data.Post
import com.kalpi.apitestapplication.data.PostRepository
import kotlinx.coroutines.launch

/**
 * The ViewModel for the Post screen.
 * It's responsible for fetching and holding the list of posts.
 *
 * @param repository The PostRepository, which will be "injected" here.
 */

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    // A private MutableLiveData that can be modified inside the ViewModel.
    // It will hold the list of posts.
    private val _posts = MutableLiveData<List<Post>>()

    // A public LiveData that exposes the list of posts to the UI.
    // The UI can observe this LiveData, but cannot modify it directly.
    val posts: LiveData<List<Post>> get() = _posts

    init {
        // We'll automatically fetch posts as soon as the ViewModel is created.
        fetchPosts()
    }

    /**
     * Fetches the posts from the repository.
     *
     * This method launches a coroutine in the viewModelScope. The viewModelScope is
     * tied to the lifecycle of the ViewModel, which means the coroutine will be
     * automatically cancelled if the ViewModel is cleared (e.g., when the user
     * navigates away from the screen).
     */
    fun fetchPosts() {
        viewModelScope.launch {
            // Call the getPosts() method from the repository, which will
            // handle the network request for us.
            val fetchedPosts = repository.getPosts()

            // Update the value of our private MutableLiveData.
            // This will automatically notify any observers (our UI).
            _posts.value = fetchedPosts
        }
    }
}
