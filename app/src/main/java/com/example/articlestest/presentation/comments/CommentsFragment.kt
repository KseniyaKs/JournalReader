package com.example.articlestest.presentation.comments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.articlestest.R
import com.example.articlestest.data.model.Comment


class CommentsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    CommentsScreen()
                }
            }
        }
    }
}

@Composable
fun CommentsScreen() {
    val comments = remember { mutableStateOf(listOf<Comment>()) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, end = 20.dp, top = 25.dp)
    ) {

        items(comments.value) {
            Comment(it)
        }
    }
}

@Composable
fun Comment(comment: Comment) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        val (image, name, commentText) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_no_image_profile),
            contentDescription = null,
            modifier = Modifier.constrainAs(image) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            })

        Text(
            text = comment.user.firstName + " " + comment.user.surname,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(image.end, margin = 12.dp)
                top.linkTo(parent.top)
            })

        Text(
            text = comment.commentText,
            modifier = Modifier.constrainAs(commentText) {
                top.linkTo(image.bottom, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

