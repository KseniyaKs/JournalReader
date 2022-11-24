package com.example.articlestest.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.articlestest.presentation.theme.Pink
import com.example.articlestest.presentation.theme.WhiteSmoke

@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WhiteSmoke),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator(
            color = Pink,
            modifier = modifier
        )
    }
}
