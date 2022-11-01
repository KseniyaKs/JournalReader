package com.example.articlestest.presentation.main_app.journal_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.compose.AsyncImage
import com.example.articlestest.R
import com.example.articlestest.extension.floatToInt
import com.example.articlestest.extension.getRubleAddition
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.DarkGrey
import com.example.articlestest.presentation.theme.Pink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JournalDetailsFragment : Fragment() {

    val viewModel: JournalDetailsViewModel by viewModels()
    private val args: JournalDetailsFragmentArgs by navArgs()

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
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            Log.d("JournalDetailsFragment", destination.toString())
            when (destination) {
                is NavDestination.BackClick -> {
                    findNavController().popBackStack()
                }
                NavDestination.BuyJournal -> {
                    val action =
                        JournalDetailsFragmentDirections.actionFragmentJournalToBuyJournal()
                    findNavController().navigate(action)
                }
                is NavDestination.ReadJournal -> {
                    val action =
                        JournalDetailsFragmentDirections.actionFragmentJournalToReadJournal(
                            destination.firstPageId
                        )
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onTriggerEvent(eventType = JournalDetailsEvent.Get(args.journalId))
    }
}

@Composable
fun JournalDetailsScreen(viewModel: JournalDetailsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        BaseViewState.Loading -> {}
        is BaseViewState.Data -> {
            JournalDetailsContent(
                (uiState as BaseViewState.Data<JournalDetailsViewState>).value,
                viewModel
            )
        }
        else -> {}
    }
}

@Composable
fun JournalDetailsContent(
    journalState: JournalDetailsViewState,
    viewModel: JournalDetailsViewModel
) {

    val journalInfo = remember { journalState.journal }
    val price = journalInfo.price.floatToInt()
    Log.d("price", price.toString())

    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val (back, journal, number, month, price, descriptionTag, description, button) = createRefs()

        AsyncImage(
            model = "https://i.stack.imgur.com/Xcfqcl.png", //journalInfo.image.file
            contentDescription = null,
            modifier = Modifier
                .constrainAs(journal) {
                    top.linkTo(parent.top, margin = 32.dp)
                }
                .fillMaxWidth()
                .height(375.dp)
        )


//        PicassoImage(
//            model = "https://i.stack.imgur.com/Xcfqcl.png",//journalInfo.image.file,
//            modifier = Modifier
//                .constrainAs(journal) {
//                    top.linkTo(parent.top, margin = 32.dp)
//                }
//                .wrapContentSize()
//                .height(375.dp)
//        )

        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back),
            tint = Pink,
            modifier = Modifier
                .constrainAs(back) {
                    top.linkTo(parent.top, margin = 20.dp)
                    start.linkTo(parent.start)
                }
                .clickable {
                    viewModel.onNavigationEvent(eventType = NavDestination.BackClick)
                }
        )

        Text(
            text = "№ ${journalInfo.number}",
            fontFamily = FontFamily(Font(R.font.poiret_one_regular_400)),
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(number) {
                top.linkTo(journal.bottom, margin = 26.dp)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = journalInfo.month.lowercase(),
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
            if (!journalInfo.isBought) {
                Text(
                    text = journalInfo.price.floatToInt().getRubleAddition(),
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
            text = journalInfo.description,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            color = Color.Black,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(descriptionTag.bottom, margin = 6.dp)
                start.linkTo(parent.start)
            }
        )

        Button(
            onClick = {
                if (journalInfo.isBought) {
                    viewModel.onTriggerEvent(eventType = JournalDetailsEvent.Read(journalInfo.pages.first().id))
                } else viewModel.onTriggerEvent(eventType = JournalDetailsEvent.Buy)
            },
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
                text = if (journalInfo.isBought) stringResource(id = R.string.read) else stringResource(
                    id = R.string.buy
                ),
                fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
                fontSize = 17.sp,
                color = Color.White
            )
        }
    }
}
