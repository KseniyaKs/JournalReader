package com.mindev.mindev_pdfviewer

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class MindevPDFViewer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    interface MindevViewerStatusListener {
        fun onStartDownload()
        fun onPageChanged(position: Int, total: Int)
        fun onSuccessDownLoad(path: String)
        fun onFail(error: Throwable)
        fun unsupportedDevice()
    }


    private var orientation: Direction = Direction.HORIZONTAL
    private var isPdfAnimation: Boolean = false
    private val rv: RecyclerView by lazy { findViewById(R.id.recyclerView) }

    init {
        getAttrs(attrs, defStyleAttr)

        LayoutInflater.from(context)
            .inflate(R.layout.pdf_viewer_view, this, false)
            .let(::addView)
    }

    fun onNextPage() {
        if (findPagePosition(rv) < pageTotalCount) {
            rv.smoothScrollToPosition(
                (rv.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    .plus(1)
            )
        }
    }

    fun onPreviousPage() {
        if (findPagePosition(rv) > 0) {
            rv.smoothScrollToPosition(
                (rv.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                    .minus(1)
            )
        }
    }

    fun onSelectedPage(position: Int) {
        rv.smoothScrollToPosition(position)
    }

    @SuppressLint("CustomViewStyleable")
    private fun getAttrs(attrs: AttributeSet?, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.MindevPDFViewer, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val ori =
            typedArray.getInt(R.styleable.MindevPDFViewer_pdf_direction, Direction.HORIZONTAL.ori)
        orientation = Direction.values().first { it.ori == ori }
        isPdfAnimation = typedArray.getBoolean(R.styleable.MindevPDFViewer_pdf_animation, false)
        typedArray.recycle()
    }

    var pdfRendererCore: PdfCore? = null
    private lateinit var statusListener: MindevViewerStatusListener
    private val pageTotalCount get() = pdfRendererCore?.getPDFPagePage() ?: 0

    fun initializePDFDownloader(url: String, statusListener: MindevViewerStatusListener) {
        pdfRendererCore?.pdfFile?.exists()?.let {
            if (it) return
        }

        this.statusListener = statusListener
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            statusListener.unsupportedDevice()
            return
        }
        val cacheFile = File(context.cacheDir, "mindevPDF.pdf")
        PdfDownloader(cacheFile, url, statusListener)
    }

    fun fileInit(path: String) {

        pdfRendererCore = PdfCore(context, File(path))

        rv.apply {
            layoutManager = LinearLayoutManager(this.context).apply {
                orientation =
                    if (this@MindevPDFViewer.orientation.ori == Direction.HORIZONTAL.ori) {
                        LinearLayoutManager.HORIZONTAL
                    } else {
                        LinearLayoutManager.VERTICAL
                    }
            }
            if (pdfRendererCore != null) adapter = PdfAdapter(pdfRendererCore!!, isPdfAnimation)
            addOnScrollListener(scrollListener)
        }
        rv.let(PagerSnapHelper()::attachToRecyclerView)
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            statusListener.onPageChanged(findPagePosition(recyclerView), pageTotalCount)
        }
    }

    private fun findPagePosition(recyclerView: RecyclerView): Int {
        var pos = 0
        (recyclerView.layoutManager as LinearLayoutManager).run {
            var foundPosition = findFirstCompletelyVisibleItemPosition()
            if (foundPosition != RecyclerView.NO_POSITION) {
                pos = foundPosition
                return@run
            }
            foundPosition = findFirstVisibleItemPosition()
            if (foundPosition != RecyclerView.NO_POSITION) {
                pos = foundPosition
                return@run
            }
        }
        return pos
    }
}