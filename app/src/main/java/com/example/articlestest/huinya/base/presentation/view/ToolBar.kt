package com.example.articlestest.huinya.base.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.Pink

@Composable
fun Toolbar(articleTitle: String, navController: NavController) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(49.dp)
    )
    {
        val (back, title) = createRefs()

        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back),
            tint = Pink,
            modifier = Modifier
                .constrainAs(back) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    navController
                        .popBackStack()
                        .not()
                }
        )
        Text(
            text = articleTitle.uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}