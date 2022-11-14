package com.example.articlestest.presentation.main_app.pdf_reader

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.articlestest.domain.repositories.MainRepository
import com.example.articlestest.presentation.base.BaseViewModel
import com.example.articlestest.presentation.base.BaseViewState
import com.example.articlestest.presentation.navigation.NavDestination
import com.example.articlestest.presentation.view.FileDownloader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class PdfReaderViewModel @Inject constructor(
    private val repository: MainRepository,
    private val fileDownloader: FileDownloader,
    @ApplicationContext context: Context
) : BaseViewModel<BaseViewState<PdfReaderViewState>, PdfReaderEvent>() {

    val pdfDownload = MutableStateFlow<File?>(null)
    private fun getPage(pageId: String) {
        setState(BaseViewState.Loading)
        viewModelScope.launch {
            val page = repository.getPage(pageId)
            setState(BaseViewState.Data(PdfReaderViewState(page)))
        }
    }

//    private fun onLikeClick(article: Article) {
//        viewModelScope.launch(coroutineExceptionHandler) {
//            val isLike = repository.changeArticleLikeStatus(article.id)
//            val count = article.likeCount.toInt()
//            val articleCopy = article.copy(
//                isLike = isLike,
//                likeCount = if (isLike) count.plus(1) else count.minus(1)
//            )
//
//            setState(BaseViewState.Data(ArticleDetailsViewState(articleCopy)))
//        }
//    }
//
//    private fun onCommentClick(article: Article) {
//        onNavigationEvent(eventType = NavDestination.ArticleComments(article))
//    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val url =
                "https://vk.com/doc10903696_270848153?hash=i0aojwIFMuY7vHiie9KBOMIW9O6VpWnH5unA77JZWBD&dl=u2MUQ3HALsj53uvnpEZWCqFTKdNxkSbgZkz6zqyAQ2z"//"http://polli-style.ru/media/uploads/journals/pdf-test-file.pdf"
            fileDownloader.download(url, "pdf-test-file.pdf")
                .onEach {
                    Log.d("fileDownloader onEach", it.toString())
                }
                .onCompletion {
                    Log.d("fileDownloader onCompletion", it.toString())
                }
                .collect {
                    pdfDownload.emit(it)
                    Log.d("fileDownloader collect", it.toString())
                }
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
}