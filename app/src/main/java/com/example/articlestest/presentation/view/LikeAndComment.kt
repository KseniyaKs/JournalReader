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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.Blue


@Composable
fun LikeAndComment(
    modifier: Modifier = Modifier,
    likeCount: String,
    isLiked: Boolean,
    isCommented: Boolean,
    commentCount: String,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .wrapContentSize()
    ) {
        val (like, likeAmount, comment, commentAmount, spacer) = createRefs()
        Image(
            painter = painterResource(id = if (isLiked) R.drawable.ic_full_like else R.drawable.ic_empty_like),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(like) {
                    start.linkTo(parent.start)
                }
                .clickable { onLikeClick() }
        )

        Text(
            text = if (likeCount.toInt() < 1) "" else likeCount,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            fontSize = 14.sp,
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
            painter = painterResource(id = if (isCommented) R.drawable.ic_is_commented else R.drawable.ic_is_not_comment),
            contentDescription = null,
            tint = Blue,
            modifier = Modifier
                .constrainAs(comment) {
                    end.linkTo(parent.end)
                }
                .clickable { onCommentClick() }
        )

        Text(
            text = if (commentCount.toInt() < 1) "" else commentCount,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            fontSize = 14.sp,
            modifier = Modifier.constrainAs(commentAmount) {
                start.linkTo(comment.end, margin = 6.dp)
            }
        )
    }

}