package com.example.articlestest.presentation.main_app.pdf_reader

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.view.FileDownloader
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.util.regex.Pattern
import javax.inject.Inject


@HiltViewModel
class PdfReaderViewModel @Inject constructor(
    private val repository: MainRepository,
    private val fileDownloader: FileDownloader,
    @ApplicationContext context: Context
) : BaseViewModel<BaseViewState<PdfReaderViewState>, PdfReaderEvent>() {

    //    val pdfDownload = MutableStateFlow<File?>(null)
    val text = MutableStateFlow<String?>(null)


    private fun getPage(pageId: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val page = repository.getPage(pageId)
            setState(BaseViewState.Data(PdfReaderViewState(page)))
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val url = "http://95.183.11.194/media/uploads/journals/pdf-test-file.pdf"
            fileDownloader.download(url, "pdf-test-file.pdf")
                .onEach {
                    Log.d("fileDownloader onEach", it.toString())
                }
                .onCompletion {
                    Log.d("fileDownloader onCompletion", it.toString())
                }
                .collect {
//                    pdfDownload.emit(it)
                    text.value = extractPDF(it.path)
                    Log.d("fileDownloader collect", it.toString())
                }
//            downloadFileInInternalStorage(url, "pdf-test-file.pdf", context)
//            text.value = extractPDF(pdfDownload.value!!.name)
        }
    }

    override fun onNavigationEvent(eventType: NavDestination) {
        navigationState.value = eventType
        super.onNavigationEvent(eventType)
    }

    override fun onTriggerEvent(eventType: PdfReaderEvent) {
        when (eventType) {
            is PdfReaderEvent.GetPage -> getPage(eventType.pageId)
        }
    }

    private fun extractPDF(name: String): String {
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

    private fun downloadFileInInternalStorage(link: String, fileName: String, context: Context) {
        val mFileName = fileName.replace(" ", "_")
            .replace(Pattern.compile("[.][.]+").toRegex(), ".")

        val request = Request.Builder()
            .url(link)
            .build()
        val client = OkHttpClient.Builder()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val fileData = response.body?.byteStream()
                if (fileData != null) {
                    try {
                        context.openFileOutput(mFileName, Context.MODE_PRIVATE).use { output ->
                            output.write(fileData.readBytes())
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
                return
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
    }
}