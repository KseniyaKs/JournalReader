package com.example.articlestest.presentation.main_app.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.articlestest.R
import com.example.articlestest.data.model.Comment
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.Grey300
import com.example.articlestest.presentation.theme.Grey900
import com.example.articlestest.presentation.theme.Pink
import com.example.articlestest.presentation.theme.WhiteSmoke
import com.example.articlestest.presentation.view.ButtonMaxWidthWithText
import com.example.articlestest.presentation.view.ToolbarWithTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private val viewModel: CommentsViewModel by viewModels()

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
                    CommentsScreen(viewModel)
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
                is NavDestination.BackClick -> {
                    findNavController().popBackStack()
                }
                else -> {}
            }
        }
    }
}

@Composable
fun CommentsScreen(viewModel: CommentsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is BaseViewState.Data -> {
            CommentsContent(
                viewModel = viewModel,
                (uiState as BaseViewState.Data<CommentsViewState>).value
            )
        }
        else -> {}
    }
}

@Composable
fun CommentsContent(
    viewModel: CommentsViewModel,
    state: CommentsViewState
) {

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val newCommentsList = remember { state.comments }

    if (state.scrollToBottom) {
        LaunchedEffect(key1 = state) {
            coroutineScope.launch {
                scrollState.animateScrollToItem(newCommentsList.lastIndex)
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 25.dp)
    ) {
        val (toolbar, commentsList, noComments) = createRefs()

        ToolbarWithTitle(
            titleText = stringResource(id = R.string.comments),
            onBack = { viewModel.onNavigationEvent(eventType = NavDestination.BackClick) },
            modifier = Modifier.constrainAs(toolbar) {
                top.linkTo(parent.top)
            }
        )

        if (newCommentsList.isEmpty()) {
            Text(
                text = "Нет комментариев..",
                modifier = Modifier.constrainAs(noComments) {
                    top.linkTo(toolbar.bottom)
                }
            )
        } else {
            LazyColumn(
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .padding(bottom = 220.dp)
                    .constrainAs(commentsList) {
                        top.linkTo(toolbar.bottom)
                    }
            ) {
                for (comment in newCommentsList) {
                    item(comment.id) {
                        Comment(comment)
                    }
                }
            }
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (sendComment) = createRefs()

        SendComment(
            viewModel,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(WhiteSmoke)
                .padding(start = 20.dp, end = 20.dp, bottom = 24.dp)
                .constrainAs(sendComment) {
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SendComment(
    viewModel: CommentsViewModel,
    modifier: Modifier = Modifier
) {
    val commentText = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(98.dp)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Grey900,
                backgroundColor = Color.White,
                focusedIndicatorColor = Grey300,
                unfocusedIndicatorColor = Grey300,
                cursorColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            textStyle = LocalTextStyle.current.copy(
                fontFamily = FontFamily(Font(R.font.gilroy_regular_400)),
                fontSize = 20.sp,
            ),
            value = commentText.value,
            onValueChange = { commentText.value = it }
        )

        ButtonMaxWidthWithText(
            onClick = {
                viewModel.onTriggerEvent(
                    eventType = CommentsEvent.Send(
                        commentText.value
                    )
                )
                commentText.value = ""
            },
            background = Pink,
            text = stringResource(id = R.string.send),
            textColor = Color.White,
            enabled = commentText.value.isNotEmpty()
        )
    }
}

@Composable
fun Comment(comment: Comment) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
    ) {
        val (preview, name, commentText) = createRefs()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .constrainAs(preview) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .background(
                    color = Pink,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Text(
                text = comment.user.firstName.first().toString() + comment.user.surname.first()
                    .toString(),
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }

        Text(
            text = comment.user.firstName + " " + comment.user.surname,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(preview.end, margin = 12.dp)
                top.linkTo(parent.top, margin = 4.dp)
            })

        Text(
            text = comment.commentText,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(commentText) {
                    top.linkTo(preview.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
    }
}
