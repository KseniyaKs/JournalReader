package com.example.articlestest.presentation.main_app.pdf_reader

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.articlestest.databinding.FragmentPdfReaderBinding
import com.example.articlestest.presentation.navigation.NavDestination
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mindev.mindev_pdfviewer.PdfScope
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PdfReaderFragment : Fragment() {

    val viewModel: PdfReaderViewModel by viewModels()
    val args: PdfReaderFragmentArgs by navArgs()

    private var binding: FragmentPdfReaderBinding? = null

    private val statusListener = object : MindevPDFViewer.MindevViewerStatusListener {
        override fun onStartDownload() {
            binding?.tv?.text = "start"
        }

        override fun onPageChanged(position: Int, total: Int) {
            binding?.tv?.text = "${position + 1} из $total"
        }

        override fun onSuccessDownLoad(path: String) {
            binding?.pdf?.fileInit(path)
        }

        override fun onFail(error: Throwable) {
        }

        override fun unsupportedDevice() {
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentPdfReaderBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            Log.d("JournalDetailsFragment", destination.toString())
            when (destination) {
                is NavDestination.BackClick -> {
                    findNavController().popBackStack()
                }
                else -> {}
            }

            val url =
                "https://vk.com/doc10903696_270848153?hash=i0aojwIFMuY7vHiie9KBOMIW9O6VpWnH5unA77JZWBD&dl=u2MUQ3HALsj53uvnpEZWCqFTKdNxkSbgZkz6zqyAQ2z"
            binding?.pdf?.initializePDFDownloader(url, statusListener)
            lifecycle.addObserver(PdfScope())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

//@Composable
//fun ReaderScreen(viewModel: PdfReaderViewModel, firstPageId: String) {
//    val uiState by viewModel.uiState.collectAsState()
//
//    when (uiState) {
//        BaseViewState.Loading -> {}
//        is BaseViewState.Data -> {
//            ReaderContent(
//                (uiState as BaseViewState.Data<PdfReaderViewState>).value,
//                viewModel
//            )
//        }
//        else -> {}
//    }
//
//    LaunchedEffect(Unit) {
//        viewModel.onTriggerEvent(
//            eventType = PdfReaderEvent.GetPage(firstPageId)
//        )
//    }
//}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun ReaderContent(
//    pdfState: PdfReaderViewState,
//    viewModel: PdfReaderViewModel,
//) {
//    val pageInfo = remember { pdfState.page }
//    val pdfFile by viewModel.pdfDownload.collectAsState()
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.SpaceBetween
//    ) {
//        JournalToolBar(
//            onBack = { viewModel.onNavigationEvent(eventType = NavDestination.BackClick) },
//            month = pageInfo.journal.month.monthNumber(),
//            year = pageInfo.journal.dateIssue,
//            sizeJournal = pageInfo.journal.pages.size.toString(),
//            currentPage = "2",
//            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp)
//        )
//
//        pdfFile?.let {
//            PdfViewer(it)
//        }
//    }
//
//    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
//        val (bottomBar) = createRefs()
//
//        JournalBottomBar(
//            onButtonClick = { },
//            likeAmount = pageInfo.likeCount.toString(),
//            commentAmount = pageInfo.comments.size.toString(),
//            isLike = pageInfo.isLike,
//            onLikeClick = {},
//            onCommentClick = {},
//            modifier = Modifier
//                .constrainAs(bottomBar) {
//                    bottom.linkTo(parent.bottom)
//                }
//                .fillMaxWidth()
//                .wrapContentHeight()
//                .background(WhiteSmoke)
//        )
//    }
//}