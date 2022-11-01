package com.example.articlestest.presentation.main_app.pdf_reader

import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.articlestest.R
import com.example.articlestest.extension.monthNumber
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.theme.Pink
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PdfReaderFragment : Fragment() {

    val viewModel: PdfReaderViewModel by viewModels()
    val args: PdfReaderFragmentArgs by navArgs()

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
                    ReaderScreen(viewModel, firstPageId = args.firstPageId)
                }
            }
            isClickable = true
        }
    }
}

@Composable
fun ReaderScreen(viewModel: PdfReaderViewModel, firstPageId: String) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        BaseViewState.Loading -> {}
        is BaseViewState.Data -> {
            ReaderContent(
                (uiState as BaseViewState.Data<PdfReaderViewState>).value,
                viewModel,
                firstPageId
            )
        }
        else -> {}
    }
}

@Composable
fun ReaderContent(
    pdfState: PdfReaderViewState,
    viewModel: PdfReaderViewModel,
    firstPageId: String
) {

    val journalInfo = remember { pdfState.page }

    Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.back),
            tint = Pink,
            modifier = Modifier
                .clickable {
                    viewModel.onNavigationEvent(eventType = NavDestination.BackClick)
                }
        )

        Text(text = "август".monthNumber() + "/" + "2022")

        Text(text = "78 из 256")
    }



    Column() {
        LaunchedEffect(Unit) {
            viewModel.onTriggerEvent(
                eventType = PdfReaderEvent.GetPage(firstPageId)
            )
        }

        val pfd =
            ParcelFileDescriptor.open(pdfState.page.pageFile, ParcelFileDescriptor.MODE_READ_ONLY)


    }

//    val uri = Uri.parse("file:///android_asset/my-document.pdf")
//    val config = PdfActivityConfiguration.Builder(context).build()
//    PdfActivity.showDocument(this, uri, config)
}