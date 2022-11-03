package com.example.articlestest.presentation.main_app.pdf_reader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Bundle
import android.os.FileUtils.copy
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.articlestest.extension.monthNumber
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.view.JournalBottomBar
import com.example.articlestest.presentation.view.JournalToolBar
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


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
                else -> {}
            }
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
                viewModel
            )
        }
        else -> {}
    }

    LaunchedEffect(Unit) {
        viewModel.onTriggerEvent(
            eventType = PdfReaderEvent.GetPage(firstPageId)
        )
    }
}

@Composable
fun ReaderContent(
    pdfState: PdfReaderViewState,
    viewModel: PdfReaderViewModel,
) {
    val journalInfo = remember { pdfState.page }
//    val pdfFile by viewModel.pdfDownload.collectAsState()
    val text by viewModel.text.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            JournalToolBar(
                onBack = { viewModel.onNavigationEvent(eventType = NavDestination.BackClick) },
                month = journalInfo.journal.month.monthNumber(),
                year = journalInfo.journal.dateIssue,
                sizeJournal = journalInfo.journal.pages.size.toString(),
                currentPage = "2"
            )

//            val url = "http://95.183.11.194/media/uploads/journals/pdf-test-file.pdf"
//
//            val text = extractPDF(url)

//                        pdfFile?.let {
//                PdfViewer(it, modifier = Modifier.fillMaxHeight())
//            }
            Text(text = text ?: "NOT FOUND")
        }

        Column(
            modifier = Modifier.weight(1f, false),
        ) {
            JournalBottomBar(
                onButtonClick = { },
                likeAmount = journalInfo.likeCount.toString(),
                commentAmount = journalInfo.comments.size.toString()
            )
        }
    }
}

fun extractPDF(name: String): String {
    try {
        // creating a string for
        // storing our extracted text.
        var extractedText = ""

        // creating a variable for pdf reader
        // and passing our PDF file in it.
        val reader = PdfReader(name)

        // below line is for getting number
        // of pages of PDF file.
        val n = reader.numberOfPages;

        // running a for loop to get the data from PDF
        // we are storing that data inside our string.
        for (i in 0..n) {
            extractedText =
                extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n"
            // to extract the PDF content from the different pages
        }

        // after extracting all the data we are
        // setting that string value to our text view.
//        extractedTV.setText(extractedText);

        // below line is used for closing reader.
        reader.close();
        return extractedText
    } finally {

    }
//    catch (Exception e) {
//        // for handling error while extracting the text file.
//        extractedTV.setText("Error found is : \n$e");
//    }
}


//@Composable
//fun PdfViewer(
//    modifier: Modifier = Modifier,
//    uri: Uri,
//    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp)
//) {
//    val rendererScope = rememberCoroutineScope()
//    val mutex = remember { Mutex() }
//    val renderer by produceState<PdfRenderer?>(null, uri) {
//        rendererScope.launch(Dispatchers.IO) {
//            val input = ParcelFileDescriptor.open(uri.toFile(), ParcelFileDescriptor.MODE_READ_ONLY)
//            value = PdfRenderer(input)
//        }
//        awaitDispose {
//            val currentRenderer = value
//            rendererScope.launch(Dispatchers.IO) {
//                mutex.withLock {
//                    currentRenderer?.close()
//                }
//            }
//        }
//    }
//    val context = LocalContext.current
//    val imageLoader = LocalContext.current.imageLoader
//    val imageLoadingScope = rememberCoroutineScope()
//    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
//        val width = with(LocalDensity.current) { maxWidth.toPx() }.toInt()
//        val height = (width * sqrt(2f)).toInt()
//        val pageCount by remember(renderer) { derivedStateOf { renderer?.pageCount ?: 0 } }
//        LazyColumn(
//            verticalArrangement = verticalArrangement
//        ) {
//            items(
//                count = pageCount,
//                key = { index -> "$uri-$index" }
//            ) { index ->
//                val cacheKey = MemoryCache.Key("$uri-$index")
//                var bitmap by remember { mutableStateOf(imageLoader.memoryCache?.get(cacheKey)) }
//                if (bitmap == null) {
//                    DisposableEffect(uri, index) {
//                        val job = imageLoadingScope.launch(Dispatchers.IO) {
//                            val destinationBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                            mutex.withLock {
////                                Timber.d("Loading PDF $uri - page $index/$pageCount")
//                                if (!coroutineContext.isActive) return@launch
//                                try {
//                                    renderer?.let {
//                                        it.openPage(index).use { page ->
//                                            page.render(
//                                                destinationBitmap,
//                                                null,
//                                                null,
//                                                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
//                                            )
//                                        }
//                                    }
//                                } catch (e: Exception) {
//                                    //Just catch and return in case the renderer is being closed
//                                    return@launch
//                                }
//                            }
//                            bitmap = destinationBitmap as MemoryCache.Value
//                        }
//                        onDispose {
//                            job.cancel()
//                        }
//                    }
//                    Box(modifier = Modifier
//                        .background(Color.White)
//                        .aspectRatio(1f / sqrt(2f))
//                        .fillMaxWidth())
//                } else {
//                    val request = ImageRequest.Builder(context)
//                        .size(width, height)
//                        .memoryCacheKey(cacheKey)
//                        .data(bitmap)
//                        .build()
//
//                    Image(
//                        modifier = Modifier
//                            .background(Color.White)
//                            .aspectRatio(1f / sqrt(2f))
//                            .fillMaxWidth(),
//                        contentScale = ContentScale.Fit,
//                        painter = rememberImagePainter(request),
//                        contentDescription = "Page ${index + 1} of $pageCount"
//                    )
//                }
//            }
//        }
//    }
//}


@RequiresApi(Build.VERSION_CODES.Q)
suspend fun Context.loadPdf(
    inputStream: InputStream,
    loadingListener: (
        isLoading: Boolean,
        currentPage: Int?,
        maxPage: Int?
    ) -> Unit = { _, _, _ -> }
): List<String> = withContext(Dispatchers.Default) {
    loadingListener(true, null, null)
    val outputDir = cacheDir
    val tempFile = File.createTempFile("temp", "pdf", outputDir)
    tempFile.mkdirs()
    tempFile.deleteOnExit()
    val outputStream = FileOutputStream(tempFile)
    copy(inputStream, outputStream)
    val input = ParcelFileDescriptor.open(tempFile, ParcelFileDescriptor.MODE_READ_ONLY)
    val renderer = PdfRenderer(input)
    (0 until renderer.pageCount).map { pageNumber ->
        loadingListener(true, pageNumber, renderer.pageCount)
        val file = File.createTempFile("PDFpage$pageNumber", "png", outputDir)
        file.mkdirs()
        file.deleteOnExit()
        val page = renderer.openPage(pageNumber)
        val bitmap = Bitmap.createBitmap(1240, 1754, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, FileOutputStream(file))
        Log.i("PDF_VIEWER", "Loaded page $pageNumber")
        file.absolutePath.also { Log.d("TESTE", it) }
    }.also {
        loadingListener(false, null, renderer.pageCount)
        renderer.close()
    }
}
//