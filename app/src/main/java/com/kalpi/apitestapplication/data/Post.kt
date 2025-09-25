package com.kalpi.apitestapplication.data

import com.google.gson.annotations.SerializedName

/**
 * A data class to represent a single "Post" object from a mock API response.
 *
 * It's crucial that the fields in your data class match the keys in the JSON response.
 * We use the `@SerializedName` annotation to handle cases where the JSON key doesn't
 * match our preferred Kotlin naming convention (e.g., using camelCase for property names).
 *
 * Example JSON for a single Post:
 * {
 * "userId": 1,
 * "id": 1,
 * "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
 * "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
 * }
 */

data class Post(
    // We use @SerializedName here to map the "userId" key from the JSON
    // to our preferred Kotlin property name, which in this case is the same.
    // However, if the JSON had "user_id", this annotation would be essential.
    @SerializedName("userId")
    val userId: Int,

    // Here, we're mapping the "id" key to a different property name, "postId",
    // to show how @SerializedName works. This is useful for avoiding conflicts
    // or making names more descriptive.
    @SerializedName("id")
    val postId: Int,

    // When the JSON key matches your property name, you don't need the annotation.
    val title: String,
    val body: String
)
