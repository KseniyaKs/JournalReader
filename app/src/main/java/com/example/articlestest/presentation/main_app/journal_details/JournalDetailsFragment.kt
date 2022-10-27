package com.example.articlestest.presentation.main_app.journal_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.articlestest.R
import com.example.articlestest.presentation.theme.DarkGrey
import com.example.articlestest.presentation.theme.Pink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JournalDetailsFragment : Fragment() {

    companion object {
        private const val ID = "ID"
        fun newInstance(id: String) = JournalDetailsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ID, id)
            }
        }
    }

    val viewModel: JournalDetailsViewModel by viewModels()

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
                    JournalDetailsScreen(viewModel)
                }
            }
            isClickable = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

@Composable
fun JournalDetailsScreen(viewModel: JournalDetailsViewModel) {

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val (journal, number, month, price, period, descriptionTag, description, button) = createRefs()

        Log.d("JournalDetailsScreen", "JournalDetailsScreen")
//        PicassoImage(
//            model = "",
//            modifier = Modifier
//                .constrainAs(journal) {
//                    top.linkTo(parent.top, margin = 32.dp)
//                })
        Image(
            painter = painterResource(id = R.drawable.food1),
            contentDescription = null,
            modifier = Modifier
                .height(375.dp)
                .width(375.dp)
                .constrainAs(journal) {
                    top.linkTo(parent.top, margin = 32.dp)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = "№ 1",
            fontFamily = FontFamily(Font(R.font.poiret_one_regular_400)),
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(number) {
                top.linkTo(journal.bottom, margin = 26.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "month",
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            fontSize = 17.sp,
            modifier = Modifier.constrainAs(month) {
                top.linkTo(number.bottom, margin = 2.dp)
                start.linkTo(parent.start)
            }
        )

        Column(modifier = Modifier
            .wrapContentWidth()
            .constrainAs(price) {
                top.linkTo(journal.bottom, margin = 29.dp)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = "234 рубля",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
                color = Pink
            )

            Text(
                text = "в месяц",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.gilroy_light_300)),
                color = DarkGrey,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }

        Text(
            text = stringResource(id = R.string.journal_description),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            color = DarkGrey,
            modifier = Modifier.constrainAs(descriptionTag) {
                top.linkTo(month.bottom, margin = 32.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                    "\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
                    "\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            color = Color.Black,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(descriptionTag.bottom, margin = 6.dp)
                start.linkTo(parent.start)
            }
        )

        Button(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(37.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Pink),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .constrainAs(button) {
                    top.linkTo(description.bottom, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                }
        ) {
            Text(
                text = stringResource(id = R.string.buy),
                fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}