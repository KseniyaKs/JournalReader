package com.example.articlestest.presentation.view

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
fun LikeAndComment(modifier: Modifier = Modifier, amountOfLike: String, amountOfComment: String) {

    ConstraintLayout(
        modifier = Modifier
            .then(modifier)
            .wrapContentSize()
    ) {
        val (like, likeAmount, comment, commentAmount, spacer) = createRefs()
        Icon(
            painter = painterResource(id = R.drawable.ic_like),
            contentDescription = null,
            tint = Blue,
            modifier = Modifier.constrainAs(like) {
                start.linkTo(parent.start)
            }
        )

        Text(
            text = amountOfLike,
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
            modifier = Modifier.constrainAs(comment) {
                end.linkTo(parent.end)
            }
        )

        Text(
            text = amountOfComment,
            modifier = Modifier.constrainAs(commentAmount) {
                start.linkTo(comment.end, margin = 6.dp)
            }
        )
    }

}