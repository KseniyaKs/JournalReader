package com.example.articlestest.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.Pink


@Composable
fun LogoAndBack(backClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val (back, logo) = createRefs()
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back),
            tint = Pink,
            modifier = Modifier
                .padding(top = 14.dp)
                .constrainAs(back) {
                    start.linkTo(parent.start, margin = 4.dp)
                }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    backClick()
                }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_logo_small),
            contentDescription = null,
            modifier = Modifier
                .padding(PaddingValues(bottom = 45.dp))
                .constrainAs(logo) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        )
    }

//    BackHandler() {
//
//    }
}