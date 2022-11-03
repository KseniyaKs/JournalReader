package com.example.articlestest.huinya.base.presentation.view

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.articlestest.R
import com.example.articlestest.presentation.main_app.article_details.ArticleDetailViewModel
import com.example.articlestest.presentation.theme.Blue
import com.example.articlestest.presentation.theme.Pink

@Composable
fun ArticleDetailsScreen(
    navController: NavController,
    argument: Bundle?,
    articleDetailViewModel: ArticleDetailViewModel = hiltViewModel()
) {
    val isLike = remember { mutableStateOf<Boolean>(false) }
    val articleTitle = argument?.getString("article")
    val articleImage = argument?.getInt("image")

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(PaddingValues(start = 20.dp, end = 20.dp))
    )
    {
        Toolbar(articleTitle = articleTitle?.uppercase() ?: "", navController = navController)

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            modifier = Modifier.padding(PaddingValues(bottom = 24.dp)),
            lineHeight = 18.sp

        )

        Image(
            painter = painterResource(id = articleImage ?: R.drawable.default_image),
            contentDescription = null,
            modifier = Modifier
                .height(167.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "DemoDataProvider.articleDetails_2",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            modifier = Modifier.padding(PaddingValues(top = 24.dp, bottom = 58.dp)),
            lineHeight = 18.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues(bottom = 22.dp)),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                tint = if (isLike.value) Pink else Blue,
                modifier = Modifier
                    .padding(PaddingValues(end = 52.dp))
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        articleDetailViewModel.likeArticle(isLike.value)
                        isLike.value = !isLike.value
                    }
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = null,
                tint = Blue
            )
        }
    }
}


