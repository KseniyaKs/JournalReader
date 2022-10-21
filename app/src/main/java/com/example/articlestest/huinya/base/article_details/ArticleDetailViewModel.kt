package com.example.articlestest.huinya.base.article_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articlestest.huinya.base.test.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val repository: ArticleRepository
) : ViewModel() {

    fun likeArticle(isLike: Boolean) {
        viewModelScope.launch(CoroutineExceptionHandler{  _, throwable ->
            Log.d("exception", throwable.toString())
        }) {
            repository.likeArticle(isLike)
        }
    }

}