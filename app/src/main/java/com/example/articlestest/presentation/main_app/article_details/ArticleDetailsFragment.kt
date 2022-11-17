package com.example.articlestest.presentation.main_app.article_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.view.LikeAndComment
import com.example.articlestest.presentation.view.ToolbarWithTitle
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private val viewModel: ArticleDetailsViewModel by viewModels()
    private val args: ArticleDetailsFragmentArgs by navArgs()

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
                    ArticleDetailsScreen(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTriggerEvent(eventType = ArticleDetailsEvent.Get(args.articleIdArg))
    }

    fun initNavigation() {
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.BackClick -> {
                    findNavController().popBackStack()
                }
                is NavDestination.ArticleComments -> {
                    val action =
                        ArticleDetailsFragmentDirections.actionArticleContentToComment(
                            destination.article
                        )
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }
    }
}

@Composable
fun ArticleDetailsScreen(
    viewModel: ArticleDetailsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        BaseViewState.Loading -> {}
        is BaseViewState.Data -> {
            ArticleDetailsContent(
                (uiState as BaseViewState.Data<ArticleDetailsViewState>).value,
                viewModel
            )
        }
        else -> {}
    }

}

@Composable
fun ArticleDetailsContent(state: ArticleDetailsViewState, viewModel: ArticleDetailsViewModel) {

    val article = state.article

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(start = 20.dp, end = 20.dp)),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ToolbarWithTitle(
            titleText = article.title.uppercase(),
            onBack = { viewModel.onNavigationEvent(eventType = NavDestination.BackClick) }
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "article.descriptionLorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                        "\n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                        "\n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat." +
                        "Vertically scrollable component was measured with an infinity maximum height constraints, which is disallowed. One of the common reasons is nesting layouts like LazyColumn and Column(Modifier.verticalScroll()). If you want to add a header before the list of items please add a header as a separate item() before the main items() inside the LazyColumn scope. There are could be other reasons for this to happen: your ComposeView was added into a LinearLayout with some weight, you applied Modifier.wrapContentSize(unbounded = true) or wrote a custom layout. Please try to remove the source of infinite constraints in the hierarchy above the scrolling container."
            )

            LikeAndComment(
                likeCount = article.likeCount.toString(),
                isLiked = article.isLiked,
                isCommented = article.isCommented,
                commentCount = article.comments.size.toString(),
                onCommentClick = {
                    viewModel.onTriggerEvent(
                        eventType = ArticleDetailsEvent.CommentClick(
                            article
                        )
                    )
                },
                onLikeClick = {
                    viewModel.onTriggerEvent(
                        eventType = ArticleDetailsEvent.LikeClick(article)
                    )
                },
                modifier = Modifier.padding(top = 55.dp, bottom = 20.dp)
            )
        }
    }
}