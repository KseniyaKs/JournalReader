package com.example.articlestest.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.Blue


@Composable
fun LikeAndComment(
    modifier: Modifier = Modifier,
    likeCount: String,
    isLike: Boolean,
    onLikeClick: () -> Unit,
    commentCount: String,
    onCommentClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .wrapContentSize()
    ) {
        val (like, likeAmount, comment, commentAmount, spacer) = createRefs()
        Image(
            painter = painterResource(id = if (isLike) R.drawable.ic_full_like else R.drawable.ic_empty_like),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(like) {
                    start.linkTo(parent.start)
                }
                .clickable { onLikeClick() }
        )

        Text(
            text = if (likeCount.toInt() < 1) "" else likeCount,
            modifier = Modifier.constrainAs(likeAmount) {
                start.linkTo(like.end, margin = 6.dp)
            }
        )

        Spacer(modifier = Modifier
            .width(52.dp)
            .constrainAs(spacer) {
                start.linkTo(like.end)
                end.linkTo(comment.start)
            })

        Icon(
            painter = painterResource(id = R.drawable.ic_comment),
            contentDescription = null,
            tint = Blue,
            modifier = Modifier
                .constrainAs(comment) {
                    end.linkTo(parent.end)
                }
                .clickable { onCommentClick() }
        )

        Text(
            text = commentCount,
            modifier = Modifier.constrainAs(commentAmount) {
                start.linkTo(comment.end, margin = 6.dp)
            }
        )
    }

}