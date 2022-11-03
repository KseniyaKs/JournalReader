package com.example.articlestest.presentation.main_app.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.DarkGrey
import com.example.articlestest.presentation.theme.Pink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    val viewModel: ArticlesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    ArticlesScreen(viewModel)
                }
            }
        }
    }
}


@Composable
fun ArticlesScreen(viewModel: ArticlesViewModel) {
//    val articles = remember { }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp)
    ) {

        item {
//            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_logo_small),
                contentDescription = null,
                modifier = Modifier.padding(top = 35.dp, bottom = 34.dp)
            )
//            Spacer(modifier = Modifier.height(9.dp))
        }

//        items(articles) {
//            Article(it, onClick = {
//            })
//        }
    }
}

@Composable
fun Article(
//    article: Article,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() })
    {
        val (image, title, date, more) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.food1),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 24.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(167.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "article.title.uppercase(),",
            color = DarkGrey,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.foglihten_no_regular_400)),
            fontWeight = FontWeight.Light,

            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(image.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = "article.date",
            color = DarkGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_light_300)),
            modifier = Modifier.constrainAs(date) {
                top.linkTo(title.bottom, margin = 5.dp)
                start.linkTo(parent.start)
            })
        Text(
            text = stringResource(R.string.more_button),
            color = Pink,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            modifier = Modifier.constrainAs(more) {
                top.linkTo(title.bottom, margin = 5.dp)
                end.linkTo(parent.end)
            })
    }
}