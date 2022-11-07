package com.example.articlestest.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.DarkGrey
import com.example.articlestest.presentation.theme.Pink


@Composable
fun JournalPrice(price: String, modifier: Modifier) {

    Column(modifier = Modifier.padding(top = 29.dp)) {
        Text(
            text = "$price рублей",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            color = Pink
        )

        Text(
            text = "в месяц",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_light_300)),
            color = DarkGrey
        )
    }
}

@Composable
fun IssueNumber(month: String, issue: String, modifier: Modifier) {

    Column(modifier = modifier) {
        Text(
            text = "№ $issue",
            fontFamily = FontFamily(Font(R.font.poiret_one_regular_400)),
            fontSize = 20.sp
        )

        Text(
            text = month,
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            fontSize = 17.sp
        )
    }
}

@Composable
fun JournalToolBar(
    onBack: () -> Unit,
    month: String,
    year: String,
    sizeJournal: String,
    currentPage: String
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(bottom = 14.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val (back, dateIssue, pages) = createRefs()
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back),
            tint = Pink,
            modifier = Modifier
                .clickable { onBack() }
                .constrainAs(back) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = "$month/$year",
            modifier = Modifier.constrainAs(dateIssue) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = "$currentPage из $sizeJournal",
            modifier = Modifier.constrainAs(pages) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            }
        )
    }
}

@Composable
fun JournalBottomBar(onButtonClick: () -> Unit, likeAmount: String, commentAmount: String) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (previos, next, likeAndComment) = createRefs()

        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(previos) {
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_previos_page),
                contentDescription = null
            )
        }

        LikeAndComment(
            modifier = Modifier.constrainAs(likeAndComment) {
                start.linkTo(previos.end)
                end.linkTo(next.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            amountOfLike = likeAmount,
            amountOfComment = commentAmount
        )

        IconButton(
            onClick = {},
            modifier = Modifier.constrainAs(next) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_next_page),
                contentDescription = null
            )
        }
    }
}
