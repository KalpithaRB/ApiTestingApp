package com.kalpi.apitestapplication


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.kalpi.apitestapplication.data.Post
import com.kalpi.apitestapplication.data.JsonPlaceholderApi
import com.kalpi.apitestapplication.data.PostRepository
import com.kalpi.apitestapplication.ui.PostViewModel
import com.kalpi.apitestapplication.ui.PostViewModelFactory
import com.kalpi.apitestapplication.ui.theme.ApiTestApplicationTheme
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {

    // Late-initialized ViewModel, which we'll instantiate in onCreate
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Set up Retrofit for networking
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // 2. Create the API service and repository instances
        val api = retrofit.create(JsonPlaceholderApi::class.java)
        val repository = PostRepository(api)

        // 3. Use our custom factory to get an instance of the ViewModel
        val factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PostViewModel::class.java]

        // 4. Set up the UI using Jetpack Compose
        setContent {
            ApiTestApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PostScreen(viewModel)
                }
            }
        }
    }
}

/**
 * The main Composable function for the screen.
 * It observes the ViewModel's data and displays it.
 */
@Composable
fun PostScreen(viewModel: PostViewModel) {
    // We observe the posts LiveData. `observeAsState()` automatically
    // updates the UI whenever the data changes.
    val posts by viewModel.posts.observeAsState(initial = emptyList())

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (posts.isEmpty()) {
            // Show a loading indicator while data is being fetched.
            CircularProgressIndicator()
        } else {
            // Display the list of posts
            PostList(posts)
        }
    }
}

/**
 * A Composable that displays the list of posts in a LazyColumn.
 */
@Composable
fun PostList(posts: List<Post>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(posts) { post ->
            PostItem(post)
        }
    }
}

/**
 * A Composable that displays a single post item.
 */
@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = post.body,
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}