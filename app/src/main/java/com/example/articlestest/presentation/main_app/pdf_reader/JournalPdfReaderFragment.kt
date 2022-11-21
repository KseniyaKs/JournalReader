package com.example.articlestest.presentation.main_app.pdf_reader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.articlestest.R
import com.example.articlestest.data.model.JournalPage
import com.example.articlestest.databinding.FragmentJournalPdfReaderBinding
import com.example.articlestest.extension.hideKeyboard
import com.example.articlestest.extension.monthNumber
import com.example.articlestest.extension.observe
import com.example.articlestest.presentation.navigation.NavDestination
import com.mindev.mindev_pdfviewer.MindevPDFViewer
import com.mindev.mindev_pdfviewer.PdfScope
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JournalPdfReaderFragment : Fragment() {

    private val viewModel: PdfReaderViewModel by viewModels()
    private val args: JournalPdfReaderFragmentArgs by navArgs()

    private var binding: FragmentJournalPdfReaderBinding? = null
    private var id = ""
    private var journalPage: JournalPage? = null

    private val statusListener = object : MindevPDFViewer.MindevViewerStatusListener {
        override fun onStartDownload() {
            binding?.pageCount?.text = "start"
        }

        override fun onPageChanged(position: Int, total: Int) {
            binding?.pageCount?.text = "${position + 1} из $total"

            if (id == args.journalArg.pages[position].id) return
            id = args.journalArg.pages[position].id
            viewModel.onTriggerEvent(eventType = JournalPageEvent.GetPage(id))
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
        binding = FragmentJournalPdfReaderBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initNavigation()
        initViews()
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
                is NavDestination.JournalPageComments -> {
                    val action =
                        JournalPdfReaderFragmentDirections.actionPageToComment(
                            destination.page
                        )
                    findNavController().navigate(action)
                }
                else -> {}
            }
        }
    }

    private fun observeViewModel() {
        observe(viewModel.journalPageState) { page ->
            binding?.likeCommentLayout?.apply {

                likeCount.text =
                    if (page.likeCount.toInt() < 1) "" else page.likeCount.toString()

                commentCount.text =
                    if (page.comments.isEmpty()) "" else page.comments.size.toString()

                like.setBackgroundResource(if (page.isLike) R.drawable.ic_full_like else R.drawable.ic_empty_like)
                comment.setBackgroundResource(if (page.isCommented) R.drawable.ic_is_commented else R.drawable.ic_is_not_comment)
            }
            journalPage = page
        }
    }

    private fun initViews() {
        val url = args.journalArg.journalFile

        binding?.apply {

            pdf.initializePDFDownloader(url, statusListener)
            lifecycle.addObserver(PdfScope())

            nextPage.setOnClickListener {
                pdf.onNextPage()
            }

            previousPage.setOnClickListener {
                pdf.onPreviousPage()
            }

            back.setOnClickListener {
                viewModel.onNavigationEvent(eventType = NavDestination.BackClick)
            }

            pageCount.setOnClickListener {
                selectedPageLayout.selectedPageLayout.visibility = View.VISIBLE
            }

            likeCommentLayout.apply {
                comment.setOnClickListener {
                    viewModel.onTriggerEvent(eventType = JournalPageEvent.CommentClick)
                }

                like.setOnClickListener {
                    viewModel.onTriggerEvent(eventType = JournalPageEvent.LikeClick(journalPage!!))
                }
            }

            date.text = "${args.journalArg.month.monthNumber()}/${args.journalArg.dateIssue}"

            selectedPageLayout.apply {
                cancel.setOnClickListener {
                    hideKeyboard()
                    selectedPageLayout.visibility = View.GONE
                }

                selectedPageNumber.setOnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        val position = selectedPageNumber.text.toString()
                        try {
                            pdf.onSelectedPage(position = position.toInt() - 1)
                            selectedPageNumber.setText("")
                            hideKeyboard()
                        } catch (nfe: NumberFormatException) {

                        }
                        selectedPageLayout.visibility = View.GONE
                        true
                    } else false
                }
            }
        }
    }
}
