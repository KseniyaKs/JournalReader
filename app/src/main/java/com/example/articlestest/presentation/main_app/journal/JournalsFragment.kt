package com.example.articlestest.presentation.main_app.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.example.articlestest.R
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.Pink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JournalsFragment : Fragment() {

    val viewModel: JournalsViewModel by viewModels()

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
                    JournalScreen(viewModel)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.JournalDetails -> {
                    val action =
                        JournalsFragmentDirections.actionFragmentJournalToJournalDetails(destination.id)//)
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }
    }
}

@Composable
fun JournalScreen(viewModel: JournalsViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        BaseViewState.Loading -> {}
        is BaseViewState.Data -> {
            JournalsContent((uiState as BaseViewState.Data<JournalsViewState>).value, viewModel)
        }
        else -> {}
    }
}

@Composable
fun JournalsContent(journalsState: JournalsViewState, viewModel: JournalsViewModel) {

    val mainJournal = remember { journalsState.journalsData.journals.first() }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 35.dp, bottom = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val (logo, journal, number, month, buy, more) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.ic_logo_small),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 34.dp)
        )

        AsyncImage(
            model = "https://i.stack.imgur.com/Xcfqcl.png",//mainJournal.image.file,
            contentDescription = null,
            modifier = Modifier
                .constrainAs(journal) {
                    top.linkTo(logo.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(bottom = 32.dp)
                .height(336.dp)
                .fillMaxWidth()
        )

        Text(
            text = "№" + mainJournal.number.toString(),
            fontFamily = FontFamily(Font(R.font.poiret_one_regular_400)),
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(number) {
                top.linkTo(journal.bottom)
                start.linkTo(parent.start)
            }
        )

        Text(
            text = mainJournal.month.lowercase(),
            fontFamily = FontFamily(Font(R.font.gilroy_medium_500)),
            fontSize = 17.sp,
            modifier = Modifier.constrainAs(month) {
                top.linkTo(number.bottom)
                start.linkTo(parent.start)
            }
        )

        Button(
            onClick = {
                viewModel.onTriggerEvent(
                    eventType = JournalsEvent.GetJournalDetails(
                        id = mainJournal.id
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Pink),
            modifier = Modifier.constrainAs(buy) {
                top.linkTo(journal.bottom)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = stringResource(id = R.string.buy),
                fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
                fontSize = 17.sp,
            )
        }

        Text(
            text = stringResource(id = R.string.see_more),
            color = Pink,
            fontFamily = FontFamily(Font(R.font.gilroy_semibold_600)),
            fontSize = 17.sp,
            modifier = Modifier
                .constrainAs(more) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .clickable { }
        )
    }
}