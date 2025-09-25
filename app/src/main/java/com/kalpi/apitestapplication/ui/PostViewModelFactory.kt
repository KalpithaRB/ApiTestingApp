package com.kalpi.apitestapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kalpi.apitestapplication.data.PostRepository

/**
 * A custom ViewModelProvider.Factory that helps create a PostViewModel instance
 * with its required PostRepository dependency.
 *
 * This is necessary because the PostViewModel has a constructor parameter.
 */

class PostViewModelFactory(private val repository: PostRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // We ensure that the modelClass is of PostViewModel type.
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}