package com.example.articlestest.presentation.main_app.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import com.example.articlestest.R
import com.example.articlestest.data.model.Article
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.DarkGrey
import com.example.articlestest.presentation.theme.Pink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    private val viewModel: ArticlesViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.ArticleDetails -> {
                    val action =
                        ArticlesFragmentDirections.actionFragmentArticlesToArticleDetails(
                            destination.id
                        )
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }
    }
}

@Composable
fun ArticlesScreen(viewModel: ArticlesViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        BaseViewState.Loading -> {}
        is BaseViewState.Data -> {
            ArticlesContent((uiState as BaseViewState.Data<ArticlesViewState>).value, viewModel)
        }
        else -> {}
    }
}


@Composable
fun ArticlesContent(state: ArticlesViewState, viewModel: ArticlesViewModel) {

    val articles = remember { state.articles.results }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 35.dp)
    ) {

        item {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_small),
                contentDescription = null,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        items(articles) {
            ArticleItem(it, onClick = {
                viewModel.onTriggerEvent(
                    eventType = ArticlesEvent.GetArticleDetails(
                        id = it.id
                    )
                )
            })
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick() }
    )
    {
        val (image, title, date, more) = createRefs()

        AsyncImage(
            model = article.image.file,
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
            text = article.title.uppercase(),
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
            text = article.date,
            color = DarkGrey,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_light_300)),
            modifier = Modifier.constrainAs(date) {
                top.linkTo(title.bottom, margin = 5.dp)
                start.linkTo(parent.start)
            }
        )

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