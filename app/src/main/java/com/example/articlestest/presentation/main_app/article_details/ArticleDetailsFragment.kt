package com.example.articlestest.presentation.main_app.article_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.articlestest.R
import com.example.articlestest.data.model.Article
import com.example.articlestest.databinding.FragmentArticleDetailsBinding
import com.example.articlestest.extension.gone
import com.example.articlestest.extension.observe
import com.example.articlestest.extension.visible
import com.example.articlestest.presentation.navigation.NavDestination
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mindev.mindev_pdfviewer.PdfScope
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private val viewModel: ArticleDetailsViewModel by viewModels()
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private var binding: FragmentArticleDetailsBinding? = null
    private var article: Article? = null

    private val statusListener = object : MindevPDFViewer.MindevViewerStatusListener {
        override fun onStartDownload() {
            binding?.apply {
                progress.progress.visible()
                likeCommentLayout.likeCommentLayout.gone()
            }
        }

        override fun onPageChanged(position: Int, total: Int) {
        }

        override fun onSuccessDownLoad(path: String) {
            binding?.apply {
                pdfArticle.fileInit(path)
                progress.progress.gone()
                likeCommentLayout.likeCommentLayout.visible()
            }

        }

        override fun onFail(error: Throwable) {
            binding?.apply {
                progress.progress.gone()
                likeCommentLayout.likeCommentLayout.visible()
            }
        }

        override fun unsupportedDevice() {

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        if (binding == null) binding =
            FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onTriggerEvent(eventType = ArticleDetailsEvent.Get(args.articleArg.id))

        initNavigation()
        initViews()
        observeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun initNavigation() {
        viewModel.navigationState.observe(viewLifecycleOwner) { destination ->
            when (destination) {
                is NavDestination.BackClick -> {
                    findNavController().popBackStack()
                }
                is NavDestination.ArticleComments -> {
                    val action =
                        ArticleDetailsFragmentDirections.actionArticleContentToComment(
                            destination.article
                        )
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }
    }

    private fun initViews() {
        binding?.apply {
            back.setOnClickListener {
                viewModel.onNavigationEvent(eventType = NavDestination.BackClick)
            }

            likeCommentLayout.apply {
                comment.setOnClickListener {
                    viewModel.onTriggerEvent(eventType = ArticleDetailsEvent.CommentClick)
                }

                like.setOnClickListener {
                    viewModel.onTriggerEvent(eventType = ArticleDetailsEvent.LikeClick(article!!))
                }
            }
        }
    }

    private fun observeViewModel() {
        val url = args.articleArg.articleFile
        binding?.pdfArticle?.initializePDFDownloader(url, statusListener)
        lifecycle.addObserver(PdfScope())

        observe(viewModel.articleState) {

            binding?.apply {
                title.text = it.title.uppercase()

                likeCommentLayout.apply {
                    likeCount.text =
                        if (it.likeCount.toInt() < 1) "" else it.likeCount.toString()

                    commentCount.text =
                        if (it.comments.isEmpty()) "" else it.comments.size.toString()

                    like.setBackgroundResource(if (it.isLiked) R.drawable.ic_full_like else R.drawable.ic_empty_like)
                    comment.setBackgroundResource(if (it.isCommented) R.drawable.ic_is_commented else R.drawable.ic_is_not_comment)
                }
            }
            article = it
        }
    }
}