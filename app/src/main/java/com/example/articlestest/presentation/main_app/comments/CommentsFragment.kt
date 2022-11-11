package com.example.articlestest.presentation.main_app.comments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
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
import androidx.navigation.fragment.navArgs
import com.example.articlestest.R
import com.example.articlestest.data.model.Article
import com.example.articlestest.data.model.Comment
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.Grey300
import com.example.articlestest.presentation.theme.Grey900
import com.example.articlestest.presentation.theme.Pink
import com.example.articlestest.presentation.theme.WhiteSmoke
import com.example.articlestest.presentation.view.ArticleToolbar
import com.example.articlestest.presentation.view.ButtonMaxWidthWithText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CommentsFragment : Fragment() {

    val viewModel: CommentsViewModel by viewModels()
    val args: CommentsFragmentArgs by navArgs()

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
                    CommentsScreen(viewModel, args.article)
                }
            }
        }
    }
}

@Composable
fun CommentsScreen(viewModel: CommentsViewModel, article: Article) {

//    val comments = remember { mutableStateOf(listOf<Comment>()) }
    val comments = article.comments


    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 25.dp)
    ) {
        val (toolbar, commentsList, asd) = createRefs()

        ArticleToolbar(
            titleText = stringResource(id = R.string.comments),
            onBack = { viewModel.onNavigationEvent(eventType = NavDestination.BackClick) },
            modifier = Modifier.constrainAs(toolbar) {
                top.linkTo(parent.top)
            }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(bottom = 220.dp)
                .constrainAs(commentsList) {
                    top.linkTo(toolbar.bottom)
                }
        ) {
            items(comments) {
                Comment(it)
            }
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (sendComment) = createRefs()

        SendComment(
            viewModel,
            article,
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

@Composable
fun SendComment(
    viewModel: CommentsViewModel,
    article: Article,
    modifier: Modifier = Modifier
) {
    val commentText = remember { mutableStateOf("") }

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
//            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
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
                        article.id,
                        commentText.value
                    )
                )
            },
            background = Pink,
            text = stringResource(id = R.string.send),
            textColor = Color.White,
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
        val (preview, name, commentText, spacer) = createRefs()

        Log.d("KT", comment.user.firstName.first().toString())

        Text(
            text = comment.user.firstName.first().toString() + comment.user.surname.first()
                .toString(),
            modifier = Modifier
                .height(32.dp)
                .width(32.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Pink)
                .constrainAs(preview) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            textAlign = TextAlign.Center,
            color = Color.White
        )

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

