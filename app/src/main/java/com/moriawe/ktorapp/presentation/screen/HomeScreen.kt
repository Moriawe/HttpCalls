package com.moriawe.ktorapp.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moriawe.ktorapp.data.remote.PostServiceImpl
import com.moriawe.ktorapp.data.remote.dto.PostRequest
import com.moriawe.ktorapp.data.remote.dto.PostResponse
import com.moriawe.ktorapp.presentation.components.CodeCard
import com.moriawe.ktorapp.presentation.components.FilterChipGroup
import kotlinx.coroutines.launch


@Composable
fun HomeScreen() {

    Column(modifier = Modifier.padding(all = 12.dp)){

        val chipsList = listOf("/POST", "/GET", "/DELETE", "/PUT")
        var headLine by remember { mutableStateOf(chipsList[0]) }
        val scope  = rememberCoroutineScope()
        var jsonResponse  by remember { mutableStateOf("") }
        val service = PostServiceImpl()
        var showLoading by remember { mutableStateOf(false) }

        val post = PostRequest(
            title = "How to Make HTTP Requests With Ktor-Client in Android",
            body = "Ktor is a client-server framework that helps us build applications in Kotlin. It is a modern asynchronous framework backed by Kotlin coroutines.",
            userId = 1,
        )

        val update = PostResponse(
            id = 5,
            title = "Change post",
            body = "We have successfully changed this post",
            userId = 1,
        )


        Text( style = MaterialTheme.typography.headlineLarge,
            text = headLine)
        Divider()


        FilterChipGroup(items = chipsList,
            onSelectedChanged = { selectedIndex:Int ->
                headLine = chipsList[selectedIndex]
                jsonResponse =""
            })

        OutlinedTextField( modifier = Modifier.fillMaxWidth(),
            value = "https://jsonplaceholder.typicode.com/",
            onValueChange ={},
            readOnly = true,
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(modifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
            .width(200.dp),
            onClick = {
                showLoading = true
                scope.launch {
                    when(headLine){
                        "/POST" -> {
                            jsonResponse = service.createNewPost(post).toString()
                        }
                        "/GET" -> {
                            jsonResponse = service.getAllPosts().toString()

                        }
                        "/PUT" -> {
                            // Use PUT request to Update data
                            jsonResponse = service.updatePost(
                                postResponse = update
                            ).toString()

                        }
                        "/DELETE" -> {
                            jsonResponse = service.deletePost(id = 2).toString()

                        }
                    }
                    showLoading = !showLoading
                }

            }) {
            Text(text = "Send")
        }
        if(showLoading) {
            LinearProgressIndicator( modifier = Modifier.fillMaxWidth())
        }
        CodeCard(jsonStr = jsonResponse)
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreenLight() {
    HomeScreen()
}